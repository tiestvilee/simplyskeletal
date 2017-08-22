import csv

ktags_header = """package org.tiestvilee.kaychtml.impl

class Doctype(vararg params: Any) : KTag("!DOCTYPE", true, false, *params)
"""

class_definition = """class {}(vararg params: Any) : KTag("{}", false, {}, *params)

"""

kaychtml_header = """package org.tiestvilee.kaychtml

import org.tiestvilee.kaychtml.impl.*

fun attr(name: String) = KAttribute(name, "")
infix fun String.attr(value: String) = KAttribute(this, value)

fun id(id: String) = Id(id)
fun cl(className: String) = Class(className)

fun doctype(vararg params: Any): Doctype = Doctype(*params)

"""

fun_definition = """fun `{}`(vararg params: Any): {} = {}(*params)

"""

tags = []

with open("html_tags.csv", "r") as f:
    lines = csv.reader(f)
    for line in lines:
        tags.append((line[0].strip().title(), line[0].strip(), line[1]))

with open("../src/main/org/tiestvilee/kaychtml/impl/KTags.kt", "w") as f:
    f.write(ktags_header)
    for tag in tags:
        f.write(class_definition.format(tag[0], tag[1], tag[2]))

with open("../src/main/org/tiestvilee/kaychtml/KaychTML.kt", "w") as f:
    f.write(kaychtml_header)
    for tag in tags:
        f.write(fun_definition.format(tag[1], tag[0], tag[0]))
