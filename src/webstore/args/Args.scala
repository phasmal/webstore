package webstore.args

/** Functionality allowing parsing of command line arguments */
object Args 
{
    /** Creates and returns a new {@link ArgSpec} containing the given options and commands. 
     */
    def argSpec(options: Array[OptionType], commands: Array[Command]): ArgSpec 
            = new ArgSpec(options, commands)
    
    /** Creates and returns an array of OptionTypes out of the arguments */
    def options(options: OptionType*): Array[OptionType] = options.toArray
    
    /** Creates and returns an array of commands */
    def commands(commands: Command*): Array[Command]= commands.toArray
    
    /** Creates and returns an instance of OptionType */
    def opt(name: String, description: String, takesValue: Boolean = false) : OptionType 
            = new OptionType(name, description, takesValue)
    
    /** Creates and returns an instance of Command */
    def cmd(name: String, 
            description: String, 
            parameters: Array[Parameter] = Array(), 
            defaults: Settings = new Settings(), 
            action: Settings => Int) 
                = new Command(name, description, parameters, defaults, action)
            
    /** Creates and returns an array of parameters. */
    def params(parameters: Parameter*): Array[Parameter] = parameters.toArray
            
    /** Creates and returns an instance of Parameter */
    def param(name: String, description: String, optional: Boolean = false): Parameter 
            = new Parameter(name, description, optional)
}
