package org.tiestvilee.kaychtml

import org.tiestvilee.kaychtml.impl.KTag

object SimpleFormatter {
    fun toHtml(tag: KTag): String = toHtml(tag, "")
    fun toHtml(tag: KTag, indent: String): String {
        val innerText = innerText(tag, indent + "  ")
        return indent + "<${tag.tagName}${attributes(tag)}>$innerText${if (innerText.length > 0) "\n" + indent else ""}</${tag.tagName}>"
    }

    private fun innerText(tag: KTag, indent: String): String =
            tag.content
                    .map {
                        "\n" + when (it) {
                            is String -> indent + it
                            is KTag -> toHtml(it, indent)
                            else -> throw IllegalArgumentException("Don't understand item: " + it.javaClass.simpleName)
                        }
                    }
                    .joinToString("")

    private fun attributes(tag: KTag): String {
        val attributes = tag.attributes
                .asSequence()
                .sortedBy { it.key }
                .joinToString(" ") {
                    if (it.value.isBlank()) it.key else """${it.key}="${it.value}""""
                }
        return if (attributes.isBlank()) "" else " " + attributes
    }

}

object CompactFormatter {
    fun toHtml(tag: KTag): String =
            "<${tag.tagName}${attributes(tag)}>${innerText(tag)}</${tag.tagName}>"

    private fun innerText(tag: KTag): String =
            tag.content
                    .map {
                        when (it) {
                            is String -> it
                            is KTag -> toHtml(it)
                            else -> throw IllegalArgumentException("Don't understand item: " + it.javaClass.simpleName)
                        }
                    }
                    .joinToString("")

    private fun attributes(tag: KTag): String {
        val attributes = tag.attributes
                .asSequence()
                .sortedBy { it.key }
                .joinToString(" ") {
                    if (it.value.isBlank()) it.key else """${it.key}="${it.value}""""
                }
        return if (attributes.isBlank()) "" else " " + attributes
    }

}

fun KTag.toHtml(): String = SimpleFormatter.toHtml(this)
fun KTag.toCompactHtml(): String = CompactFormatter.toHtml(this)

