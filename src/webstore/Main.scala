package webstore

import webstore.args.Args._
import webstore.args._

object Main 
{

    /**
     * @param args the command line arguments
     */
    def main(args: Array[String]): Unit =
    {
        return argSpec(
            options(
                opt("store", "The location of the store to connect to")
            ),
            commands(
                cmd("update", 
                    "Updates the store with the current state of the given directory.",
                    params(
                        param("source","The directory to update the store state with the content of.")),
                    defaults(
                        setting("aDefault","hello default boy")),
                    (settings: Settings) => 
                    {
                        println("Hello Update Boy!")
                        println(settings)
                        0
                    }
                )
            )
        ).actOn(args)
    }

}
