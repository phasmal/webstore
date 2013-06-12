package webstore

import java.io.File
import webstore.store._
import webstore.store.disk._

/**
 * A store that can store a file/directory hierarchy.
 * @param location the directory that the store is/should be located in
 * @param filesystem the filesystem the location is inside. Default is local disk filesystem.
 * @throws IllegalArgumentException if location is not a valid directory path (exists and is not a 
 *                                  directory, or cannot be created as a directory)
 */
class Store(location: Path, filesystem: Filesystem = new DiskFilesystem())
{
    /** the directory containing the store versions */
    private val versions = filesystem.getDir(location.subPath("versions"))
    /** the directory containing the store versions */
    private val files = filesystem.getDir(location.subPath("files"))
    
    
    versions.make()
    files.make()
    
    /** 
     * Updates the store so that the current version matches the contents of the given directory.
     * This creates a new version/epoch in the store and sets its contents to the contents of the
     * source dir.
     * @param source the directory that contains the structure to set the store to
     */
    def update(sourceDir: File) =
    {
        
    }
}