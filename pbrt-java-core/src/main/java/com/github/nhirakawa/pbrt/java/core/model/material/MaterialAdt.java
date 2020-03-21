package com.github.nhirakawa.pbrt.java.core.model.material;

import org.derive4j.Data;

@Data
public abstract class MaterialAdt {

  interface Cases<R> {

    R GLASS(AbstractGlassMaterial glassMaterial);

  }

  public abstract <R> R match(Cases<R> cases);

}
