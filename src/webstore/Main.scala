package webstore

import args.Args._


object Main 
{

    /**
     * @param args the command line arguments
     */
    def main(args: Array[String]): Unit =
    {
        argSpec(
            options(
                opt("store", "The location of the store to connect to")
            ), 
            commands(
                cmd("update", 
                    "Updates the store with the current state of the given directory.",
                    params())
            )
        ).actOn(args)
    }

}
