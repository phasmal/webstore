package webstore.args

import scala.collection.immutable.Stack
import webstore.args.Args.setting

/** A specification of a set of arguments specifying to a command-line invocation of a program. 
 *  @param options a set of options, any of which may be specified in the args, prior to any command
 *  @param commands a set of commands, one of which must be specified in the args
 */
class ArgSpec(options: Array[OptionType] = Array(), commands: Array[Command] = Array())
{
    val optionMap = options.map(_.toTuple).toMap
    val commandMap = commands.map(_.toTuple).toMap
    
    /** Parses the given command-line arguments, calling the appropriate command. If the args are 
     *  not appropriate for this argspec, exits the application and outputs a usage message.
     *  Also outputs usage if an option is given that is either '-?', '-h', '-help' or '--help'.
     */
    def actOn(args: Array[String]): Int =
    {
        val (optionSettings, remainingArgs) = getOptions(args)
        val (command, parameterArgs) = getCommand(remainingArgs)
        val (paramSettings, leftoverArgs) = getParameters(parameterArgs, command.parameters)
        if (leftoverArgs.length > 0)
        {
            System.err.println("WARNGING: some extra arguments not interpreted: " + leftoverArgs)
        }
        command.action(command.defaults + optionSettings + paramSettings)
    }
    
    private def getParameters(args: Array[String], paramNames: Array[Parameter]): (Settings, Array[String]) = 
    {
        if (args.length > 0 && paramNames.length > 0)
        {
            val (remainingParams, leftoverArgs) = getParameters(args.tail, paramNames.tail)
            return (new Settings(setting(paramNames.head.name, args.head)) + remainingParams, leftoverArgs)
        }
        else // either we've run out of params to match against, or args to match with
        {
            return (new Settings(), args)
        }
    }
    
    private def getOptions(args: Array[String]): (Settings, Array[String]) =
    {
        def recurse(name: String, value: String, remainingArgs: Array[String]): (Settings, Array[String]) = 
        {
            val (settings, argRemainder) = getOptions(remainingArgs)
            return (settings + setting(name, value), argRemainder)
        }
        
        if (args.length > 0 && args.head.startsWith("-"))
        {
            val name = args.head.substring(1)
            
            optionMap.get(name) match 
            {
                // option name matches known type
                case Some(optionType) =>
                    if (optionType.takesValue)
                    {
                        val argTail = args.tail
                        
                        // value required for option is present
                        if (argTail.length > 0)
                        {
                            return recurse(name, argTail(0), argTail.tail)
                        }
                        else
                        {
                            throw new ArgParseException("No value specified for option '" + name + "'")
                        }
                    }
                    else // option stands alone
                    {
                        recurse(name, "true", args.tail)
                    }
                    
                // no match for option name
                case None => 
                    System.err.println("WARNING: no match for option name, ignoring option " + name)
                    return getOptions(args.tail)
            }
        }
        else
        {
            return (new Settings(), args)
        }
    }
    
    private def getCommand(args: Array[String]): (Command, Array[String]) = 
    {
        if (args.length > 0)
        {
            val commandName = args.head
            commandMap.get(commandName) match
            {
                case Some(command) => return (command, args.tail)
                case None => throw new ArgParseException("Command specified '" + commandName + "' is not a valid command")
            }
        }
        else
        {
            throw new ArgParseException("No command specified")
        }
    }
    
    class ArgParseException(val message: String) extends Exception(message)
}
