package com.github.nhirakawa.pbrt.java.core.model.film;

import java.util.List;
import java.util.Optional;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameter;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameters;

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

  public static ImageFilm from(Parameters parameters) {
    ImageFilm.Builder builder = ImageFilm.builder();

    Optional<String> filename = parameters.getParameter("filename").map(Parameter::getValue);
    filename.ifPresent(builder::setFilename);

    Optional<Integer> xResolution = parameters.getParameter("xresolution").flatMap(Parameter::getAsInt);
    xResolution.ifPresent(builder::setXResolution);

    Optional<Integer> yResolution = parameters.getParameter("yresolution").flatMap(Parameter::getAsInt);
    yResolution.ifPresent(builder::setYResolution);

    return builder.build();
  }
}
