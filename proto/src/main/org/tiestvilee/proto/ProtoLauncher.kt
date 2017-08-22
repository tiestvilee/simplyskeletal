package org.tiestvilee.proto

import com.googlecode.utterlyidle.ApplicationBuilder.application
import com.googlecode.utterlyidle.ServerConfiguration.defaultConfiguration
import com.googlecode.utterlyidle.jetty.eclipse.RestServer

object ProtoLauncher {
    @JvmStatic fun main(args: Array<String>) {
        val app = ProtoApplication()

        Runtime.getRuntime().addShutdownHook(Thread {
            println("shutting down " + app.javaClass.getSimpleName())
            try {
                app.stop()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        application(app).start(
            defaultConfiguration()
                .serverClass(RestServer::class.java)
                .port(1338)
        )
    }
}