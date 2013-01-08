package webstore.args

/** A specification of a set of arguments specifying to a command-line invocation of a program. 
 *  @param options a set of options, any of which may be specified in the args, prior to any command
 *  @param commands a set of commands, one of which must be specified in the args
 */
class ArgSpec(options: Array[OptionType] = Array(), commands: Array[Command] = Array())
{
    /** Parses the given command-line arguments, calling the appropriate command. If the args are 
     *  not appropriate for this argspec, exits the application and outputs a usage message.
     *  Also outputs usage if an option is given that is either '-?', '-h', '-help' or '--help'.
     */
    def actOn(args: Array[String])
    {
        //TODO[RM].next from here, work out how to call a command (types etc)
    }
}
