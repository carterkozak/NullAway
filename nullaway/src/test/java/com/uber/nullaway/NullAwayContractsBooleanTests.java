package com.uber.nullaway;

import com.google.errorprone.CompilationTestHelper;
import java.util.Arrays;
import org.junit.Test;

public class NullAwayContractsBooleanTests extends NullAwayTestsBase {

  @Test
  public void nonNullCheckIsTrueIsNotNullable() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    Validation.checkTrue(o1 != null);",
            "    return o1.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void nonNullCheckIsTrueIsNotNullableReversed() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    Validation.checkTrue(null != o1);",
            "    return o1.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void nullCheckIsFalseIsNotNullable() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    Validation.checkFalse(o1 == null);",
            "    return o1.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void nullCheckIsFalseIsNotNullableReversed() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    Validation.checkFalse(null == o1);",
            "    return o1.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void nullCheckIsTrueIsNull() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    Validation.checkTrue(o1 == null);",
            "    // BUG: Diagnostic contains: dereferenced expression",
            "    return o1.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void nullCheckIsTrueIsNullReversed() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    Validation.checkTrue(null == o1);",
            "    // BUG: Diagnostic contains: dereferenced expression",
            "    return o1.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void nonNullCheckIsFalseIsNull() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    Validation.checkFalse(o1 != null);",
            "    // BUG: Diagnostic contains: dereferenced expression",
            "    return o1.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void nonNullCheckIsFalseIsNullReversed() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    Validation.checkFalse(null != o1);",
            "    // BUG: Diagnostic contains: dereferenced expression",
            "    return o1.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void compositeNullCheckAndStringEquality() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    Validation.checkTrue(o1 != null && o1.toString().equals(\"\"));",
            "    return o1.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void compositeNullCheckMultipleNonNull() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1, @Nullable Object o2) {",
            "    Validation.checkTrue(o1 != null && o2 != null);",
            "    return o1.toString() + o2.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void compositeNullCheckFalseAndStringEquality() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    Validation.checkFalse(o1 == null || o1.toString().equals(\"\"));",
            "    return o1.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void compositeNullCheckFalseMultipleNonNull() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1, @Nullable Object o2) {",
            "    Validation.checkFalse(o1 == null || o2 == null);",
            "    return o1.toString() + o2.toString();",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void identityNotNull() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    if (Validation.identity(null != o1)) {",
            "      return o1.toString();",
            "    } else {",
            "      // BUG: Diagnostic contains: dereferenced expression",
            "      return o1.toString();",
            "    }",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void invertIsNull() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    if (Validation.invert(null == o1)) {",
            "      return o1.toString();",
            "    } else {",
            "      // BUG: Diagnostic contains: dereferenced expression",
            "      return o1.toString();",
            "    }",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void identityIsNull() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    if (Validation.identity(null == o1)) {",
            "      // BUG: Diagnostic contains: dereferenced expression",
            "      return o1.toString();",
            "    } else {",
            "      return o1.toString();",
            "    }",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void invertNotNull() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "class Test {",
            "  String test(@Nullable Object o1) {",
            "    if (Validation.invert(null != o1)) {",
            "      // BUG: Diagnostic contains: dereferenced expression",
            "      return o1.toString();",
            "    } else {",
            "      return o1.toString();",
            "    }",
            "  }",
            "}")
        .doTest();
  }

  @Test
  public void checkAndReturn() {
    helper()
        .addSourceLines(
            "Test.java",
            "package com.uber;",
            "import javax.annotation.Nullable;",
            "import org.jetbrains.annotations.Contract;",
            "class Test {",
            "  @Contract(\"false -> fail\")",
            "  static boolean checkAndReturn(boolean value) {",
            "    if (!value) {",
            "      throw new RuntimeException();",
            "    }",
            "    return true;",
            "  }",
            "  String test1(@Nullable Object o1, @Nullable Object o2) {",
            "    if (checkAndReturn(o1 != null) && o2 != null) {",
            "      return o1.toString() + o2.toString();",
            "    } else {",
            "      return o1.toString() + ",
            "      // BUG: Diagnostic contains: dereferenced expression",
            "        o2.toString();",
            "    }",
            "  }",
            "  boolean test2(@Nullable Object o1, @Nullable Object o2) {",
            "    return checkAndReturn(o1 != null) && o1.toString().isEmpty();",
            "  }",
            "  boolean test3(@Nullable Object o1, @Nullable Object o2) {",
            "    return checkAndReturn(o1 == null) ",
            "      // BUG: Diagnostic contains: dereferenced expression",
            "      && o1.toString().isEmpty();",
            "  }",
            "}")
        .doTest();
  }

  private CompilationTestHelper helper() {
    return makeTestHelperWithArgs(
            Arrays.asList(
                "-d",
                temporaryFolder.getRoot().getAbsolutePath(),
                "-XepOpt:NullAway:AnnotatedPackages=com.uber"))
        .addSourceLines(
            "Validation.java",
            "package com.uber;",
            "import org.jetbrains.annotations.Contract;",
            "public final class Validation {",
            "  @Contract(\"false -> fail\")",
            "  static void checkTrue(boolean value) {",
            "    if (!value) throw new RuntimeException();",
            "  }",
            "  @Contract(\"true -> fail\")",
            "  static void checkFalse(boolean value) {",
            "    if (!value) throw new RuntimeException();",
            "  }",
            "  @Contract(\"true -> true; false -> false\")",
            "  static boolean identity(boolean value) {",
            "    return value;",
            "  }",
            "  @Contract(\"true -> false; false -> true\")",
            "  static boolean invert(boolean value) {",
            "    return !value;",
            "  }",
            "}");
  }
}