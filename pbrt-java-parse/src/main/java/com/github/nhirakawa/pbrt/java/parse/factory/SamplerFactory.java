package com.github.nhirakawa.pbrt.java.parse.factory;

import com.github.nhirakawa.pbrt.java.core.model.sampler.HaltonSampler;
import com.github.nhirakawa.pbrt.java.parse.model.Parameter;
import com.github.nhirakawa.pbrt.java.parse.model.Parameters;
import java.util.Optional;

public final class SamplerFactory {

  public static HaltonSampler toHaltonSampler(Parameters parameters) {
    HaltonSampler.Builder builder = HaltonSampler.builder();

    Optional<Integer> pixelSamples = parameters
      .getParameter("pixelsamples")
      .flatMap(Parameter::getAsInt);
    pixelSamples.ifPresent(builder::setNumberOfSamplesPerPixel);

    return builder.build();
  }
}
