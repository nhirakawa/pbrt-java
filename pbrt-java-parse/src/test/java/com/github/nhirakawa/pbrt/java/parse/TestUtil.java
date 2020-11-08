package com.github.nhirakawa.pbrt.java.parse;

import java.util.function.Supplier;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public final class TestUtil {

  private TestUtil() {
    throw new UnsupportedOperationException();
  }

  public static <T extends PbrtBaseListener> void doParse(
    String input,
    Supplier<T> supplier
  ) {
    CharStream charStream = CharStreams.fromString(input);
    PbrtLexer lexer = new PbrtLexer(charStream);
    CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
    commonTokenStream.fill();
    PbrtParser pbrtParser = new PbrtParser(commonTokenStream);
    ParseTree parseTree = pbrtParser.numberLiteral();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    parseTreeWalker.walk(supplier.get(), parseTree);
  }
}
