package webfs.store

/** A single item, consisting of a sequence of bytes, stored in a [[Store]] */
class Resource
{
    /** The path that the resource is at. */
    def path: Path
    
    /** The bytes that make up the content of this resource.*/
    def content: IndexNode
    
}
