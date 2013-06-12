package webfs.store

/** A byte value that makes up part of a sequence of bytes. */
trait ByteSequence 
{
    /** The bytes that make up this sequence. */
    def bytes(): Array[Byte]
    
    /** The length of the node in bytes. */
    def length(): Int
}
