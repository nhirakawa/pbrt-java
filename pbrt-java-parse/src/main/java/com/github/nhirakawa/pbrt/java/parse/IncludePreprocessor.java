package com.github.nhirakawa.pbrt.java.parse;

import com.google.common.base.Joiner;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncludePreprocessor {
  private static final Logger LOG = LoggerFactory.getLogger(
    IncludePreprocessor.class
  );

  private static final Pattern PATTERN = Pattern.compile(
    ".*Include \"(?<filename>.*.pbrt)\".*"
  );

  private static final Joiner JOINER = Joiner.on("\n");

  public IncludePreprocessor() {}

  public String preprocess(InputStream inputStream) throws IOException {
    List<String> processedLines = new ArrayList<>();

    try (
      BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8)
      )
    ) {
      while (bufferedReader.ready()) {
        String line = bufferedReader.readLine();
        String processed = process(line);
        processedLines.add(processed);
      }
    }

    return JOINER.join(processedLines);
  }

  private String process(String input) throws IOException {
    Matcher matcher = PATTERN.matcher(input);

    if (!matcher.matches()) {
      return input;
    }

    String filename = matcher.group("filename");
    try (
      InputStream inputStream = Files.newInputStream(
        Paths.get(filename),
        StandardOpenOption.READ
      )
    ) {
      return preprocess(inputStream);
    }
  }

  public static void main(String... args) throws IOException {
    try (
      InputStream inputStream = Files.newInputStream(
        Paths.get("nested.pbrt"),
        StandardOpenOption.READ
      )
    ) {
      String replaced = new IncludePreprocessor().preprocess(inputStream);

      LOG.info("Transformed into\n{}", replaced);
    }
  }
}
