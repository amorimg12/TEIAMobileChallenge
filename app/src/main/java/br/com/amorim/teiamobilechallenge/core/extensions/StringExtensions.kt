package br.com.amorim.teiamobilechallenge.core.extensions

fun String.isAlphanumeric(): Boolean {
    val regex = Regex("^[a-zA-Z0-9]*$")
    return regex.matches(this)
}