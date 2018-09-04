package com.github.nhirakawa.pbrt.java.parse;

import com.github.nhirakawa.pbrt.PbrtBaseListener;
import com.github.nhirakawa.pbrt.PbrtParser.CoordinateSystemContext;
import com.github.nhirakawa.pbrt.PbrtParser.CoordinateSystemTransformContext;
import com.github.nhirakawa.pbrt.PbrtParser.LookAtContext;

public class PbrtParseListener extends PbrtBaseListener {

  @Override
  public void enterCoordinateSystem(CoordinateSystemContext coordinateSystemContext) {
    coordinateSystemContext.name().getText();
  }

  @Override
  public void enterCoordinateSystemTransform(CoordinateSystemTransformContext coordinateSystemTransformContext) {
    coordinateSystemTransformContext.name().getText();
  }

  @Override
  public void enterLookAt(LookAtContext lookAtContext) {
    lookAtContext.lookAtEyeX().getText();
  }
}
