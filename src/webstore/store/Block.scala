package webfs.store

/** A block of contiguous bytes as part of a [[Resource]]. */
class Block(blockBytes: Array[Byte]) extends ByteSequence {

    private var content: Array[Byte] = blockBytes
    
    def bytes(): Array[Byte] = 
    {
        return content
    }
    
    def length(): Int =
    {
        return content.length
    }
}
