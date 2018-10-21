package com.github.nhirakawa.pbrt.java.core.model.sampler;

import java.util.Optional;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameter;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameters;

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

  public static HaltonSampler from(Parameters parameters) {
    HaltonSampler.Builder builder = HaltonSampler.builder();

    Optional<Integer> pixelSamples = parameters.getParameter("pixelsamples").flatMap(Parameter::getAsInt);
    pixelSamples.ifPresent(builder::setNumberOfSamplesPerPixel);

    return builder.build();
  }

}
