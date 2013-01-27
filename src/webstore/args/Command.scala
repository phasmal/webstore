package webstore.args

/** A type of command that can define an action that a program can perform. 
 *  @param name the name of the command, may not start with a hyphen ('-')
 *  @param description the textual description of the purpose of the command
 *  @param parameters the set of parameters that can be passed to the command
 *  @param defaults the state that the command should be run with
 *  @param action the action to call when the command is run, takes the options as a settings object 
 *                and returns an integer success code, being 0 for success and anything else for 
 *                failure
 *  
 *  @throws IllegalArgumentException if name begins with a hyphen ('-')
 */
class Command(
    val name: String,
    val description: String,
    val parameters: Array[Parameter],
    val defaults: Settings,
    val action: Settings=>Int)
{
    if (name.startsWith("-"))
    {
        throw new IllegalArgumentException(
            "Command may not start with '-', given was '" + name + "'")
    }
    
    def toTuple: (String, Command) = (name, this)
}
