package webstore.args

/** A set of settings that define configuration values for a system. */
class Settings(settings: Array[Setting])
{
    def this(settings: Setting*) = this(settings.toArray)
    
    val byName = settings.map(_.toTuple).toMap
    
    /** Returns the value of the option of the given name, an Option which will contain the value if 
     *  one matches. 
     */
    def get(name: String): Option[String] = byName.get(name).map(_.value)
    
    /** Returns the number of settings. */
    def size: Int = settings.length
    
    /** Returns a new settings object which contains all the settings in this one, plus the given 
     *  setting.  If there is an existing setting in this with the same name as the given setting,
     *  then only the given setting is included in the returned object.
     */
    def +(setting: Setting) = new Settings((byName + (setting.name -> setting)).values.toArray)
    
    /** Returns anew settings object which contains all the settings in this one plus the given
     *  settings. If there are existing setting in this with the same name as the given settings,
     *  then only the ones from the given settings are included in the returned object.
     */
    def +(otherSettings: Settings) =
    {
        val changedMap = otherSettings.byName.foldLeft(byName)((workingMap, tuple) => workingMap + tuple)
        new Settings(changedMap.values.toArray)
    }
    
    override def toString = "{\n" + settings.map("  " + _.toString).mkString("\n") + "\n}"
}

/** A single setting giving a configuration value for a part of the system. 
 *  @param name the name of the option
 *  @param value the value associated with the name
 */
class Setting(val name: String, val value: String)
{
    /** Returns a tuple of the name and self of this setting. */
    def toTuple(): (String,Setting) = (name, this)
    
    override def toString = name + " = " + value
}