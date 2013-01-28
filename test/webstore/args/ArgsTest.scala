package webstore.args

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import webstore.args._
import webstore.args.Args._

@RunWith(classOf[JUnitRunner])
class ArgsTest extends FunSuite
{
    test("Run a single command with no options configured")
    {
        var length = -1
        
        argSpec(
            "",
            options(),
            commands(
                cmd("b", "", params(), defaults(),
                    (settings: Settings) => 
                    {
                        length = settings.size
                        0
                    }
                )
            )
        ).actOn(Array("b"))
        
        assert(length == 0)
    }
    
    test("Run a single command with one option")
    {
        var length = -1
        var value:Option[String] = None
        
        val spec = argSpec(
            "",
            options(
                opt("a", "", true)
            ),
            commands(
                cmd("b", "", params(), defaults(),
                    (settings: Settings) => 
                    {
                        length = settings.size
                        value = settings.get("a")
                        0
                    }
                )
            )
        )
        
        spec.actOn(Array("b"))
        
        assert(length == 0)
        assert(value == None)
        
        spec.actOn(Array("-a", "aVal", "b"))
        
        assert(length == 1)
        assert(value != None && value.get == "aVal")
    }
    
    test("Run a command out of two possible with options, defaults and parameters")
    {
        var c = ""
        var s = new Settings()
        
        val spec = argSpec(
            "",
            options(
                opt("a", "", true),
                opt("b", "", true)
            ),
            commands(
                cmd("c", "", params(param("d", ""), param("e", "")), defaults(setting("z", "zVal")),
                    (settings: Settings) =>
                    {
                        c = "c"
                        s = settings
                        0
                    }
                ),
                cmd("f", "", params(param("g", "")), defaults(),
                    (settings: Settings) => 
                    {
                        c = "f"
                        s = settings
                        0
                    }
                )
            )
        )
        
        spec.actOn("-b bVal c dVal eVal".split(" "))
        
        assert(c == "c")
        assert(s.size == 4)
        assert(s.get("b") != None && s.get("b").get == "bVal")
        assert(s.get("d") != None && s.get("d").get == "dVal")
        assert(s.get("e") != None && s.get("e").get == "eVal")
        assert(s.get("z") != None && s.get("z").get == "zVal")
        
        spec.actOn("-b bVal -a aVal f gVal ignored".split(" "))
        
        assert(c == "f")
        assert(s.size == 3)
        assert(s.get("a") != None && s.get("a").get == "aVal")
        assert(s.get("b") != None && s.get("b").get == "bVal")
        assert(s.get("g") != None && s.get("g").get == "gVal")
    }
}
