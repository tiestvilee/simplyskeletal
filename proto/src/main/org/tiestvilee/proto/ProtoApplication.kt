package org.tiestvilee.proto

import com.googlecode.utterlyidle.BasePath.basePath
import com.googlecode.utterlyidle.RestApplication
import org.tiestvilee.helloworld.HelloWorldModule

class ProtoApplication() : RestApplication(basePath("")) {
    init {
        add(HelloWorldModule())
    }
}

