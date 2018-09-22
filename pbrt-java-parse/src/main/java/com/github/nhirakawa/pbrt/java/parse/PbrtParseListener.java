package com.github.nhirakawa.pbrt.java.parse;

import java.io.IOException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.nhirakawa.pbrt.java.core.model.transform.LookAt;
import com.github.nhirakawa.pbrt.java.parse.PbrtParser.LookAtContext;
import com.google.common.io.Resources;

public class PbrtParseListener extends PbrtBaseListener {

  private static final Logger LOG = LoggerFactory.getLogger(PbrtParseListener.class);

  @Override
  public void exitLookAt(LookAtContext ctx) {
    double lookAtEyeX = Double.parseDouble(ctx.lookAtEyeX().getText());
    double lookAtEyeY = Double.parseDouble(ctx.lookAtEyeY().getText());
    double lookAtEyeZ = Double.parseDouble(ctx.lookAtEyeZ().getText());

    double lookAtPointX = Double.parseDouble(ctx.lookAtPointX().getText());
    double lookAtPointY = Double.parseDouble(ctx.lookAtPointY().getText());
    double lookAtPointZ = Double.parseDouble(ctx.lookAtPointZ().getText());

    double lookAtUpX = Double.parseDouble(ctx.lookAtUpX().getText());
    double lookAtUpY = Double.parseDouble(ctx.lookAtUpY().getText());
    double lookAtUpZ = Double.parseDouble(ctx.lookAtUpZ().getText());

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
    LOG.debug("{}", lookAt);

    super.exitLookAt(ctx);
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
}
