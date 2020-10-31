package com.github.nhirakawa.pbrt.java.core.model.geometry;

import org.immutables.value.Value;

import com.github.nhirakawa.pbrt.java.core.model.util.NaNChecker;
import com.google.common.base.Preconditions;

public interface Vector2<T extends Number> {
  T getX();
  T getY();

  @Value.Check
  default void check() {
    if (getX() instanceof Double) {
      Preconditions.checkArgument(!NaNChecker.isNaN((double) getX()));
      Preconditions.checkArgument(!NaNChecker.isNaN((double) getY()));
    } else if (getX() instanceof Float) {
      Preconditions.checkArgument(!NaNChecker.isNaN((float) getX()));
      Preconditions.checkArgument(!NaNChecker.isNaN((float) getY()));
    }
  }
}
