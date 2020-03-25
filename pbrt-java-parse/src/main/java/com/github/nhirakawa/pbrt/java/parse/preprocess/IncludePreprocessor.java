package com.github.nhirakawa.pbrt.java.parse.preprocess;

import com.google.common.base.Joiner;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
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

  private final FileLoader fileLoader;

  public IncludePreprocessor(FileLoader fileLoader) {
    this.fileLoader = fileLoader;
  }

  public String preprocess(URL url) throws IOException {
    List<String> processedLines = new ArrayList<>();

    try (
      BufferedReader bufferedReader = new BufferedReader(
        new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)
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
    return preprocess(fileLoader.locate(filename));
  }
}
