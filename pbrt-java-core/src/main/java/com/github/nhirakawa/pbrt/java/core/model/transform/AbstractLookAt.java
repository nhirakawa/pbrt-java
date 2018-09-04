package com.github.nhirakawa.pbrt.java.core.model.transform;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractLookAt {

  public abstract double getEyeX();
  public abstract double getEyeY();
  public abstract double getEyeZ();

  public abstract double getPointX();
  public abstract double getPointY();
  public abstract double getPointZ();

  public abstract double getUpX();
  public abstract double getUpY();
  public abstract double getUpZ();
}
