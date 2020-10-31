package com.github.nhirakawa.pbrt.java.core.model.shape;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.nhirakawa.immutable.style.ImmutableStyle;
import org.immutables.value.Value;

@Value.Immutable
@ImmutableStyle
@JsonTypeInfo(use = Id.NAME, include = As.EXISTING_PROPERTY, property = "shapeType")
@JsonSerialize
public interface AbstractSphere extends Shape {
  @Value.Default
  default double getRadius() {
    return 1;
  }

  @Value.Default
  default double getZMin() {
    return -getRadius();
  }

  @Value.Default
  default double getZMax() {
    return getRadius();
  }

  @Value.Default
  default double getPhiMax() {
    return 360;
  }

  @Override
  @Value.Derived
  default ShapeType getShapeType() {
    return ShapeType.SPHERE;
  }
}
