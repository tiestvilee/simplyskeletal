package org.tiestvilee.helloworld

import com.googlecode.utterlyidle.Resources
import com.googlecode.utterlyidle.Response
import com.googlecode.utterlyidle.Status.OK
import com.googlecode.utterlyidle.dsl.BindingBuilder
import com.googlecode.utterlyidle.modules.ResourcesModule
import org.tiestvilee.kaychtml.*

class HelloWorldModule : ResourcesModule {
    override fun addResources(resources: Resources): Resources =
            resources.add(BindingBuilder()
                    .httpMethod("GET")
                    .path("/hello")
                    .method(HelloWorldResource::class.java.getDeclaredMethod("hello"))
                    .build())
}

class HelloWorldResource {
    fun hello(): Response {
        return Response.response(OK).entity(
                doctype(attr("html", ""), html(head(title("hello")), body("hello body"))).toCompactHtml()
        )
    }
}