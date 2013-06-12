package webfs.store

/** A type of URI that represents a path hierarchy within some context. This could be a path on a
 *  filesystem or the URI space on a server.
 *  
 *  A path is a series of path segments (text names) separated by a forward slash ('/'). The path
 *  also begins with a forward slash.
 *  
 *  @param textForm the text form of the path to create. Whether or not the passed form begins with
 *                  a slash, this path will begin with a slash.
 */
class Path(textForm: String)
{
    private var p:String = if (textForm.startsWith("/")) textForm else '/' + textForm;
    
    /** The text form of the path. */
    def text(): String =
    {
        return p;
    }
}
