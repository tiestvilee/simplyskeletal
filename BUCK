prebuilt_jar(
  name = "kotlin-runtime",
  binary_jar = "lib/runtime/kotlin-runtime-1.0.4.jar",
  visibility = ['PUBLIC']
)

prebuilt_jar(
  name = "kotlin-reflect",
  binary_jar = "lib/runtime/kotlin-reflect-1.0.4.jar",
  visibility = ['PUBLIC']
)

java_library(
  name = "kotlin-deps",
  visibility = ["PUBLIC"],
  exported_deps = [":kotlin-reflect", ":kotlin-runtime"],
)

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