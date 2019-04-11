package com.github.nhirakawa.pbrt.java.parse;

import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nhirakawa.pbrt.java.core.model.ParameterUtils;
import com.github.nhirakawa.pbrt.java.core.model.camera.CameraAdt;
import com.github.nhirakawa.pbrt.java.core.model.camera.CameraAdts;
import com.github.nhirakawa.pbrt.java.core.model.camera.PerspectiveCamera;
import com.github.nhirakawa.pbrt.java.core.model.film.ImageFilm;
import com.github.nhirakawa.pbrt.java.core.model.integrator.IntegratorAdt;
import com.github.nhirakawa.pbrt.java.core.model.integrator.IntegratorAdts;
import com.github.nhirakawa.pbrt.java.core.model.integrator.PathIntegrator;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameter;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameters;
import com.github.nhirakawa.pbrt.java.core.model.sampler.HaltonSampler;
import com.github.nhirakawa.pbrt.java.core.model.sampler.SamplerAdt;
import com.github.nhirakawa.pbrt.java.core.model.sampler.SamplerAdts;
import com.github.nhirakawa.pbrt.java.core.model.shape.ShapeAdt;
import com.github.nhirakawa.pbrt.java.core.model.shape.ShapeAdts;
import com.github.nhirakawa.pbrt.java.core.model.shape.Sphere;
import com.github.nhirakawa.pbrt.java.core.model.shape.TriangleMesh;
import com.github.nhirakawa.pbrt.java.core.model.transform.LookAt;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.BdptIntegratorContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ConeShapeContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.CurveShapeContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.CylinderShapeContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.DirectLightingIntegratorContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.DiskShapeContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.EnvironmentCameraContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.FilmContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.HaltonSamplerContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.HyperboloidShapeContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.LookAtContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.MaxMinDistSamplerContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.MltIntegratorContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.NumberArrayLiteralContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.NumberLiteralContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.OrthographicCameraContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ParaboloidShapeContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ParameterContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ParameterListContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.PathIntegratorContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.PerspectiveCameraContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.RandomSamplerContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.RealisticCameraContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.SobolSamplerContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.SphereShapeContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.SppmIntegratorContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.StratifiedSamplerContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.TriangleMeshShapeContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ValueContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.WhittedIntegratorContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ZeroTwoSequenceSamplerContext;
import com.github.nhirakawa.pbrt.java.parse.factory.CameraFactory;
import com.github.nhirakawa.pbrt.java.parse.factory.IntegratorFactory;
import com.github.nhirakawa.pbrt.java.parse.factory.SamplerFactory;
import com.github.nhirakawa.pbrt.java.parse.factory.ShapeFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.io.Resources;

public class PbrtParseListener extends PbrtBaseListener {

  private static final Logger LOG = LoggerFactory.getLogger(PbrtParseListener.class);

  @Override
  public void exitLookAt(LookAtContext lookAtContext) {
    double lookAtEyeX = Double.parseDouble(lookAtContext.lookAtEyeX().getText());
    double lookAtEyeY = Double.parseDouble(lookAtContext.lookAtEyeY().getText());
    double lookAtEyeZ = Double.parseDouble(lookAtContext.lookAtEyeZ().getText());

    double lookAtPointX = Double.parseDouble(lookAtContext.lookAtPointX().getText());
    double lookAtPointY = Double.parseDouble(lookAtContext.lookAtPointY().getText());
    double lookAtPointZ = Double.parseDouble(lookAtContext.lookAtPointZ().getText());

    double lookAtUpX = Double.parseDouble(lookAtContext.lookAtUpX().getText());
    double lookAtUpY = Double.parseDouble(lookAtContext.lookAtUpY().getText());
    double lookAtUpZ = Double.parseDouble(lookAtContext.lookAtUpZ().getText());

    LookAt lookAt = LookAt.builder()
        .setEyeX(lookAtEyeX)
        .setEyeY(lookAtEyeY)
        .setEyeZ(lookAtEyeZ)
        .setPointX(lookAtPointX)
        .setPointY(lookAtPointY)
        .setPointZ(lookAtPointZ)
        .setUpX(lookAtUpX)
        .setUpY(lookAtUpY)
        .setUpZ(lookAtUpZ)
        .build();
    LOG.debug("LookAt {}", lookAt);

    super.exitLookAt(lookAtContext);
  }

  // Cameras

  @Override
  public void exitPerspectiveCamera(PerspectiveCameraContext ctx) {
    Parameters parameters = toParameters(ctx.parameterList());

    PerspectiveCamera perspectiveCamera = CameraFactory.toPerspectiveCamera(parameters);

    CameraAdt camera = CameraAdts.PERSPECTIVE_CAMERA(perspectiveCamera);
  }

