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
        assertThat(bodyAt(get("/hello")), equalTo("""<!DOCTYPE html>
<html>
  <head>
    <title>Hello World</title>
    <link href="https://gitcdn.link/repo/Chalarangelo/mini.css/master/dist/mini-default.min.css" rel="stylesheet">
    <style></style>
  </head>
  <body>
    <header class="sticky row">
      <div class="col-sm col-md-10, col-md-offset-1">
        <a href="/editor/manuscriptTable" role="button">Manuscripts</a>
        <a href="asXml" role="button">As XML</a>
        <a href="asPdf" role="button">As PDF</a>
      </div>
    </header>
    <div class="container">
      <div class="row">
        <div class="col-md-4">
        </div>
        <div class="col-md-4">
          <div class="fluid card">
            <div class="section">
              <h3>Hello World</h3>
            </div>
            <form action enctype="multipart/form-data" method="POST">
              <fieldset>
                <legend>Create a new article</legend>
                <div class="row responsive-label">
                  <div class="col-md-5">
                    <label for="articleType">Article Type</label>
                  </div>
                  <div class="col-md">
                    <select id="articleType" name="articleType">
                      <option>review</option>
                      <option>obituary</option>
                    </select>
                  </div>
                </div>
                <div class="row responsive-label">
                  <div class="col-md-5">
                    <label for="uploadManuscript">Upload Manuscript</label>
                  </div>
                  <div class="col-md">
                    <input id="uploadManuscript" multiple name="uploadManuscript" type="file">
                    <label class="button" for="uploadManuscript">Upload</label>
                  </div>
                </div>
                <div class="row responsive-label">
                  <div class="col-md-5">
                    <span>
                    </span>
                  </div>
                  <div class="col-md">
                    <button type="submit">Create</button>
                  </div>
                </div>
              </fieldset>
            </form>
          </div>
        </div>
      </div>
    </div>
    <footer>
      <div class="col-sm col-md-10 col-md-offset-1">
        <p>Copyright &copy; SpringerNature 2017</p>
      </div>
    </footer>
    <script></script>
  </body>
</html>"""))
    }

}