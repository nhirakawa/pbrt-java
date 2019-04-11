package com.github.nhirakawa.pbrt.java.core.model.shape;

import org.derive4j.Data;

@Data
public abstract class ShapeAdt {

  interface Cases<R> {

    R SPHERE(AbstractSphere sphere);
    R TRIANGLE_MESH(AbstractTriangleMesh triangleMesh);

  }

  public abstract <R> R match(Cases<R> cases);

}
