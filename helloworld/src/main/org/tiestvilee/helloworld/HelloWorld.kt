package org.tiestvilee.helloworld

import com.googlecode.totallylazy.Option
import com.googlecode.totallylazy.Pair.pair
import com.googlecode.utterlyidle.*
import com.googlecode.utterlyidle.Status.OK
import com.googlecode.utterlyidle.annotations.AnnotatedBindings.annotatedClass
import com.googlecode.utterlyidle.annotations.GET
import com.googlecode.utterlyidle.annotations.POST
import com.googlecode.utterlyidle.annotations.Path
import com.googlecode.utterlyidle.annotations.PathParam
import com.googlecode.utterlyidle.dsl.BindingBuilder
import com.googlecode.utterlyidle.modules.ResourcesModule
import org.tiestvilee.kaychtml.*
import org.tiestvilee.kaychtml.impl.KTag
import org.tiestvilee.multipartform.StreamingMultipartFormParts
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets
import java.time.ZonedDateTime


class HelloWorldModule : ResourcesModule {
    override fun addResources(resources: Resources): Resources {
//        val articleType: com.googlecode.totallylazy.Pair<Type, Option<Parameter>> = pair(String::class.java, Option.option(NamedParameter("articleType", String::class.java, Option.none<String>()) as Parameter))
//        val uploadManuscript: com.googlecode.totallylazy.Pair<Type, Option<Parameter>> = pair(String::class.java, Option.option(NamedParameter("uploadManuscript", NamedParameter::class.java, Option.none<String>()) as Parameter))

        /*     @Override
    public Resources addResources(Resources resources) throws Exception {
        return resources.add(annotatedClass(CmsSiteSetupResource.class));
    }
    */
        val annotatedClass = annotatedClass(HelloWorldResource::class.java)

        annotatedClass.forEach { binding ->
            printBinding(binding)
        }

        val requestType: Type = Request::class.java
        val postResource = BindingBuilder()
            .httpMethod("POST")
            .method(HelloWorldResource::class.java.getDeclaredMethod("post", Request::class.java))
            .parameters(listOf(pair(requestType, Option.none<Parameter>())))
            .path("/hello")
            .build()
        printBinding(postResource)

        return resources
            .add(BindingBuilder()
                .httpMethod("GET")
                .path("/hello")
                .method(HelloWorldResource::class.java.getDeclaredMethod("hello"))
                .build())
//            .add(*annotatedClass)
            .add(postResource)
    }

    private fun printBinding(binding: Binding) {
        println(binding)
        println("  a " + binding.action())
        println("  c " + binding.consumes())
        println("  h " + binding.hidden())
        println("  h " + binding.httpMethod())
        println("  n " + binding.namedParameters().map { p -> "${p.parametersClass()} -> ${p.name()}, ${p.defaultValue()} " })
        println("  # " + binding.numberOfArguments())
        println("  p " + binding.parameters())
        println("  1 " + binding.priority())
        println("  p " + binding.produces())
        println("  u " + binding.uriTemplate())
    }
}

class HelloWorldResource {
    fun hello(): Response {
        return Response.response(OK).entity(
            page("Hello World",
                div(cl("row"),
                    div(cl("col-md-4")),
                    div(cl("col-md-4"),
                        div(cl("fluid card"),
                            div(cl("section"), h3("Hello World")),
                            form("method" attr "POST", "action" attr "", "enctype" attr "multipart/form-data",
                                fieldset(
                                    legend("Create a new article"),
                                    formRow(label("for" attr "articleType", "Article Type"),
                                        select("id" attr "articleType", "name" attr "articleType",
                                            option("review"),
                                            option("obituary")
                                        )),
                                    formRow(label("for" attr "uploadManuscript", "Upload Manuscript"),
                                        input("type" attr "file", "name" attr "uploadManuscript", "id" attr "uploadManuscript", attr("multiple")),
                                        label("for" attr "uploadManuscript", cl("button"), "Upload")
                                    ),
                                    formRow(span(), button("Create", "type" attr "submit"))
                                )
                            )
                        )))
            ).toHtml()

        )
    }

    private fun formRow(label: KTag, vararg input: KTag): KTag {
        return div(cl("row responsive-label"),
            div(cl("col-md-5"), label),
            div(cl("col-md"), *input))
    }

    val styles = ""
    val scripts = ""

    private fun page(title: String, content: KTag): KTag {
        return doctype(attr("html"),
            html(
                head(
                    title(title),
                    link("rel" attr "stylesheet", "href" attr "https://gitcdn.link/repo/Chalarangelo/mini.css/master/dist/mini-default.min.css"),
                    style(styles)
                ),
                body(
                    header(cl("sticky row"),
                        div(cl("col-sm col-md-10, col-md-offset-1"),
                            a("href" attr "/editor/manuscriptTable", "role" attr "button", "Manuscripts"),
                            a("href" attr "asXml", "role" attr "button", "As XML"),
                            a("href" attr "asPdf", "role" attr "button", "As PDF")
                        )),
                    div(cl("container"), content),
                    footer(
                        div(cl("col-sm col-md-10 col-md-offset-1"),
                            p("Copyright &copy; SpringerNature ${ZonedDateTime.now().year}"))
                    ),
                    script(scripts)
                )))
    }


    /*
        @POST
    @Path("/site/create")
    @Produces(APPLICATION_JSON)
    public Response createSite(@FormParam("publisher") PublisherBrand publisher,
                               @FormParam("siteHostname") SiteKey siteHostname) {
     */
    @POST
    @Path("/hello")
    fun post(request: Request): Response {

        val contentType = request.header("Content-Type").get()
        val boundary = contentType.substring(contentType.indexOf("boundary=") + "boundary=".length).toByteArray(StandardCharsets.UTF_8)
        val body = request.entity().inputStream()
        val parts = StreamingMultipartFormParts.parse(boundary, body, StandardCharsets.UTF_8)

        for (part in parts) {
            if (part.isFormField) {
                println("Got ${part.fieldName}, value ${part.contentsAsString}")
            } else {
                val fileName = if (part.fileName.length < 3) "too-short" else part.fileName
                val outputFile = File.createTempFile(fileName, "", File("./out"))
                println("Got ${part.fieldName}, writing to $outputFile")
                var count = 0
                FileOutputStream(outputFile).use { out ->
                    while (true) {
                        val aByte = part.inputStream.read()
                        if (aByte < 0) {
                            break
                        }
                        count++
                        out.write(aByte)
                        if ((count % 1000000) == 0) {
                            print(".")
                        }
                    }
                }
                println("finished")
            }
        }
        return Response.response(OK)
//        println("<<$request>>")
//        write(request.entity().toBytes(), File("example.multipart"))
//        return Response.response(OK).entity(
//            page("Uploaded", ul(request.form().map { p -> p.first() })).toCompactHtml()
//        )
    }

    @GET
    @Path("/goodbye/{id:.+}")
    fun withParam(@PathParam("id") id: String): Response {
        println(id)
        return Response.response(OK).entity(
            page("Uploaded", h1(id)).toCompactHtml()
        )
    }
}

