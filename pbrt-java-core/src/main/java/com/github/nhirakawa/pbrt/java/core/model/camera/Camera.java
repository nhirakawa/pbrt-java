package com.github.nhirakawa.pbrt.java.core.model.camera;

import org.immutables.value.Value;

public interface Camera {
  CameraType getCameraType();

  @Value.Default
  default double getShutterOpen() {
    return 0;
  }

  @Value.Default
  default double getShutterClose() {
    return 1;
  }
}
