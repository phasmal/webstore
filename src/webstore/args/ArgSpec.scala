package webstore.args

import scala.collection.immutable.Stack
import webstore.args.Args._

/** A specification of a set of arguments specifying to a command-line invocation of a program. 
 *  @param programName the name of the program
 *  @param options a set of options, any of which may be specified in the args, prior to any command
 *  @param commands a set of commands, one of which must be specified in the args
 */
class ArgSpec(programName: String, options: Array[OptionType] = Array(), commands: Array[Command] = Array())
{
    val helpOption = opt("help", "Show the help/usage screen")
    
    /** The usage string for this argspec. */
    def usage : String 
        = "Usage:\n" +
          programName + " [options*] command [parameters*]\n" +
          "\n" +
          "Commands:\n" +
          commandOverview(commands) +
          "\n" +
          "Options:\n" +
          "   (asterisk '*' following a name means that option takes a value)\n" +
          optionUsage(options) +
          "\n" +
          "Command Detail:\n" +
          commandUsage(commands)
    
    /** Parses the given command-line arguments, calling the appropriate command. If the args are 
     *  not appropriate for this argspec, exits the application and outputs a usage message.
     *  Also outputs usage if an option is given that is either '-?', '-h', '-help' or '--help'.
     */
    def actOn(args: Array[String]): Int =
    {
        try
        {
            val (optionSettings, remainingArgs) 
                = getOptions((options :+ helpOption).map(_.toTuple).toMap, args)
            
            if (optionSettings.get(helpOption.name) == None)
            {
                val (command, parameterArgs)
                    = getCommand(commands.map(_.toTuple).toMap, remainingArgs)
                
                val (paramSettings, leftoverArgs) = getParameters(command.parameters, parameterArgs)
                
                if (leftoverArgs.length > 0)
                {
                    System.err.println("WARNING: some extra arguments ignored: " + leftoverArgs)
                }
                
                command.action(command.defaults + optionSettings + paramSettings)
            }
            else
            {
                printf(usage)
                0
            }
        }
        catch
        {
            case e: ArgParseException => 
                System.err.println("Error: " + e.message + "\n\n" + usage)
                return -1
        }
    }
          
    private def commandOverview(commands: Array[Command]): String
        = commands.map(c => "   " + c.name + ": " + c.description + "\n").mkString
    
    private def optionUsage(options: Array[OptionType]): String 
        = options.map(o => 
            "   " + o.name
            + (if (o.takesValue) "*" else "") 
            + " - " + o.description + "\n").mkString
    
    private def commandUsage(commands: Array[Command]): String 
        = commands.map(c => 
            "   " + c.name + " " + paramString(c) + "\n" + 
            "     " + c.description + "\n" +
            "     " + paramUsage(c) + "\n").mkString
        
    private def paramString(command: Command): String
        = command.parameters.map(
            (param) => 
                (if (param.optional) "[" else "") +
                param.name +
                (if (param.optional) "]" else "")
            ).mkString(" ")
    
    private def paramUsage(command: Command): String
        = command.parameters.map(p => "    " + p.name + ": " + p.description).mkString
    
    private def getOptions(optionMap: Map[String, OptionType], args: Array[String]): (Settings, Array[String]) =
    {
        def recurse(name: String, value: String, remainingArgs: Array[String]): (Settings, Array[String]) = 
        {
            val (settings, argRemainder) = getOptions(optionMap, remainingArgs)
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
                    return getOptions(optionMap, args.tail)
            }
        }
        else
        {
            return (new Settings(), args)
        }
    }
    
    private def getCommand(commandMap: Map[String, Command], args: Array[String]): (Command, Array[String]) = 
    {
        if (args.length > 0)
        {
            val commandName = args.head
            commandMap.get(commandName) match
            {
                case Some(command) => return (command, args.tail)
                case None => 
                    throw new ArgParseException(
                            "Command specified '" + commandName + "' is not a valid command")
            }
        }
        else
        {
            throw new ArgParseException("No command specified")
        }
    }
    
    private def getParameters(
        params: Array[Parameter], args: Array[String]): (Settings, Array[String]) = 
    {
        if (args.length > 0 && params.length > 0)
        {
            val (paramSettings, leftoverArgs) = getParameters(params.tail, args.tail)
            return (new Settings(setting(params.head.name, args.head)) + paramSettings, leftoverArgs)
        }
        else // either we've run out of params to match against, or args to match with
        {
            val mandatoryParams = params.filter(!_.optional)
            if (mandatoryParams.length > 0)
            { 
                throw new ArgParseException(
                    "Mandatory parameters were not specified: " 
                    + mandatoryParams.map(_.name).mkString(", "))
            }
            
            return (new Settings(), args)
        }
    }
    
    class ArgParseException(val message: String) extends Exception(message)
}
