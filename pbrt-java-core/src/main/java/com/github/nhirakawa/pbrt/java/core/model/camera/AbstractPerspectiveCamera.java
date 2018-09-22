package com.github.nhirakawa.pbrt.java.core.model.camera;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractPerspectiveCamera extends Camera {

  @Override
  @Value.Auxiliary
  default CameraType getCameraType() {
    return CameraType.PERSPECTIVE;
  }

  float getFrameAspectRatio();
  float getScreenWindow();

  @Value.Default
  default double getLensRadius() {
    return 0;
  }

  @Value.Default
  default double getFocalDistance() {
    return Math.pow(10, 30);
  }

  @Value.Default
  default double getFov() {
    return 90;
  }

  double getHalfFov();

}
