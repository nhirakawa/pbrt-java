package com.github.nhirakawa.pbrt.java.parse.preprocess;

import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;

public class ResourcesFileLoader implements FileLoader {

  @Override
  public URL locate(String filename) throws IOException {
    return Resources.getResource(filename);
  }
}
