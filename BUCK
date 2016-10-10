
jar_group("kotlin")
jar_group("utterlyidle", ["totallylazy", "yadic"])
jar_group("jetty", ["javax.servlet-api"])
jar_group("junit", ["javax.servlet-api"])

prebuilt_jar(
  name = "junit",
  binary_jar = "lib/build/junit-4.12.jar",
  visibility = ['PUBLIC']
)

prebuilt_jar(
  name = "hamcrest-core",
  binary_jar = "lib/build/hamcrest-core-1.3.jar",
  visibility = ['PUBLIC']
)

prebuilt_jar(
  name = "hamkrest",
  binary_jar = "lib/build/hamkrest-1.2.2.0.jar",
  visibility = ['PUBLIC']
)

java_library(
  name = "test-deps",
  visibility = ["PUBLIC"],
  exported_deps = [":junit", ":hamcrest-core", ":hamkrest"],
)