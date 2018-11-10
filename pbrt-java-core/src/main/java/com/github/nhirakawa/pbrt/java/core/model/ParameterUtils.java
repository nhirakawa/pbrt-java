package com.github.nhirakawa.pbrt.java.core.model;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public final class ParameterUtils {

  private static final Splitter RAW_INPUT_SPLITTER = Splitter.on("[ ]")
      .omitEmptyStrings()
      .trimResults();

  private static final String SEPARATOR = ";";

  private static final Splitter VALUE_SPLITTER = Splitter.on(SEPARATOR)
      .trimResults();

  private static final Joiner VALUE_JOINER = Joiner.on(SEPARATOR);

  private static final Collector<CharSequence, ?, String> COLLECTOR = Collectors.joining(SEPARATOR);

  private ParameterUtils() {}

  public static String fromRawValue(String rawValue) {
    return VALUE_JOINER.join(RAW_INPUT_SPLITTER.split(rawValue));
  }

  public static Iterable<String> split(String value) {
    return VALUE_SPLITTER.split(value);
  }

  public static Collector<CharSequence, ?, String> collector() {
    return COLLECTOR;
  }
}
