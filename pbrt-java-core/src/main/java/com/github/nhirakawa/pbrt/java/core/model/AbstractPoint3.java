package com.github.nhirakawa.pbrt.java.core.model;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.google.common.base.Preconditions;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractPoint3 {

  public abstract double getX();

  public abstract double getY();

  public abstract double getZ();

  @Value.Derived
  public int getXInt() {
    return (int) getX();
  }

  @Value.Derived
  public int getYInt() {
    return (int) getY();
  }

  @Value.Derived
  public int getZInt() {
    return (int) getZ();
  }

  public static Point3 fromInts(List<Integer> coordinates) {
    ensureCorrectNumberOfElements(coordinates);
    return Point3
      .builder()
      .setX(coordinates.get(0))
      .setY(coordinates.get(1))
      .setZ(coordinates.get(2))
      .build();
  }

  public static Point3 fromDoubles(List<Double> coordinates) {
    ensureCorrectNumberOfElements(coordinates);
    return Point3
      .builder()
      .setX(coordinates.get(0))
      .setY(coordinates.get(1))
      .setZ(coordinates.get(2))
      .build();
  }

  private static void ensureCorrectNumberOfElements(List<?> points) {
    Preconditions.checkState(
      points.size() == 3,
      "Must pass in exactly 3 numbers. %s",
      points
    );
  }
}
