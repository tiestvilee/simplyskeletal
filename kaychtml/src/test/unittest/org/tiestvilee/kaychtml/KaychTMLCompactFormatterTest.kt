package unittest.org.tiestvilee.kaychtml

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.tiestvilee.kaychtml.*
import org.tiestvilee.kaychtml.impl.*
import kotlin.test.fail

class KaychTMLCompactFormatterTest {
    @Test
    fun `Outputs a single tag`() {
        assertThat(html().toCompactHtml(),
            equalTo("<html></html>"))
    }

    @Test
    fun `Outputs a single tag with an id`() {
        assertThat(html(id("the-id")).toCompactHtml(),
            equalTo("""<html id="the-id"></html>"""))
    }

    @Test
    fun `Outputs a single tag with a class`() {
        assertThat(html(cl("className")).toCompactHtml(),
            equalTo("""<html class="className"></html>"""))
    }

    @Test
    fun `Outputs a single tag with multiple classes`() {
        assertThat(html(cl("className"), cl("otherClassName")).toCompactHtml(),
            equalTo("""<html class="className otherClassName"></html>"""))
    }

    @Test
    fun `Outputs a single tag with lots of attributes`() {
        assertThat(html(id("the-id"), cl("className"), "data-test" attr "another attr", cl("otherClassName")).toCompactHtml(),
            equalTo("<html class=\"className otherClassName\" data-test=\"another attr\" id=\"the-id\"></html>"))
    }

    @Test
    fun `Outputs body text`() {
        assertThat(html("This is some inner text".s).toCompactHtml(),
            equalTo("<html>This is some inner text</html>"))
    }

    @Test
    fun `Outputs inner tag`() {
        assertThat(html("This is some inner text".s, body("user sees this".s)).toCompactHtml(),
            equalTo("<html>This is some inner text<body>user sees this</body></html>"))
    }

    @Test
    fun `copes with lists of stuff`() {
        val body = body("a list".s, ul(elements(li("first".s), li("second".s))))
        assertThat(body.toCompactHtml(),
            equalTo("<body>a list<ul>" +
                "<li>first</li>" +
                "<li>second</li>" +
                "</ul>" +
                "</body>"))
    }

    @Test
    fun `doctypes are special`() {
        val body = doctype(attr("html"), html(body("some text".s)))
        assertThat(body.toCompactHtml(),
            equalTo("<!DOCTYPE html>" +
                "<html>" +
                "<body>some text</body>" +
                "</html>"))
    }

    @Test
    fun `empty tags must be empty`() {
        try {
            body(img("src" attr "http://wherever.com", "contents".s, "alt" attr "a shoe"))
            fail("Empty tags must be empty")
        } catch (e: IllegalArgumentException) {
            assertThat(e.message, equalTo("Tag 'img' must be empty"))
        }
    }

    @Test
    fun `empty tags render in a special way`() {
        val body = body("some text".s, img("src" attr "http://wherever.com"), b())
        assertThat(body.toCompactHtml(),
            equalTo(
                """<body>some text<img src="http://wherever.com"><b></b></body>"""))
    }

    private val conflictsStyles = """
        |  body {
        |︎      font-family: monospace;
        |︎      background-color: #eee;
        |︎  }""".trimMargin()

    @Test
    fun `bigger test`() {
        assertThat(
            biggerHtml(emptyList(), emptyList()).toCompactHtml(),
            equalTo(
                """<!DOCTYPE html><html><head><style>  body {
︎      font-family: monospace;
︎      background-color: #eee;
︎  }</style></head><body><h1>no conflicts found</h1></body></html>"""))

        assertThat(
            biggerHtml(listOf(Conflict(setOf(Scientist("con-A"), Scientist("con-B")), Scientist("auth-c"))), listOf(Scientist("unknown-d"))).toCompactHtml(),
            equalTo(
                """<!DOCTYPE html><html><head><style>  body {
︎      font-family: monospace;
︎      background-color: #eee;
︎  }</style></head><body><h1>Potential conflicts</h1><ul id="conflicts"><li class="conflict"><span class="author">auth-c</span>worked with <ul><li>con-A</li><li>con-B</li></ul></li></ul><h1>Couldn't find the following scientists</h1><ul id="missingScientists"><li>unknown-d</li></ul></body></html>"""))
    }

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