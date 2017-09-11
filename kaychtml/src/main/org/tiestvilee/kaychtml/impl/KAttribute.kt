package org.tiestvilee.kaychtml.impl

open class KAttribute(val name: String, val value: String) : KElement {
    fun asPair(): Pair<String, String> = Pair(name, value)
}

class Id(id: String) : KAttribute("id", id)

class Class(className: String) : KAttribute("class", className)

fun attr(name: String) = KAttribute(name, "")
infix fun String.attr(value: String) = KAttribute(this, value)

fun id(id: String) = Id(id)
fun cl(className: String) = Class(className)

