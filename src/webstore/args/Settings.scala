package webstore.args

/** A set of settings that define configuration values for a system. */
class Settings(settings: Setting*)
{
    val all = settings.toArray
    val map = settings.map(_.toTuple).toMap
    
    /** Returns the value of the option of the given name, null if it is not set. */
    def get(name: String): String = map.get(name).get
}

/** A single setting giving a configuration value for a part of the system. 
 *  @param name the name of the option
 *  @param value the value associated with the name
 */
class Setting(val name: String, val value: String)
{
    /** Returns a tuple of the name and value of this option. */
    def toTuple(): (String,String) = (name, value)
}