package com.github.nhirakawa.pbrt.java.parse.factory;

import com.github.nhirakawa.pbrt.java.core.model.integrator.PathIntegrator;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameters;

public final class IntegratorFactory {

  public static PathIntegrator toPathIntegrator(Parameters parameters) {
    return PathIntegrator.builder().build();
  }

}
