package com.github.nhirakawa.pbrt.java.core.model.parse;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.github.nhirakawa.pbrt.java.core.model.ParameterUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;

@Value.Immutable
@ImmutableStyle
public abstract class ParameterModel {

  public abstract String getName();
  public abstract String getType();
  public abstract String getValue();

  @Value.Lazy
  public Optional<Double> getAsDouble() {
    if (getType().toLowerCase(Locale.ENGLISH).equals("float")) {
      return Optional.ofNullable(Doubles.tryParse(getValue()));
    }

    return Optional.empty();
  }

  @Value.Lazy
  public Optional<Integer> getAsInt() {
    if (getType().toLowerCase(Locale.ENGLISH).equals("integer")) {
      return Optional.ofNullable(Ints.tryParse(getValue()));
    }

    return Optional.empty();
  }

  @Value.Lazy
  public Optional<Long> getAsLong() {
    if (getType().toLowerCase(Locale.ENGLISH).equals("integer")) {
      return Optional.ofNullable(Longs.tryParse(getValue()));
    }

    return Optional.empty();
  }

  @Value.Lazy
  public Optional<Boolean> getAsBoolean() {
    if (getType().toLowerCase(Locale.ENGLISH).equals("bool")) {
      return Optional.of(Boolean.valueOf(getValue()));
    }

    return Optional.empty();
  }

  @Value.Lazy
  public List<Integer> getAsListOfIntegers() {
    return Streams.stream(ParameterUtils.split(getValue()))
        .map(Integer::valueOf)
        .collect(ImmutableList.toImmutableList());
  }

}
