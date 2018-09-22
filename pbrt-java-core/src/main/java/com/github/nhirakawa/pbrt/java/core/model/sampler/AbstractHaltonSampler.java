package com.github.nhirakawa.pbrt.java.core.model.sampler;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractHaltonSampler extends Sampler {

  @Override
  default SamplerType getSamplerType() {
    return SamplerType.HALTON;
  }

  @Value.Default
  default int getNumberOfSamplesPerPixel() {
    return 16;
  }

}
