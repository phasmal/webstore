package webstore.args

/** 
 * A specification of a parameter that can be passed to a command.
 * 
 * @param name the name of the parameter
 * @param description a description of the purpose of the parameter
 * @param optional true if the parameter doesn't have to be specified
 */
class Parameter(val name: String, val description: String, val optional: Boolean = false)
{
}
