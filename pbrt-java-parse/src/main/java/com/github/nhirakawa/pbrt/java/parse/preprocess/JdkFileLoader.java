package com.github.nhirakawa.pbrt.java.parse.preprocess;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class JdkFileLoader implements FileLoader {

  @Override
  public URL locate(String filename) throws IOException {
    return new File(filename).toURI().toURL();
  }
}
