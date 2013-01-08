package webstore.args

/** A type of option or switch that can be applied to a command. 
 *  @param name the name of the option - appears on the command line as "-name"
 *  @param description the text description ofthe meaning of the option
 *  @param takesValue true if the option has a value after it (like "-myoption value") default is 
 *                    false
 */
class OptionType(val name: String, val description: String, val takesValue: Boolean = false)
{
    
}
