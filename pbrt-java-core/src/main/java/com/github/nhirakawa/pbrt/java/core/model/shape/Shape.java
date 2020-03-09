package com.github.nhirakawa.pbrt.java.core.model.shape;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import org.immutables.value.Value;

@JsonSubTypes({ @Type(name = "SPHERE", value = Sphere.class) })
public interface Shape {
  @Value.Auxiliary
  ShapeType getShapeType();
}
