package com.github.nhirakawa.pbrt.java.core.model.lightsource;

import org.derive4j.Data;

@Data
public abstract class LightSourceAdt {

  interface Cases<R> {
    R DISTANT(AbstractDistantLightSource distantLightSource);
    R INFINITE(AbstractInfiniteLightSource infiniteLightSource);
  }

  public abstract <R> R match(Cases<R> cases);
}
