package com.github.nhirakawa.pbrt.java.core.model.camera;

import java.util.Optional;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameter;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameters;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractPerspectiveCamera implements Camera {

  @Override
  @Value.Auxiliary
  public CameraType getCameraType() {
    return CameraType.PERSPECTIVE;
  }

  public abstract Optional<Double> getFrameAspectRatio();
  public abstract Optional<Double> getScreenWindow();

  @Value.Default
  public double getLensRadius() {
    return 0;
  }

  @Value.Default
  public double getFocalDistance() {
    return Math.pow(10, 30);
  }

  @Value.Default
  public double getFov() {
    return 90;
  }

  public abstract Optional<Double> getHalfFov();

  public static PerspectiveCamera from(Parameters parameters) {
    PerspectiveCamera.Builder builder = PerspectiveCamera.builder();

    Optional<Double> fov = parameters.getParameter("fov").flatMap(Parameter::getAsDouble);
    fov.ifPresent(builder::setFov);

    return builder.build();
  }

}
