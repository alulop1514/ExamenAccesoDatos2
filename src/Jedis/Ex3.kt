package Jedis

import redis.clients.jedis.Jedis
import kotlin.math.floor

fun main() {
    val con = Jedis("89.36.214.106")
    con.connect()
    con.auth("ieselcaminas.ad")

    val numEndevinar = floor(Math.random() * 99 + 1).toInt()
    var numJugador = 0
    val tiempoIni = System.currentTimeMillis().toDouble()
    println(numEndevinar)
    while (numJugador != numEndevinar) {
        println("Introduce un numero del 1 al 100")
        numJugador = readLine()!!.toInt()
    }
    val tiempoFinal = System.currentTimeMillis().toDouble()
    val tiempoTotal = (tiempoIni - tiempoFinal)/1000

    val noms = con.zrangeWithScores("joc_marques", 0 ,9)
    for (nom in noms) {
        println("${nom.element} --> ${nom.score}")
    }

}