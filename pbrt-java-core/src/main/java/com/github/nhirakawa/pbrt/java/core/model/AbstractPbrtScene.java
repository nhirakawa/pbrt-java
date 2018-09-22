package com.github.nhirakawa.pbrt.java.core.model;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractPbrtScene {

  public abstract SceneWideRenderingOption getSceneWideRenderingOptions();
  public abstract World getWorld();

}
