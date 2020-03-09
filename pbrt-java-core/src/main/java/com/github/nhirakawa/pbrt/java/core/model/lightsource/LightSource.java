package com.github.nhirakawa.pbrt.java.core.model.lightsource;

import com.github.nhirakawa.pbrt.java.core.model.spectrum.Spectrum;
import org.immutables.value.Value;

public interface LightSource {
  @Value.Auxiliary
  LightSourceType getLightSourceType();

  Spectrum getScale();
}
