package org.tiestvilee.kaychtml

import org.tiestvilee.kaychtml.impl.*

fun html(vararg params: Any): Html = Html(*params)

fun body(vararg params: Any): Body = Body(*params)

fun ul(vararg params: Any): Ul = Ul(*params)

fun li(vararg params: Any): Li = Li(*params)

fun attr(name: String, value: String) = KAttribute(name, value)

fun id(id: String) = Id(id)

fun cl(className: String) = Class(className)

