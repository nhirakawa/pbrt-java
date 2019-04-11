package com.github.nhirakawa.pbrt.java.parse.factory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.github.nhirakawa.pbrt.java.core.model.Point3;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameter;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameters;
import com.github.nhirakawa.pbrt.java.core.model.shape.Sphere;
import com.github.nhirakawa.pbrt.java.core.model.shape.TriangleMesh;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public final class ShapeFactory {

  public static Sphere toSphere(Parameters parameters) {
    Optional<Double> radius = parameters.getParameter("radius").flatMap(Parameter::getAsDouble);

    Sphere.Builder builder = Sphere.builder();
    radius.ifPresent(builder::setRadius);

    return builder.build();
  }

  public static TriangleMesh toTriangleMesh(Parameters parameters) {
    TriangleMesh.Builder builder = TriangleMesh.builder();

    Optional<List<Integer>> indices = parameters.getParameter("indices").map(Parameter::getAsListOfIntegers);
    indices.ifPresent(builder::addAllIndices);

    List<Integer> rawCoordinates = parameters.getParameter("P").map(Parameter::getAsListOfIntegers).orElse(Collections.emptyList());
    List<List<Integer>> partitionedCoordinates = Lists.partition(rawCoordinates, 3);
    List<Point3> points = partitionedCoordinates.stream()
        .map(Point3::fromInts)
        .collect(ImmutableList.toImmutableList());

    return TriangleMesh.builder()
        .setIndices(indices.get())
        .setPoints(points)
        .build();
  }
}
