package com.github.nhirakawa.pbrt.java.core.model.lightsource;

import java.util.Optional;

import org.immutables.value.Value;

import com.github.nhirakawa.pbrt.java.core.model.spectrum.Spectrum;

public interface AbstractInfiniteLightSource extends LightSource {

  @Override
  default LightSourceType getLightSourceType() {
    return LightSourceType.INFINITE;
  }

  Spectrum getRadianceScale();

  @Value.Default
  default int getSamples() {
    return 1;
  }

  Optional<String> getMapName();
}
