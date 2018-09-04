package com.github.nhirakawa.pbrt.java.core.model.integrator;

import java.util.Collections;
import java.util.List;

import org.immutables.value.Value;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.google.common.base.Preconditions;

@Value.Immutable
@ImmutableStyle
public interface AbstractPathIntegrator extends Integrator {

  @Override
  @Value.Auxiliary
  default IntegratorType getIntegratorType() {
    return IntegratorType.PATH;
  }

  @Value.Default
  default int getMaxDepth() {
    return 5;
  }

  @Value.Default
  default List<Integer> getPixelBounds() {
    return Collections.emptyList();
  }

  @Value.Default
  default double getRussianRouletteThreshold() {
    return 1;
  }

  @Value.Default
  default LightSampleStrategy getLightSampleStrategy() {
    return LightSampleStrategy.SPATIAL;
  }

  @Value.Check
  default void check() {
    Preconditions.checkState(
        getPixelBounds().isEmpty() || getPixelBounds().size() == 4,
        "pixel bounds must either have size 0 or size 4"
    );
  }

  enum LightSampleStrategy {
    UNIFORM,
    POWER,
    SPATIAL
  }

}
