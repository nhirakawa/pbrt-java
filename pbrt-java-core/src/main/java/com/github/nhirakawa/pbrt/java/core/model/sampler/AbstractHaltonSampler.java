package com.github.nhirakawa.pbrt.java.core.model.sampler;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractHaltonSampler implements Sampler {

  @Override
  public SamplerType getSamplerType() {
    return SamplerType.HALTON;
  }

  @Value.Default
  public int getNumberOfSamplesPerPixel() {
    return 16;
  }

}
