package org.tiestvilee.proto

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

class Test {
    @Test
    fun `this just prints to the screen`() {
        println("hello again")
        val v : String? = "asdf"
        assertThat(v, equalTo("asdf"))
    }
}