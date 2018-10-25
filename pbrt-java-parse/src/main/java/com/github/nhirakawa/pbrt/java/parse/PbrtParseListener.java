package com.github.nhirakawa.pbrt.java.parse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nhirakawa.pbrt.java.core.model.ParameterUtils;
import com.github.nhirakawa.pbrt.java.core.model.Point3;
import com.github.nhirakawa.pbrt.java.core.model.camera.Camera;
import com.github.nhirakawa.pbrt.java.core.model.camera.CameraType;
import com.github.nhirakawa.pbrt.java.core.model.camera.PerspectiveCamera;
import com.github.nhirakawa.pbrt.java.core.model.film.ImageFilm;
import com.github.nhirakawa.pbrt.java.core.model.integrator.Integrator;
import com.github.nhirakawa.pbrt.java.core.model.integrator.PathIntegrator;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameter;
import com.github.nhirakawa.pbrt.java.core.model.parse.Parameters;
import com.github.nhirakawa.pbrt.java.core.model.sampler.HaltonSampler;
import com.github.nhirakawa.pbrt.java.core.model.sampler.Sampler;
import com.github.nhirakawa.pbrt.java.core.model.sampler.SamplerType;
import com.github.nhirakawa.pbrt.java.core.model.shape.Shape;
import com.github.nhirakawa.pbrt.java.core.model.shape.ShapeType;
import com.github.nhirakawa.pbrt.java.core.model.shape.Sphere;
import com.github.nhirakawa.pbrt.java.core.model.shape.TriangleMesh;
import com.github.nhirakawa.pbrt.java.core.model.transform.LookAt;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.CameraContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.FilmContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.IntegratorContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.LookAtContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.NumberArrayLiteralContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.NumberLiteralContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ParameterContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ParameterListContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.SamplerContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ShapeContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ValueContext;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
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

  @Override
  public void exitCamera(CameraContext cameraContext) {
    CameraType cameraType = CameraType.valueOf(cameraContext.cameraType().getText().toUpperCase(Locale.ENGLISH));
    Parameters parameters = toParameters(cameraContext.parameterList());

    Camera camera = buildCamera(cameraType, parameters);

    LOG.info("Camera - {}", camera);

    super.exitCamera(cameraContext);
  }

  private Camera buildCamera(CameraType cameraType, Parameters parameters) {
    switch (cameraType) {
      case PERSPECTIVE:
        return PerspectiveCamera.from(parameters);
      default:
        throw new IllegalArgumentException(cameraType + " is not a supported camera type");
    }
  }

  @Override
  public void exitSampler(SamplerContext samplerContext) {
    SamplerType samplerType = SamplerType.valueOf(samplerContext.samplerType().getText().toUpperCase(Locale.ENGLISH));
    Parameters parameters = toParameters(samplerContext.parameterList());

    Sampler sampler = buildSampler(samplerType, parameters);

    LOG.info("Sampler - {}", sampler);

    super.exitSampler(samplerContext);
  }

  private Sampler buildSampler(SamplerType samplerType, Parameters parameters) {
    switch (samplerType) {
      case HALTON:
        return HaltonSampler.from(parameters);
      default:
        throw new IllegalArgumentException(samplerType + "is not a supported sampler type");
    }
  }

  @Override
  public void exitIntegrator(IntegratorContext integratorContext) {
    Parameters parameters = toParameters(integratorContext.parameterList());

    Integrator integrator = PathIntegrator.from(parameters);

    LOG.info("Integrator - {}", integrator);

    super.exitIntegrator(integratorContext);
  }

  public void exitFilm(FilmContext ctx) {
    Parameters parameters = toParameters(ctx.parameterList());

    ImageFilm imageFilm = ImageFilm.from(parameters);

    LOG.info("ImageFilm - {}", imageFilm);

    super.exitFilm(ctx);
  }

  @Override
  public void enterShape(ShapeContext shapeContext) {
    ShapeType shapeType = ShapeType.valueOf(shapeContext.shapeType().getText().toUpperCase());

    Shape shape = buildShape(shapeType, shapeContext.parameterList());

    LOG.info("Shape - {}", shape);

    super.enterShape(shapeContext);
  }

  private Shape buildShape(ShapeType shapeType, ParameterListContext parameterListContext) {
    Parameters parameters = toParameters(parameterListContext);
    switch (shapeType) {
      case SPHERE:
        return buildSphere(parameters);
      case TRIANGLEMESH:
        return buildTriangleMesh(parameters);
      default:
        throw new IllegalArgumentException(shapeType + " is not a valid shape type");
    }
  }

  private Sphere buildSphere(Parameters parameters) {
    Optional<Double> radius = parameters.getParameter("radius").flatMap(Parameter::getAsDouble);

    Sphere.Builder builder = Sphere.builder();
    radius.ifPresent(builder::setRadius);

    return builder.build();
  }

  private TriangleMesh buildTriangleMesh(Parameters parameters) {
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

  private static void logToken(Token token) {
    LOG.debug("{}", token);
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
