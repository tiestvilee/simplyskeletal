package org.tiestvilee.proto

import com.googlecode.utterlyidle.Application
import com.googlecode.utterlyidle.Request
import com.googlecode.utterlyidle.Request.get
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.Test

interface ApplicationAssertions {
    val application: Application

    fun bodyAt(request: Request) = application.handle(request).entity().toString()
}

class HelloTest : ApplicationAssertions {
    override val application : Application = ProtoApplication()

    @Test
    fun `does hello world work?`() {
        assertThat(bodyAt(get("/hello")), equalTo("<!doctype html><html><head><title>hello</title><body>hello"))
    }

}