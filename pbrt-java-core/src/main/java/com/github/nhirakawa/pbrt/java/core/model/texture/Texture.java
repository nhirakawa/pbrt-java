package com.github.nhirakawa.pbrt.java.core.model.texture;

import java.util.Optional;

import org.immutables.value.Value;

public interface Texture {

  @Value.Auxiliary
  TextureType getTextureType();

  Optional<String> getName();


}
