package com.github.nhirakawa.pbrt.java.parse;

import org.antlr.v4.gui.TestRig;

public class PbrtParseTest {

  public static void main(String... args) throws Exception {
    doTestRigForActualGrammar();
  }

  private static void doTestRigForActualGrammar() throws Exception {
    new TestRig(
      new String[] {
        "com.github.nhirakawa.pbrt.java.parse.Pbrt",
        "pbrt",
        "-tokens",
        "-gui",
        "-diagnostics",
        "-trace",
        "C:\\Users\\Nick\\workspace\\pbrt-java\\pbrt-java-parse\\src\\main\\resources\\ball.pbrt"
      }
    )
    .process();
  }
}
