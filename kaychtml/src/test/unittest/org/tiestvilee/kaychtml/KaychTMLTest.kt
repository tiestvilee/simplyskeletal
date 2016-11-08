package unittest.org.tiestvilee.kaychtml

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test
import org.tiestvilee.kaychtml.*

class KaychTMLTest {
    @Test
    fun `Outputs a single tag`() {
        assertThat(html().toHtml(),
                equalTo("<html ></html>"))
    }

    @Test
    fun `Outputs a single tag with an id`() {
        assertThat(html(id("the-id")).toHtml(),
                equalTo("""<html id="the-id"></html>"""))
    }

    @Test
    fun `Outputs a single tag with a class`() {
        assertThat(html(cl("className")).toHtml(),
                equalTo("""<html class="className"></html>"""))
    }

    @Test
    fun `Outputs a single tag with multiple classes`() {
        assertThat(html(cl("className"), cl("otherClassName")).toHtml(),
                equalTo("""<html class="className otherClassName"></html>"""))
    }

    @Test
    fun `Outputs a single tag with lots of attributes`() {
        assertThat(html(id("the-id"), cl("className"), attr("data-test", "another attr"), cl("otherClassName")).toHtml(),
                equalTo("<html class=\"className otherClassName\" data-test=\"another attr\" id=\"the-id\"></html>"))
    }

    @Test
    fun `Outputs body text`() {
        assertThat(html("This is some inner text").toHtml(),
                equalTo("<html >\n" +
                        "  This is some inner text\n" +
                        "</html>"))
    }

    @Test
    fun `Outputs inner tag`() {
        assertThat(html("This is some inner text", body("user sees this")).toHtml(),
                equalTo("<html >\n" +
                        "  This is some inner text\n" +
                        "  <body >\n" +
                        "    user sees this\n" +
                        "  </body>\n" +
                        "</html>"))
    }

    @Test
    fun `copes with lists of stuff`() {
        val body = body("a list", ul(listOf(li("first"), li("second"))))
        assertThat(body.toHtml(),
                equalTo("<body >\n" +
                        "  a list\n" +
                        "  <ul >\n" +
                        "    <li >\n" +
                        "      first\n" +
                        "    </li>\n" +
                        "    <li >\n" +
                        "      second\n" +
                        "    </li>\n" +
                        "  </ul>\n" +
                        "</body>"))
    }

}