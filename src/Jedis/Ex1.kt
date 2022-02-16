package Jedis

import redis.clients.jedis.Jedis

fun main() {
    val con = Jedis("89.36.214.106")
    con.connect()
    con.auth("ieselcaminas.ad")

    var contador = 1
    val claus = con.keys("*")
    for (clau in claus) {
        println("${(claus.indexOf(clau) + 1)}.- $clau")
    }
    while (contador != 0) {
        println("Introdueix un numero (0 per a eixir)")
        contador = readLine()!!.toInt()
        if (contador != 0) {
            val key = claus.elementAt(contador - 1)
            when (con.type(key)) {
                "string" -> {
                    println("$key: ${con.get(key)}")
                }
                "hash" -> {
                    println(key)
                    val subcamps = con.hkeys(key)
                    for (subcamp in subcamps) {
                        println("\t$subcamp --> ${con.hget(key, subcamp)}")
                    }
                }
                "list" -> {
                    println(key)
                    val ll = con.lrange(key, 0, -1)
                    for (valor in ll) {
                        println("\t$valor")
                    }
                }
                "set" -> {
                    println(key)
                    val s = con.smembers(key)
                    for (valor in s) {
                        println("\t$valor")
                    }
                }
                else -> {
                    println(key)
                    val s = con.zrangeWithScores(key, 0, -1)
                    for (valor in s) {
                        println("\t${valor.element} --> ${valor.score}")
                    }
                }
            }
        }
    }
    con.close()
}