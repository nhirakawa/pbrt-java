package com.github.nhirakawa.pbrt.java.core.model;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractPoint3 {

  double getX();
  double getY();
  double getZ();

  @Value.Derived
  default int getXInt() {
    return (int) getX();
  }

  @Value.Derived
  default int getYInt() {
    return (int) getY();
  }

  @Value.Derived
  default int getZInt() {
    return (int) getZ();
  }

}
