package com.github.nhirakawa.pbrt.java.core.model.film;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractImageFilm implements Film {

  @Override
  @Value.Auxiliary
  public FilmType getFilmType() {
    return FilmType.IMAGE;
  }

  @Value.Default
  public String getFilename() {
    return "pbrt.exr";
  }

  @Value.Default
  public int getXResolution() {
    return 640;
  }

  @Value.Default
  public int getYResolution() {
    return 480;
  }

  public abstract List<Double> getCropWindow();

  @Value.Default
  public double getScale() {
    return 1;
  }

  @Value.Default
  public double getMaxSampleLuminance() {
    return Double.POSITIVE_INFINITY;
  }

  @Value.Default
  public double getDiagonal() {
    return 35;
  }
}
