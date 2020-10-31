package com.github.nhirakawa.pbrt.java.parse;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    PbrtNumberParser parser = new PbrtNumberParser(consumer);
    TestUtil.doParse(input, () -> parser);
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
