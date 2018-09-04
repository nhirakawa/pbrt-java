package com.github.nhirakawa.pbrt.java.core.model.texture;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractConstantTexture extends Texture {

  @Override
  default TextureType getTextureType() {
    return TextureType.CONSTANT;
  }

  @Value.Default
  default double getValue() {
    return 1;
  }
}
