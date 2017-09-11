import csv

ktags_header = """package org.tiestvilee.kaychtml.impl

class Doctype(children: List<KElement>) : KTag("!DOCTYPE", true, false, children)
"""

class_definition = """class {}(children: List<KElement>) : KTag("{}", false, {}, children)

"""

kaychtml_header = """package org.tiestvilee.kaychtml

import org.tiestvilee.kaychtml.impl.*

fun doctype(vararg params: KElement): Doctype = Doctype(params.toList())

"""

fun_definition = """fun `{}`(vararg params: KElement): {} = {}(params.toList())

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
