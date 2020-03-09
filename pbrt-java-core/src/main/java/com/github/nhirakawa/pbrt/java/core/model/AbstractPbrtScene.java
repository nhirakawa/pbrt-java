package com.github.nhirakawa.pbrt.java.core.model;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractPbrtScene {

  public abstract List<SceneWideRenderingOption> getSceneWideRenderingOptions();

  public abstract List<SceneObject> getSceneObjects();
}
