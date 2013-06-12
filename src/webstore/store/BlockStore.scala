package webfs.store

/** Represents all blocks in the store. 
 *
 *  A block is a consecutive sequence of binary values that make up a segment of a file.
 */
object BlockStore 
{
    // select filesystem to use - configured somewhere statically, lazily load once!
    
    /** Returns the block in the store with the given hash signature, returns None if there is no 
     *  match. 
     *  @param signature the signature of the block to retrieve
     */
    def getBlock(signature:String): Option[Block] =
    {
        var o: Option[Block] = None
        
        // load block file index
        // walk index tree for signature
        // if have hit
        //   load block from disk at offset in file as indicated by block file index
        
        return o;
    }
}
