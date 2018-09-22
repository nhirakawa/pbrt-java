package com.github.nhirakawa.pbrt.java.core.model.lightsource;

import com.github.nhirakawa.pbrt.java.core.model.Point3;
import com.github.nhirakawa.pbrt.java.core.model.spectrum.Spectrum;

public interface AbstractDistantLightSource extends LightSource {

  @Override
  default LightSourceType getLightSourceType() {
    return LightSourceType.DISTANT;
  }

  Spectrum getRadiance();
  Point3 getFrom();
  Point3 getTo();

}
