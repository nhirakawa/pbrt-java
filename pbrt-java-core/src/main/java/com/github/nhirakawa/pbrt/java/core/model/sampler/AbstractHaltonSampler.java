package com.github.nhirakawa.pbrt.java.core.model.sampler;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import org.immutables.value.Value;

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
