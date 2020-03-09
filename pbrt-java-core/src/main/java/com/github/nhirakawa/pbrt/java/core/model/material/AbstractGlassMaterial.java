package com.github.nhirakawa.pbrt.java.core.model.material;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.github.nhirakawa.pbrt.java.core.model.texture.ConstantTexture;
import com.github.nhirakawa.pbrt.java.core.model.texture.Texture;
import org.immutables.value.Value;

@Value.Immutable
@ImmutableStyle
public interface AbstractGlassMaterial extends Material {
  @Override
  default MaterialType getType() {
    return MaterialType.GLASS;
  }

  @Value.Default
  default Texture getReflectivity() {
    return ConstantTexture.builder().setValue(1).build();
  }

  @Value.Default
  default Texture getTransmissivity() {
    return ConstantTexture.builder().setValue(1).build();
  }

  @Value.Default
  default Texture getInsideIndexOfRefraction() {
    return ConstantTexture.builder().setValue(1.5).build();
  }

  @Value.Default
  default Texture getURoughness() {
    return ConstantTexture.builder().setValue(0).build();
  }

  @Value.Default
  default Texture getVRoughness() {
    return ConstantTexture.builder().setValue(0).build();
  }

  @Value.Default
  default boolean hasRemapRoughness() {
    return true;
  }
}
