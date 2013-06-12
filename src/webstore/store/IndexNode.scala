package webfs.store

/** Represents a single node of a block index.
 *
 *  A single node consists of a list of either:
 *  - block references, or
 *  - node references (references to other index nodes)
 */
import scala.collection.mutable.ListBuffer

class IndexNode extends ByteSequence
{
    private var nodes: ListBuffer[ByteSequence] = new ListBuffer[ByteSequence]();
    
    def bytes(): Array[Byte] =
    {
        var bytes:Array[Byte] = new Array[Byte](length)
        
        var i = 0
        nodes.foreach(
            part => 
            {
                part.bytes.foreach( b => bytes(i) = b)
            })
        
        return bytes
    }
    
    def length(): Int =
    {
        return nodes.foldLeft(0)((total,i) => total + i.length)
    }
    
    
    /** Adds the given byte sequence to the end of the child nodes of this sequence. 
     *  @param node the byte sequence to add as a child
     */
    def addNode(node : ByteSequence) { nodes += node }
}
