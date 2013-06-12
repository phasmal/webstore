package webstore

import webstore.args.Args._
import webstore.args._

import java.io.File

object Main
{
    /**
     * @param args the command line arguments
     */
    def main(args: Array[String]): Unit =
    {
        argSpec(
            "webstore",
            options(
                opt("store", "The location of the store to connect to", true)
            ),
            commands(
                cmd("update", 
                    "Updates the store with the current state of the given directory.",
                    params(
                        param("source","The directory to update the store state with the content of.")),
                    defaults(),
                    (settings: Settings) =>
                    {
                        // TODO[RM] add settings.getFile which attempts file creation from string and return option which is OK if new File worked
                        // TODO[RM] unify this with the virtual filesystem concept
                        new Store(new File(settings.get("store").get)).update(new File(settings.get("source").get))
                        0
                    }
                )
            )
        ).actOn(args)
    }
}
