package com.github.nhirakawa.pbrt.java.parse;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

import com.github.nhirakawa.pbrt.java.parse.PbrtParser.NumberLiteralContext;

public class PbrtParserNumberTest {

  @Test
  public void itParsesNumberWithDigitsOnBothSidesOfDecimalPoint() {
    List<Double> doubles = new ArrayList<>();

    doParse("300.10", doubles::add);

    assertThat(doubles).containsExactly(300.1d);
  }

  @Test
  public void itParsesInteger() {
    List<Double> doubles = new ArrayList<>();

    doParse("300", doubles::add);

    assertThat(doubles).containsExactly(300d);
  }

  @Test
  public void itParsesNumberWithoutDigitsBeforeDecimalPoint() {
    List<Double> doubles = new ArrayList<>();

    doParse(".7", doubles::add);

    assertThat(doubles).containsExactly(.7);
  }

  @Test
  public void itParsesNegativeDecimal() {
    List<Double> doubles = new ArrayList<>();

    doParse("-1.9", doubles::add);

    assertThat(doubles).containsExactly(-1.9d);
  }

  @Test
  public void itParsesNegativeInteger() {
    List<Double> doubles = new ArrayList<>();

    doParse("-40", doubles::add);

    assertThat(doubles).containsExactly(-40d);
  }

  private void doParse(String input, Consumer<Double> consumer) {
    CharStream charStream = CharStreams.fromString(input);
    PbrtLexer lexer = new PbrtLexer(charStream);
    CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
    commonTokenStream.fill();
    PbrtParser pbrtParser = new PbrtParser(commonTokenStream);
    ParseTree parseTree = pbrtParser.numberLiteral();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    PbrtBaseListener pbrtParseListener = new PbrtNumberParser(consumer);
    parseTreeWalker.walk(pbrtParseListener, parseTree);
  }

  private static class PbrtNumberParser extends PbrtBaseListener {
    private final Consumer<Double> consumer;

    private PbrtNumberParser(Consumer<Double> consumer) {
      this.consumer = consumer;
    }

    @Override
    public void exitNumberLiteral(NumberLiteralContext ctx) {
      double d = Double.parseDouble(ctx.getText());
      consumer.accept(d);
    }
  }
}
