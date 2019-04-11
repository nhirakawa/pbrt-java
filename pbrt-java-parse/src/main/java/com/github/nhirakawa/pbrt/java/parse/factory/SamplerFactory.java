package com.github.nhirakawa.pbrt.java.parse.factory;

import java.util.Optional;

import com.github.nhirakawa.pbrt.java.core.model.parse.Parameter;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameters;
import com.github.nhirakawa.pbrt.java.core.model.sampler.HaltonSampler;

public final class SamplerFactory {

  public static HaltonSampler toHaltonSampler(Parameters parameters) {
    HaltonSampler.Builder builder = HaltonSampler.builder();

    Optional<Integer> pixelSamples = parameters.getParameter("pixelsamples").flatMap(Parameter::getAsInt);
    pixelSamples.ifPresent(builder::setNumberOfSamplesPerPixel);

    return builder.build();
  }

}
