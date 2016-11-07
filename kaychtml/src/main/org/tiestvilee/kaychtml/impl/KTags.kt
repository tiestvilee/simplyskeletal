package org.tiestvilee.kaychtml.impl

import org.tiestvilee.kaychtml.impl.KTag

class Html(vararg params: Any) : KTag("html", *params)

class Body(vararg params: Any) : KTag("body", *params)

class Ul(vararg params: Any) : KTag("ul", *params)

class Li(vararg params: Any) : KTag("li", *params)