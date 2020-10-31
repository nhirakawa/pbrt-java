package com.github.nhirakawa.pbrt.java.core.model.shape;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.github.nhirakawa.pbrt.java.core.model.Point3;
import com.github.nhirakawa.pbrt.java.core.model.texture.Texture;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractTriangleMesh implements Shape {

  @Override
  @Value.Derived
  public ShapeType getShapeType() {
    return ShapeType.TRIANGLEMESH;
  }

  public abstract List<Integer> getIndices();

  public abstract List<Point3> getPoints();

  public abstract List<Void> getNormals();

  public abstract List<Void> getTangents();

  public abstract List<Double> getTextureCoordinates();

  public abstract Optional<Texture> getAlphaTexture();

  public abstract Optional<Texture> getShadowAlphaTexture();

  @Value.Check
  public void check() {
    Preconditions.checkState(!getIndices().isEmpty(), "Must provide at least one index");

    Preconditions.checkState(
      getIndices().size() % 3 == 0,
      "Nunber of indicies must be a multiple of 3"
    );

    Preconditions.checkState(!getPoints().isEmpty(), "Must provide at least one point");

    int maxIndex = getIndices().stream().max(Integer::compareTo).get();
    Preconditions.checkState(
      getPoints().size() > maxIndex,
      "Index %s is out of bounds",
      maxIndex
    );
  }
}
