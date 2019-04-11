package com.github.nhirakawa.pbrt.java.core.model.integrator;

import org.derive4j.Data;

@Data
public abstract class IntegratorAdt {

  interface Cases<R> {

    R PATH(AbstractPathIntegrator pathIntegrator);

  }

  public abstract <R> R match(Cases<R> cases);
}
