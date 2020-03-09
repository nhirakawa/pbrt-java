package com.github.nhirakawa.pbrt.java.parse.factory;

import com.github.nhirakawa.pbrt.java.core.model.film.ImageFilm;
import com.github.nhirakawa.pbrt.java.parse.model.Parameter;
import com.github.nhirakawa.pbrt.java.parse.model.Parameters;
import java.util.Optional;

public final class ImageFactory {

  public static ImageFilm toImage(Parameters parameters) {
    ImageFilm.Builder builder = ImageFilm.builder();

    Optional<String> filename = parameters
      .getParameter("filename")
      .map(Parameter::getValue);
    filename.ifPresent(builder::setFilename);

    Optional<Integer> xResolution = parameters
      .getParameter("xresolution")
      .flatMap(Parameter::getAsInt);
    xResolution.ifPresent(builder::setXResolution);

    Optional<Integer> yResolution = parameters
      .getParameter("yresolution")
      .flatMap(Parameter::getAsInt);
    yResolution.ifPresent(builder::setYResolution);

    return builder.build();
  }
}
