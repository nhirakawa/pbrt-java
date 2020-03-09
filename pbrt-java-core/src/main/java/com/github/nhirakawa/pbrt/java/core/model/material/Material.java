package com.github.nhirakawa.pbrt.java.core.model.material;

import com.github.nhirakawa.pbrt.java.core.model.texture.Texture;
import java.util.Optional;

public interface Material {
  MaterialType getType();

  Optional<? extends Texture> getBumpMapTextureName();
}
