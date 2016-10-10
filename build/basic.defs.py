def kotlin(name, main_class, dependencies = []):

  kotlin_library(
    name = "{}-kotlin".format(name),
    srcs = glob(["src/main/**/*.kt"]),
    resources = glob(["src/main/**"], excludes = ["src/main/**/*.java", "src/main/**/*.kt"]),
    deps = dependencies + ["//:kotlin-deps"],
  )

  kotlin_test(
    name = "{}-kotlin-test".format(name),
    srcs = glob(["src/test/**/*.kt"]),
    resources = glob(["src/test/**"], excludes = ["src/test/**/*.java", "src/test/**/*.kt"]),
    deps = dependencies + ["//:test-deps", ":{}-kotlin".format(name)],
  )

  java_binary(
    name = "{}-app".format(name),
    main_class = main_class,
    deps = [":{}-kotlin-test".format(name)],
    visibility = ["PUBLIC"],
  )