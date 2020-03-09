package com.github.nhirakawa.pbrt.java.parse.factory;

import com.github.nhirakawa.pbrt.java.core.model.lightsource.AbstractDistantLightSource;
import com.github.nhirakawa.pbrt.java.core.model.lightsource.AbstractInfiniteLightSource;
import com.github.nhirakawa.pbrt.java.core.model.lightsource.DistantLightSource;
import com.github.nhirakawa.pbrt.java.core.model.lightsource.InfiniteLightSource;
import com.github.nhirakawa.pbrt.java.parse.model.Parameters;

public final class LightSourceFactory {

  private LightSourceFactory() {}

  public static DistantLightSource toDistantLightSource(Parameters parameters) {
    throw new UnsupportedOperationException();
  }

  public static InfiniteLightSource toInfiniteLightSource(
    Parameters parameters
  ) {
    throw new UnsupportedOperationException();
  }
}
