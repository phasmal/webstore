package webstore

import java.io.File

/**
 * A store that can store a file/directory hierarchy.
 * @param location the directory that the store is/should be located in
 * @throws IllegalArgumentException if location is not a valid directory path (exists and is not a 
 *                                  directory, or cannot be created as a directory)
 */
class Store(location: File)
{
        
    /** the directory containing the store versions */
    private val versions = new File(location, "versions")
    /** the directory containing the store versions */
    private val files = new File(location, "files")
    
    
    // create all dirs or error if not possible
    mkdir(location, "location")
    mkdir(versions, "versions")
    mkdir(files, "files")
    
    /** 
     * Updates the store so that the current version matches the contents of the given directory.
     * This creates a new version/epoch in the store and sets its contents to the contents of the
     * source dir.
     * @param source the directory that contains the structure to set the store to
     */
    def update(sourceDir: File) =
    {
        
    }
    
    private def mkdir(path: File, name: String) = 
    {
        if (path == null || (path.exists && !path.isDirectory)) 
        {
            throw new IllegalArgumentException(
                name + " must be a valid directory path " +
                "(is " + (if (path == null) "null" else path.getAbsolutePath) + " instead)")
                                               
        }
        
        if (!path.exists)
        {
            val dirCreated = path.mkdirs
            if (!dirCreated)
            {
                throw new IllegalArgumentException(
                    "Could not create dir at " + name + ", " + name + " must be a valid directory "
                    + "path (" + name + " = '" + path.getAbsolutePath + "')")
            }
        }
        // POST path exists and is a directory
    }
}