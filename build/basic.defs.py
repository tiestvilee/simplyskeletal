import glob as gl
import os
import re

def kotlin(name, main_class, dependencies = []):

  kotlin_library(
    name = "{}-kotlin".format(name),
    srcs = glob(["src/main/**/*.kt"]),
    resources = glob(["src/main/**"], excludes = ["src/main/**/*.java", "src/main/**/*.kt"]),
    exported_deps = dependencies + ["//:kotlin-deps"],
    visibility = ["PUBLIC"],
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
    deps = [":{}-kotlin".format(name)],
    tests = [":{}-kotlin-test".format(name)],
    visibility = ["PUBLIC"],
  )

def jar_group(name, prefixes = None, lib_type = "runtime"):
  deps = []
  if not prefixes:
    prefixes = []
  prefixes.append(name)

  for file in gl.iglob("lib/{}/*.jar".format(lib_type)):
    filename = os.path.basename(file)
    if not filename.endswith("sources.jar"):
      for prefix in prefixes:
        if filename.startswith(prefix):
          simplename = re.search("({}-[^-]*).*".format(prefix), filename).group(1)
          deps.append(":" + simplename)
          prebuilt_jar(
            name = simplename,
            binary_jar = file,
          )

  java_library(
    name = name + "-deps",
    visibility = ["PUBLIC"],
    exported_deps = deps,
  )
