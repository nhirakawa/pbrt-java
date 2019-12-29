package com.github.nhirakawa.pbrt.java.core.model;

import java.util.List;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractPbrtScene {

  public abstract List<SceneWideRenderingOption> getSceneWideRenderingOptions();
  public abstract List<SceneObject> getSceneObjects();

}