  @Override
  public void exitEnvironmentCamera(EnvironmentCameraContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitRealisticCamera(RealisticCameraContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitOrthographicCamera(OrthographicCameraContext ctx) {
    throw new UnsupportedOperationException();
  }

   // Samplers

  @Override
  public void exitHaltonSampler(HaltonSamplerContext ctx) {
    Parameters parameters = toParameters(ctx.parameterList());
    HaltonSampler haltonSampler = SamplerFactory.toHaltonSampler(parameters);

    SamplerAdt samplerAdt = SamplerAdts.HALTON_SAMPLER(haltonSampler);
  }

  @Override
  public void exitZeroTwoSequenceSampler(ZeroTwoSequenceSamplerContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitMaxMinDistSampler(MaxMinDistSamplerContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitRandomSampler(RandomSamplerContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitSobolSampler(SobolSamplerContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitStratifiedSampler(StratifiedSamplerContext ctx) {
    throw new UnsupportedOperationException();
  }

  // Integrators

  @Override
  public void exitPathIntegrator(PathIntegratorContext ctx) {
    Parameters parameters = toParameters(ctx.parameterList());

    PathIntegrator pathIntegrator = IntegratorFactory.toPathIntegrator(parameters);

    IntegratorAdt integratorAdt = IntegratorAdts.PATH(pathIntegrator);
  }

  @Override
  public void exitBdptIntegrator(BdptIntegratorContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitDirectLightingIntegrator(DirectLightingIntegratorContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitMltIntegrator(MltIntegratorContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitSppmIntegrator(SppmIntegratorContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitWhittedIntegrator(WhittedIntegratorContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitFilm(FilmContext ctx) {
    Parameters parameters = toParameters(ctx.parameterList());

    ImageFilm imageFilm = ImageFilm.from(parameters);

    LOG.info("ImageFilm - {}", imageFilm);

    super.exitFilm(ctx);
  }

  // Shapes

  @Override
  public void exitConeShape(ConeShapeContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitCurveShape(CurveShapeContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitCylinderShape(CylinderShapeContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitDiskShape(DiskShapeContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitHyperboloidShape(HyperboloidShapeContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitParaboloidShape(ParaboloidShapeContext ctx) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void exitSphereShape(SphereShapeContext ctx) {
    Parameters parameters = toParameters(ctx.parameterList());

    Sphere sphere = ShapeFactory.toSphere(parameters);

    ShapeAdt shapeAdt = ShapeAdts.SPHERE(sphere);
  }

  @Override
  public void exitTriangleMeshShape(TriangleMeshShapeContext ctx) {
    Parameters parameters = toParameters(ctx.parameterList());

    TriangleMesh triangleMesh = ShapeFactory.toTriangleMesh(parameters);

    ShapeAdt shapeAdt = ShapeAdts.TRIANGLE_MESH(triangleMesh);
  }

  @Override
  public void exitEveryRule(ParserRuleContext parserRuleContext) {
    super.exitEveryRule(parserRuleContext);
  }

  public static void main(String... args) throws IOException {
    CharStream charStream = CharStreams.fromStream(Resources.getResource("ball.pbrt").openStream());
    PbrtLexer lexer = new PbrtLexer(charStream);
    CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
    commonTokenStream.fill();
    PbrtParser pbrtParser = new PbrtParser(commonTokenStream);
    ParseTree parseTree = pbrtParser.pbrt();
    ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
    PbrtParseListener pbrtParseListener = new PbrtParseListener();
    parseTreeWalker.walk(pbrtParseListener, parseTree);
  }

  private static Parameters toParameters(ParameterListContext parameterListContext) {
    List<Parameter> parameterList = parameterListContext.parameter().stream()
        .map(PbrtParseListener::toParamater)
        .collect(ImmutableList.toImmutableList());

    return Parameters.builder()
        .setParameters(parameterList)
        .build();
  }

  private static Parameter toParamater(ParameterContext parameterContext) {
    Parameter.Builder builder = Parameter.builder()
        .setName(parameterContext.name().getText())
        .setType(parameterContext.type().getText());

    ValueContext valueContext = parameterContext.value();
    if (valueContext.numberArrayLiteral() != null) {
      NumberArrayLiteralContext numberArrayLiteralContext = valueContext.numberArrayLiteral();
      if (numberArrayLiteralContext.singleValueArray() != null) {
        builder.setValue(numberArrayLiteralContext.singleValueArray().numberLiteral().getText());
      } else if (numberArrayLiteralContext.numberLiteral() != null) {
        builder.setValue(numberArrayLiteralContext.numberLiteral().getText());
      } else if (numberArrayLiteralContext.multipleValueArray() != null) {
        String joinedValue = numberArrayLiteralContext.multipleValueArray().numberLiteral().stream()
            .map(NumberLiteralContext::getText)
            .collect(ParameterUtils.collector());
        builder.setValue(joinedValue);
      }
    } else {
      builder.setValue(parameterContext.getText());
    }

    return builder.build();
  }

}
