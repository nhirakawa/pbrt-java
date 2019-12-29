package com.github.nhirakawa.pbrt.java.core.model.camera;

import org.derive4j.Data;

@Data
public abstract class CameraAdt {

  interface Cases<R> {

    R PERSPECTIVE_CAMERA(AbstractPerspectiveCamera perspectiveCamera);

  }

  public abstract <R> R match(Cases<R> cases);

}
