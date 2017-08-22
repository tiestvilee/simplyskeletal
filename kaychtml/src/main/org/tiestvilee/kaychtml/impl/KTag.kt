package org.tiestvilee.kaychtml.impl

open class KTag(val tagName: String, val declaration: Boolean, val empty: Boolean, vararg params: Any) {
    private val flattenedParams = params.fold(listOf<Any>(), { acc, item ->
        when (item) {
            is Collection<*> ->
                item.fold(acc, { flattened, subItem ->
                    if (subItem == null) {
                        flattened
                    } else {
                        flattened + subItem
                    }
                })
            else -> acc + item
        }
    })

    val attributes = flattenedParams
            .filter { it is KAttribute }
            .map { it as KAttribute }
            .fold(mapOf<String, String>(),
                    { acc, attr ->
                        acc + if (acc.containsKey(attr.name)) {
                            Pair(attr.name, (acc[attr.name] + " " + attr.value))
                        } else {
                            attr.asPair()
                        }
                    })

    val content = flattenedParams
            .filter { it !is KAttribute }
            .map {
                if (empty) throw IllegalArgumentException("Tag '$tagName' must be empty")
                when (it) {
                    is String -> it
                    is KTag -> it
                    else -> throw IllegalArgumentException(
                        "Don't understand argument: ${it.javaClass.simpleName}, expecting one of String, KTag")
                }
            }

    override fun toString(): String{
        return "${javaClass.simpleName}$flattenedParams"
    }


}

