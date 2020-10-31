package com.github.nhirakawa.pbrt.java.parse.factory;

import com.github.nhirakawa.pbrt.java.core.model.camera.PerspectiveCamera;
import com.github.nhirakawa.pbrt.java.parse.model.Parameter;
import com.github.nhirakawa.pbrt.java.parse.model.Parameters;
import java.util.Optional;

public final class CameraFactory {

  public static PerspectiveCamera toPerspectiveCamera(Parameters parameters) {
    PerspectiveCamera.Builder builder = PerspectiveCamera.builder();

    Optional<Double> fov = parameters.getParameter("fov").flatMap(Parameter::getAsDouble);
    fov.ifPresent(builder::setFov);

    return builder.build();
  }
}
