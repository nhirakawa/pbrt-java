package com.github.nhirakawa.pbrt.java.core.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.google.common.collect.Maps;

@Value.Immutable
@ImmutableStyle
public abstract class ParametersModel {

  public abstract List<Parameter> getParameters();

  @Value.Derived
  @JsonIgnore
  public Map<String, Parameter> getParameterByName() {
    return Maps.uniqueIndex(getParameters(), Parameter::getName);
  }

  public Optional<Parameter> getParameter(String name) {
    return Optional.ofNullable(getParameterByName().get(name));
  }

}
