package com.github.nhirakawa.pbrt.java.core.model.material;

import java.util.Optional;

import com.github.nhirakawa.pbrt.java.core.model.texture.Texture;

public interface Material {

  MaterialType getType();

  Optional<? extends Texture> getBumpMapTextureName();

}
