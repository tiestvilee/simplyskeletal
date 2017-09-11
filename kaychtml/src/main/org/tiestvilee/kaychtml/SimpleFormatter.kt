package org.tiestvilee.kaychtml

import org.tiestvilee.kaychtml.impl.KString
import org.tiestvilee.kaychtml.impl.KTag

object SimpleFormatter {
    data class ChunkRenderResult(val indent: String, val text: String)

    interface Chunk {
        fun toString(indent: String, previous: Chunk): ChunkRenderResult
        fun indentFrom(indent: String): String
    }

    data class Breakable(val contents: String) : Chunk {
        override fun indentFrom(indent: String): String {
            return "\n" + indent
        }

        override fun toString(indent: String, previous: Chunk): ChunkRenderResult {
            return ChunkRenderResult(indent, previous.indentFrom(indent) + contents)
        }
    }

    data class IndentBreakable(val contents: String) : Chunk {
        override fun indentFrom(indent: String): String {
            return "\n" + indent
        }

        override fun toString(indent: String, previous: Chunk): ChunkRenderResult {
            return ChunkRenderResult(indent + "  ", previous.indentFrom(indent) + contents)
        }
    }

    data class UnindentBreakable(val contents: String) : Chunk {
        override fun indentFrom(indent: String): String {
            return "\n" + indent
        }

        override fun toString(indent: String, previous: Chunk): ChunkRenderResult {
            val newIndent = indent.substring(0, Math.max(0, indent.length - 2))
            return ChunkRenderResult(newIndent, previous.indentFrom(newIndent) + contents)
        }
    }

    data class Unbreakable(val contents: String) : Chunk {
        override fun indentFrom(indent: String): String {
            return ""
        }

        override fun toString(indent: String, previous: Chunk): ChunkRenderResult {
            return ChunkRenderResult(indent, contents)
        }
    }

    fun toHtml(tag: KTag): String {
        val chunks = toChunks(tag)
        var previous: Chunk = Unbreakable("")
        var indent = ""
        val result = StringBuilder()

        for (current in chunks) {
            val (newIndent, text) = current.toString(indent, previous)
            indent = newIndent
            previous = current
            result.append(text)
        }
        return result.toString()
    }

    fun toChunks(tag: KTag): List<Chunk> {
        return when {
            tag.declaration -> {
                renderDeclaration(tag, innerText(tag))
            }
            tag.empty -> {
                if (innerText(tag).isNotEmpty()) {
                    throw IllegalArgumentException("Tag ${tag.tagName} must be empty")
                }
                renderEmptyTag(tag)
            }
            else -> {
                renderTag(tag, innerText(tag))
            }
        }
    }


    private fun renderDeclaration(tag: KTag, chunks: List<Chunk>) =
        listOf(Breakable("<${tag.tagName}${attributes(tag)}>")) + chunks

    private fun renderEmptyTag(tag: KTag) =
        listOf(Breakable("<${tag.tagName}${attributes(tag)}>"))

    private fun renderTag(tag: KTag, chunks: List<Chunk>) =
        listOf(IndentBreakable("<${tag.tagName}${attributes(tag)}>")) + chunks + UnindentBreakable("</${tag.tagName}>")

    private fun innerText(tag: KTag): List<Chunk> =
        tag.content
            .flatMap {
                when (it) {
                    is KString -> listOf(Unbreakable(it.contents))
                    is KTag -> toChunks(it)
                    else -> throw IllegalArgumentException("Don't understand item: " + it.javaClass.simpleName)
                }
            }

}


object CompactFormatter {
    fun toHtml(tag: KTag): String =
        when {
            tag.declaration -> {
                "<${tag.tagName}${attributes(tag)}>${innerText(tag)}"
            }
            tag.empty -> {
                "<${tag.tagName}${attributes(tag)}>"
            }
            else -> {
                "<${tag.tagName}${attributes(tag)}>${innerText(tag)}</${tag.tagName}>"
            }
        }


    private fun innerText(tag: KTag): String =
        tag.content
            .map {
                when (it) {
                    is KString -> it.contents
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

