package com.github.nhirakawa.pbrt.java.parse.preprocess;

import java.io.InputStream;
import java.io.IOException;
import java.net.URL;

public interface FileLoader {
  URL locate(String filename) throws IOException;
}
