package unittest.org.tiestvilee.kaychtml

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.junit.runners.Parameterized.Parameters
import org.tiestvilee.kaychtml.*
import org.tiestvilee.kaychtml.impl.*

@RunWith(Parameterized::class)
class KaychTMLFormatterTest {

    @JvmField
    @Parameterized.Parameter(0)
    var name: String? = null

    @JvmField
    @Parameterized.Parameter(1)
    var element: KTag? = null

    @JvmField
    @Parameterized.Parameter(2)
    var prettyResult: String? = null

    @JvmField
    @Parameterized.Parameter(3)
    var compactResult: String? = null

    companion object {

        @JvmStatic
        @Parameters(name = "{0}")
        fun data() = listOf(
            arrayOf("Outputs a single tag",
                html(),
                "<html>\n</html>",
                "<html></html>"),
            arrayOf("Outputs a single tag with an id",
                html(id("the-id")),
                """<html id="the-id">
                |</html>""".trimMargin(),
                """<html id="the-id"></html>"""),
            arrayOf("Outputs a single tag with a class",
                html(cl("className")),
                """<html class="className">
                |</html>""".trimMargin(),
                """<html class="className"></html>"""),
            arrayOf("Outputs a single tag with multiple classes",
                html(cl("className"), cl("otherClassName")),
                """<html class="className otherClassName">
                |</html>""".trimMargin(),
                """<html class="className otherClassName"></html>"""),
            arrayOf("Outputs a single tag with lots of attributes",
                html(id("the-id"), cl("className"), "data-test" attr "This is an \"attribute\" <but this isn't a tag>", cl("otherClassName")),
                "<html class=\"className otherClassName\" data-test=\"This is an &quot;attribute&quot; &lt;but this isn't a tag&gt;\" id=\"the-id\">\n</html>",
                "<html class=\"className otherClassName\" data-test=\"This is an &quot;attribute&quot; &lt;but this isn't a tag&gt;\" id=\"the-id\"></html>"),
            arrayOf("Outputs body text",
                html("This is some \"inner text\" <but this isn't a tag>".s),
                "<html>This is some &quot;inner text&quot; &lt;but this isn't a tag&gt;</html>",
                "<html>This is some &quot;inner text&quot; &lt;but this isn't a tag&gt;</html>"),
            arrayOf("Outputs inner tag",
                html("This is some inner text".s, body("user sees this".s)),
                "<html>This is some inner text<body>user sees this</body>\n</html>",
                "<html>This is some inner text<body>user sees this</body></html>"),
            arrayOf("copes with lists of stuff",
                body("a list".s, ul(elements(li("first".s), li("second".s)))),
                "<body>a list<ul>\n" +
                    "    <li>first</li>\n" +
                    "    <li>second</li>\n" +
                    "  </ul>\n" +
                    "</body>",
                """<body>a list<ul><li>first</li><li>second</li></ul></body>"""),
            arrayOf("doctypes are special",
                doctype(attr("html"), html(body("some text".s))),
                "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "  <body>some text</body>\n" +
                    "</html>",
                """<!DOCTYPE html><html><body>some text</body></html>"""),
            arrayOf("empty tags render in a special way",
                body("some text".s, img("src" attr "http://wherever.com"), b()),
                """<body>some text<img src="http://wherever.com">
                '  <b>
                '  </b>
                '</body>""".trimMargin("'"),
                """<body>some text<img src="http://wherever.com"><b></b></body>"""),
            arrayOf("bigger test 1",
                biggerHtml(emptyList(), emptyList()),
                """<!DOCTYPE html>
<html>
  <head>
    <style>  body {
︎      font-family: monospace;
︎      background-color: #eee;
︎  }</style>
  </head>
  <body>
    <h1>no conflicts found</h1>
  </body>
</html>""",
                """<!DOCTYPE html><html><head><style>  body {
︎      font-family: monospace;
︎      background-color: #eee;
︎  }</style></head><body><h1>no conflicts found</h1></body></html>"""),
            arrayOf("bigger test 2",
                biggerHtml(listOf(Conflict(setOf(Scientist("con-A"), Scientist("con-B")), Scientist("auth-c"))), listOf(Scientist("unknown-d"))),
                """<!DOCTYPE html>
<html>
  <head>
    <style>  body {
︎      font-family: monospace;
︎      background-color: #eee;
︎  }</style>
  </head>
  <body>
    <h1>Potential conflicts</h1>
    <ul id="conflicts">
      <li class="conflict">
        <span class="author">auth-c</span>worked with <ul>
          <li>con-A</li>
          <li>con-B</li>
        </ul>
      </li>
    </ul>
    <h1>Couldn't find the following scientists</h1>
    <ul id="missingScientists">
      <li>unknown-d</li>
    </ul>
  </body>
</html>""",
                """<!DOCTYPE html><html><head><style>  body {
︎      font-family: monospace;
︎      background-color: #eee;
︎  }</style></head><body><h1>Potential conflicts</h1><ul id="conflicts"><li class="conflict"><span class="author">auth-c</span>worked with <ul><li>con-A</li><li>con-B</li></ul></li></ul><h1>Couldn't find the following scientists</h1><ul id="missingScientists"><li>unknown-d</li></ul></body></html>""")

        )

        private val conflictsStyles = """
        |  body {
        |︎      font-family: monospace;
        |︎      background-color: #eee;
        |︎  }""".trimMargin()

        data class Scientist(val name: String)

        data class Conflict(val reviewers: Set<Scientist>, val author: Scientist)

        private fun biggerHtml(conflicts: List<Conflict>, scientists: List<Scientist>): Doctype {
            return doctype(attr("html"),
                html(
                    head(style(conflictsStyles.s)),
                    body(
                        if (conflicts.isNotEmpty()) {
                            elements(
                                h1("Potential conflicts".s),
                                ul(id("conflicts"),
                                    conflicts.map { conflict ->
                                        li(cl("conflict"), span(cl("author"), conflict.author.name.s), "worked with ".s,
                                            ul(
                                                conflict.reviewers.map { reviewer -> li(reviewer.name.s) }.elements()
                                            ))
                                    }.elements()))
                        } else {
                            h1("no conflicts found".s)
                        },
                        if (scientists.isNotEmpty()) {
                            elements(
                                h1("Couldn't find the following scientists".s),
                                ul(id("missingScientists"), scientists.map { scientist -> li(scientist.name.s) }.elements())
                            )
                        } else {
                            KNoop()
                        }
                    )
                ))
        }

    }

    @Test
    fun `pretty formatting`() {
        assertThat(element!!.toHtml(), equalTo(prettyResult!!))
    }

    @Test
    fun `compact formatting`() {
        assertThat(element!!.toCompactHtml(), equalTo(compactResult!!))
    }

}
