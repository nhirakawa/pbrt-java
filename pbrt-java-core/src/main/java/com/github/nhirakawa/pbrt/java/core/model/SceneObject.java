package com.github.nhirakawa.pbrt.java.core.model;

import com.github.nhirakawa.pbrt.java.core.model.lightsource.LightSourceAdt;
import com.github.nhirakawa.pbrt.java.core.model.shape.ShapeAdt;
import org.derive4j.Data;

@Data
public abstract class SceneObject {

  interface Cases<R> {
    R SHAPE(ShapeAdt shapeAdt);
    R LIGHT_SOURCE(LightSourceAdt lightSource);
  }

  public abstract <R> R match(Cases<R> cases);
}
