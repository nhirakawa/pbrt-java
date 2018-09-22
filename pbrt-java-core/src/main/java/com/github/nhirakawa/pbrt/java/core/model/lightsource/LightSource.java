package com.github.nhirakawa.pbrt.java.core.model.lightsource;

import org.immutables.value.Value;

import com.github.nhirakawa.pbrt.java.core.model.spectrum.Spectrum;

public interface LightSource {

  @Value.Auxiliary
  LightSourceType getLightSourceType();

  Spectrum getScale();

}
