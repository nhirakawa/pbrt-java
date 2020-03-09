package com.github.nhirakawa.pbrt.java.core.model.integrator;

import com.github.nhirakawa.immutable.style.ImmutableStyle;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@ImmutableStyle
public abstract class AbstractPathIntegrator implements Integrator {

  enum LightSampleStrategy {
    UNIFORM, POWER, SPATIAL
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
}
