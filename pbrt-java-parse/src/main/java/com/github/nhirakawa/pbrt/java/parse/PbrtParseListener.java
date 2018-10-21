package com.github.nhirakawa.pbrt.java.parse;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nhirakawa.pbrt.java.core.model.Parameter;
import com.github.nhirakawa.pbrt.java.core.model.Parameters;
import com.github.nhirakawa.pbrt.java.core.model.camera.Camera;
import com.github.nhirakawa.pbrt.java.core.model.camera.CameraType;
import com.github.nhirakawa.pbrt.java.core.model.camera.PerspectiveCamera;
import com.github.nhirakawa.pbrt.java.core.model.transform.LookAt;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.CameraContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.LookAtContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ParameterContext;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.ParameterListContext;
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
    return Parameter.builder()
        .setName(parameterContext.name().getText())
        .setType(parameterContext.type().getText())
        .setValue(parameterContext.value().getText())
        .build();
  }
}
