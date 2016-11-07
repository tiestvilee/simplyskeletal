package org.tiestvilee.kaychtml

import org.tiestvilee.kaychtml.impl.KTag

object SimpleFormatter {
    fun toHtml(tag: KTag): String = toHtml(tag, "")
    fun toHtml(tag: KTag, indent: String): String {
        val innerText = innerText(tag, indent + "  ")
        return indent + "<${tag.tagName} ${attributes(tag)}>$innerText${if (innerText.length > 0) "\n" + indent else ""}</${tag.tagName}>"
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

    private fun attributes(tag: KTag) = tag.attributes
            .asSequence()
            .sortedBy { it.key }
            .joinToString(" ") { """${it.key}="${it.value}"""" }

}

fun KTag.toHtml(): String = SimpleFormatter.toHtml(this)

