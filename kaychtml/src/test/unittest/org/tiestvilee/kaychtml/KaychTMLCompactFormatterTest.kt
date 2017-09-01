package unittest.org.tiestvilee.kaychtml

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.tiestvilee.kaychtml.*
import org.tiestvilee.kaychtml.impl.Doctype
import org.tiestvilee.kaychtml.impl.KTag
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
        assertThat(html("This is some inner text").toCompactHtml(),
            equalTo("<html>This is some inner text</html>"))
    }

    @Test
    fun `Outputs inner tag`() {
        assertThat(html("This is some inner text", body("user sees this")).toCompactHtml(),
            equalTo("<html>This is some inner text<body>user sees this</body></html>"))
    }

    @Test
    fun `copes with lists of stuff`() {
        val body = body("a list", ul(listOf(li("first"), li("second"))))
        assertThat(body.toCompactHtml(),
            equalTo("<body>a list<ul>" +
                "<li>first</li>" +
                "<li>second</li>" +
                "</ul>" +
                "</body>"))
    }

    @Test
    fun `doctypes are special`() {
        val body = doctype(attr("html"), html(body("some text")))
        assertThat(body.toCompactHtml(),
            equalTo("<!DOCTYPE html>" +
                "<html>" +
                "<body>some text</body>" +
                "</html>"))
    }

    @Test
    fun `empty tags must be empty`() {
        try {
            body(img("src" attr "http://wherever.com", "contents", "alt" attr "a shoe"))
            fail("Empty tags must be empty")
        } catch (e: IllegalArgumentException) {
            assertThat(e.message, equalTo("Tag 'img' must be empty"))
        }
    }

    @Test
    fun `empty tags render in a special way`() {
        val body = body("some text", img("src" attr "http://wherever.com"), b())
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
                head(style(conflictsStyles)),
                body(
                    if (conflicts.isNotEmpty()) {
                        listOf(
                            h1("Potential conflicts"),
                            ul(id("conflicts"),
                                conflicts.map { conflict ->
                                    li(cl("conflict"), span(cl("author"), conflict.author.name), "worked with ",
                                        ul(
                                            conflict.reviewers.map { reviewer -> li(reviewer.name) }
                                        ))
                                }))
                    } else {
                        h1("no conflicts found")
                    },
                    if (scientists.isNotEmpty()) {
                        listOf(
                            h1("Couldn't find the following scientists"),
                            ul(id("missingScientists"), scientists.map { scientist -> li(scientist.name) })
                        )
                    } else {
                        listOf<KTag>()
                    }
                )
            ))
    }


}