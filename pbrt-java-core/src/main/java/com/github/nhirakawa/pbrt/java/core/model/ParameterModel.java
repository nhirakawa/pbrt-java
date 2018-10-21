package com.github.nhirakawa.pbrt.java.core.model;

import java.util.Locale;
import java.util.Optional;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Longs;

@Value.Immutable
@ImmutableStyle
public interface ParameterModel {

  String getName();
  String getType();
  String getValue();

  @Value.Lazy
  default Optional<Double> getAsDouble() {
    if (getType().toLowerCase(Locale.ENGLISH).equals("float")) {
      return Optional.ofNullable(Doubles.tryParse(getValue()));
    }

    return Optional.empty();
  }

  @Value.Lazy
  default Optional<Long> getAsLong() {
    if (getType().toLowerCase(Locale.ENGLISH).equals("integer")) {
      return Optional.ofNullable(Longs.tryParse(getValue()));
    }

    return Optional.empty();
  }

  @Value.Lazy
  default Optional<Boolean> getAsBoolean() {
    if (getType().toLowerCase(Locale.ENGLISH).equals("bool")) {
      return Optional.of(Boolean.valueOf(getValue()));
    }

    return Optional.empty();
  }

}
