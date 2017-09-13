package unittest.org.tiestvilee.kaychtml

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Assert
import org.junit.Test
import org.tiestvilee.kaychtml.body
import org.tiestvilee.kaychtml.img
import org.tiestvilee.kaychtml.impl.attr
import org.tiestvilee.kaychtml.impl.s

class KaychTMLTest {


    @Test
    fun `empty tags must be empty`() {
        try {
            body(img("src" attr "http://wherever.com", "contents".s, "alt" attr "a shoe"))
            Assert.fail("Empty tags must be empty")
        } catch (e: Throwable) {
            assertThat(e.message, equalTo("Tag 'img' must be empty"))
        }
    }

}