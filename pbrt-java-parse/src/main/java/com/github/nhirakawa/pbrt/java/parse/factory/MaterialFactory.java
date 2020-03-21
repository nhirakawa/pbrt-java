package com.github.nhirakawa.pbrt.java.parse.factory;

import com.github.nhirakawa.pbrt.java.core.model.material.GlassMaterial;
import com.github.nhirakawa.pbrt.java.parse.model.Parameters;

public final class MaterialFactory {

  private MaterialFactory() {
    throw new UnsupportedOperationException();
  }

  public static GlassMaterial toGlassMaterial(Parameters parameters) {
    return GlassMaterial.builder().build();
  }

}
