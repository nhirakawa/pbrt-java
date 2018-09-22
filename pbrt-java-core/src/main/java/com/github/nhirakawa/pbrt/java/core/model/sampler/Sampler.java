package com.github.nhirakawa.pbrt.java.core.model.sampler;

import org.immutables.value.Value;

public interface Sampler {

  @Value.Auxiliary
  SamplerType getSamplerType();
}
