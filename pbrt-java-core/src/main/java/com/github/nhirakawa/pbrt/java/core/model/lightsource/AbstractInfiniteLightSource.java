package com.github.nhirakawa.pbrt.java.core.model.lightsource;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.github.nhirakawa.pbrt.java.core.model.spectrum.Spectrum;
import java.util.Optional;
import org.immutables.value.Value;

@Value.Immutable
@ImmutableStyle
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
