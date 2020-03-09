package com.github.nhirakawa.pbrt.java.core.model.sampler;

import org.derive4j.Data;

@Data
public abstract class SamplerAdt {

  interface Cases<R> {
    R HALTON_SAMPLER(AbstractHaltonSampler haltonSampler);
  }

  public abstract <R> R match(Cases<R> cases);
}
