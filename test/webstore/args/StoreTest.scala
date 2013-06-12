package webstore.args

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import webstore.Store
import webstore.store._
import webstore.store.memory._

@RunWith(classOf[JUnitRunner])
class StoreTest extends FunSuite
{
    // TODO[RM]*** NEXT have just defined this test, need to unify with the store API and implement
    //                  & optionally review patterns
    test("Store and retrieve a single file to/from a new store")
    {
        val testText = "This Is A Test\n"
        val testPath = new Path("/test.txt")
        
        // setup source local fs
        val local = new InMemoryFilesystem()
        val f = local.createFile(testPath)
        f.overwrite(testText)
        
        
        // setup store
        val store = new Store(new Path("/"), new InMemoryFilesystem())
        
        
        // do update
        val sourceDir = local.getDir("/")
        store.update(sourceDir)
        
        // setup target local fs
        val local2 = new InMemoryFilesystem()
        val f2 = local2.getFile(testPath)
        
        // do retrieve
        store.retrieve(testPath, f2)
        
        // wow. did all that work?
        assert(f2.getText() == testText)
    }
}
