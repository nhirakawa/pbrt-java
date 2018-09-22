package com.github.nhirakawa.pbrt.java.core.model.film;

import java.util.List;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;

@Value.Immutable
@ImmutableStyle
public interface AbstractImageFilm extends Film {

  @Override
  @Value.Auxiliary
  default FilmType getFilmType() {
    return FilmType.IMAGE;
  }

  @Value.Default
  default String getFilename() {
    return "pbrt.exr";
  }

  @Value.Default
  default int getXResolution() {
    return 640;
  }

  @Value.Default
  default int getYResolution() {
    return 480;
  }

  List<Double> getCropWindow();

  @Value.Default
  default double getScale() {
    return 1;
  }

  @Value.Default
  default double getMaxSampleLuminance() {
    return Double.POSITIVE_INFINITY;
  }

  @Value.Default
  default double getDiagonal() {
    return 35;
  }
}
