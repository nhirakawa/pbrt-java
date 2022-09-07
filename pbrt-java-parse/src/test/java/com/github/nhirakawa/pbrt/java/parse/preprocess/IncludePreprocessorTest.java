package com.github.nhirakawa.pbrt.java.parse.preprocess;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.io.Resources;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.junit.Before;
import org.junit.Test;

public class IncludePreprocessorTest {
  private IncludePreprocessor includePreprocessor;

  @Before
  public void setup() {
    this.includePreprocessor = new IncludePreprocessor(new ResourcesFileLoader());
  }

  @Test
  public void itDoesNothingIfNoInclude() throws IOException {
    String processed = includePreprocessor.preprocess(
      Resources.getResource("no-include.pbrt")
    );

    String raw = Resources.toString(
      Resources.getResource("no-include.pbrt"),
      StandardCharsets.UTF_8
    );

    assertThat(processed).isEqualToIgnoringWhitespace(raw);
  }

  @Test
  public void itIncludesAtSingleLevel() throws IOException {
    String processed = includePreprocessor.preprocess(
      Resources.getResource("single.pbrt")
    );

    assertThat(processed).isEqualTo("single\nsingle-1");
  }

  @Test
  public void itIncludesNestedFiles() throws IOException {
    String processed = includePreprocessor.preprocess(
      Resources.getResource("nested.pbrt")
    );

    assertThat(processed).isEqualTo("nested\nnested-1\nnested-2");
  }

  @Test
  public void itIncludesFilesInFolders() throws IOException {
    String processed = includePreprocessor.preprocess(
      Resources.getResource("folder.pbrt")
    );

    assertThat(processed).isEqualTo("folder\nsub");
  }
}
