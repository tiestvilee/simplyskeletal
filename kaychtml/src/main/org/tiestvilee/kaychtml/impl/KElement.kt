package org.tiestvilee.kaychtml.impl

import org.apache.commons.text.StringEscapeUtils.escapeHtml4

interface KElement

val String.s: KElement
    get() = KString(escapeHtml4(this))

class KString(val contents: String) : KElement

fun elements(vararg elements: KElement) = KElementList(elements.toList())

fun List<KElement>.elements() = KElementList(this)

class KElementList(val elements: List<KElement>) : KElement

fun noop() = KNoop()

class KNoop : KElement