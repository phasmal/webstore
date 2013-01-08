package webstore.args

/** A type of command that can define an action that a program can perform. 
 *  @param name the name of the command, may not start with a hyphen ('-')
 *  @param description the textual description of the purpose of the command
 *  @param parameters the set of parameters that can be passed to the command
 *  @param action the state that the command should be run with
 *  // TODO[RM].then do i need action here? - actually should be the function that is called if this command is invoked, which should be *passed* a settings object!
 *  
 *  @throws IllegalArgumentException if name begins with a hyphen ('-')
 */
class Command(
    val name: String,
    val description: String,
    val parameters: Array[Parameter] = Array(),
    val action: Settings = new Settings())
{
    if (name.startsWith("-"))
    {
        throw new IllegalArgumentException(
            "Command may not start with '-', given was '" + name + "'")
    }
}
