package org.tiestvilee.kaychtml

import org.tiestvilee.kaychtml.impl.KTag

object SimpleFormatter {
    fun toHtml(tag: KTag): String = toHtml(tag, "")
    fun toHtml(tag: KTag, indent: String): String {
        return indent + when {
            tag.declaration == true -> {
                renderDeclaration(tag, innerText(tag, indent))
            }
            tag.empty == true -> {
                if (innerText(tag, indent).trim().isNotEmpty()) {
                    throw IllegalArgumentException("Tag ${tag.tagName} must be empty")
                }
                renderEmptyTag(tag)
            }
            else -> {
                val innerText = innerText(tag, indent + "  ")
                val indentedInnerText = innerText + if (innerText.trim().isNotEmpty()) "\n" + indent else ""
                renderTag(tag, indentedInnerText)
            }
        }
    }


    private fun renderDeclaration(tag: KTag, innerText: String) =
        "<${tag.tagName}${attributes(tag)}>$innerText"

    private fun renderEmptyTag(tag: KTag) =
        "<${tag.tagName}${attributes(tag)}>"

    private fun renderTag(tag: KTag, innerText: String) =
        "<${tag.tagName}${attributes(tag)}>$innerText</${tag.tagName}>"

    private fun innerText(tag: KTag, indent: String): String =
        tag.content
            .map {
                "\n" + when (it) {
                    is String -> if (it.trim().isNotEmpty()) indent + it else ""
                    is KTag -> toHtml(it, indent)
                    else -> throw IllegalArgumentException("Don't understand item: " + it.javaClass.simpleName)
                }
            }
            .joinToString("")

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

}

private fun attributes(tag: KTag): String {
    val attributes = tag.attributes
        .asSequence()
        .sortedBy { it.key }
        .joinToString(" ") {
            if (it.value.isBlank()) it.key else """${it.key}="${it.value}""""
        }
    return if (attributes.isBlank()) "" else " " + attributes
}

fun KTag.toHtml(): String = SimpleFormatter.toHtml(this)
fun KTag.toCompactHtml(): String = CompactFormatter.toHtml(this)

