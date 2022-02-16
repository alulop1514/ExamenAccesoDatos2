package mongo

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import java.util.logging.Level
import java.util.logging.LogManager

fun main() {
    LogManager.getLogManager().getLogger("").setLevel(Level.SEVERE)

    val con = MongoClient(MongoClientURI("mongodb://ad:ieselcaminas@89.36.214.106/?authSource=test"))
    val bd = con.getDatabase("test")
    val estacions = bd.getCollection("bicicas")
    for (estacio in estacions.find()) {
        println(estacio["id"])
    }
    con.close()
}