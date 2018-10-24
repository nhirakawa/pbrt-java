package com.github.nhirakawa.pbrt.java.core.model.integrator;

import java.util.Collections;
import java.util.List;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameters;
import com.google.common.base.Preconditions;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractPathIntegrator implements Integrator {

  enum LightSampleStrategy {
    UNIFORM,
    POWER,
    SPATIAL
  }

  @Override
  @Value.Auxiliary
  public IntegratorType getIntegratorType() {
    return IntegratorType.PATH;
  }

  @Value.Default
  public int getMaxDepth() {
    return 5;
  }

  @Value.Default
  public List<Integer> getPixelBounds() {
    return Collections.emptyList();
  }

  @Value.Default
  public double getRussianRouletteThreshold() {
    return 1;
  }

  @Value.Default
  public LightSampleStrategy getLightSampleStrategy() {
    return LightSampleStrategy.SPATIAL;
  }

  @Value.Check
  public void check() {
    Preconditions.checkState(
        getPixelBounds().isEmpty() || getPixelBounds().size() == 4,
        "pixel bounds must either have size 0 or size 4"
    );
  }

  public static PathIntegrator from(Parameters parameters) {
    return PathIntegrator.builder().build();
  }

}
