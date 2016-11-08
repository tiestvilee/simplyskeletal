ktags_header = """package org.tiestvilee.kaychtml.impl

"""

class_definition = """class {}(vararg params: Any) : KTag("{}", *params)

"""

kaychtml_header = """package org.tiestvilee.kaychtml

import org.tiestvilee.kaychtml.impl.*

fun attr(name: String, value: String) = KAttribute(name, value)
fun id(id: String) = Id(id)
fun cl(className: String) = Class(className)

"""

fun_definition = """fun {}(vararg params: Any): {} = {}(*params)

"""

tags = [
    ("A", "a"),
    ("Abbr", "abbr"),
    ("Address", "address"),
    ("Area", "area"),
    ("Article", "article"),
    ("Aside", "aside"),
    ("Audio", "audio"),
    ("B", "b"),
    ("Base", "base"),
    ("Bdi", "bdi"),
    ("Bdo", "bdo"),
    ("Blockquote", "blockquote"),
    ("Body", "body"),
    ("Br", "br"),
    ("Button", "button"),
    ("Canvas", "canvas"),
    ("Caption", "caption"),
    ("Cite", "cite"),
    ("Code", "code"),
    ("Col", "col"),
    ("Colgroup", "colgroup"),
    ("Command", "command"),
    ("Datalist", "datalist"),
    ("Dd", "dd"),
    ("Del", "del"),
    ("Details", "details"),
    ("Dfn", "dfn"),
    ("Div", "div"),
    ("Dl", "dl"),
    ("Dt", "dt"),
    ("Em", "em"),
    ("Embed", "embed"),
    ("Fieldset", "fieldset"),
    ("Figcaption", "figcaption"),
    ("Figure", "figure"),
    ("Footer", "footer"),
    ("Form", "form"),
    ("H1", "h1"),
    ("H2", "h2"),
    ("H3", "h3"),
    ("H4", "h4"),
    ("H5", "h5"),
    ("H6", "h6"),
    ("Head", "head"),
    ("Header", "header"),
    ("Hgroup", "hgroup"),
    ("Hr", "hr"),
    ("Html", "html"),
    ("I", "i"),
    ("Iframe", "iframe"),
    ("Img", "img"),
    ("Input", "input"),
    ("Ins", "ins"),
    ("Kbd", "kbd"),
    ("Keygen", "keygen"),
    ("Label", "label"),
    ("Legend", "legend"),
    ("Li", "li"),
    ("Link", "link"),
    ("HtmlMap", "map"),
    ("Mark", "mark"),
    ("Menu", "menu"),
    ("Meta", "meta"),
    ("Meter", "meter"),
    ("Nav", "nav"),
    ("Noscript", "noscript"),
    ("HtmlObject", "htmlObject"),
    ("Ol", "ol"),
    ("Optgroup", "optgroup"),
    ("Option", "option"),
    ("Output", "output"),
    ("P", "p"),
    ("Param", "param"),
    ("Pre", "pre"),
    ("Progress", "progress"),
    ("Q", "q"),
    ("Rp", "rp"),
    ("Rt", "rt"),
    ("Ruby", "ruby"),
    ("S", "s"),
    ("Samp", "samp"),
    ("Script", "script"),
    ("Section", "section"),
    ("Select", "select"),
    ("Small", "small"),
    ("Source", "source"),
    ("Span", "span"),
    ("Strong", "strong"),
    ("Style", "style"),
    ("Sub", "sub"),
    ("Summary", "summary"),
    ("Sup", "sup"),
    ("Table", "table"),
    ("Tbody", "tbody"),
    ("Td", "td"),
    ("Textarea", "textarea"),
    ("Tfoot", "tfoot"),
    ("Th", "th"),
    ("Thead", "thead"),
    ("Time", "time"),
    ("Title", "title"),
    ("Tr", "tr"),
    ("Track", "track"),
    ("U", "u"),
    ("Ul", "ul"),
    ("Var", "`var`"),
    ("Video", "video"),
    ("Wbr", "wbr")]

with open("../src/main/org/tiestvilee/kaychtml/impl/KTags.kt", "w") as f:
    f.write(ktags_header)
    for name in tags:
        f.write(class_definition.format(name[0], name[1]))

with open("../src/main/org/tiestvilee/kaychtml/KaychTML.kt", "w") as f:
    f.write(kaychtml_header)
    for name in tags:
        f.write(fun_definition.format(name[1], name[0], name[0]))
