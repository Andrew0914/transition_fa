package codelapps.transition_fa;

import java.io.File;
import java.util.List;

public class FileContent implements Comparable<FileContent> {
  public final String filePath;
  public final List<String> lines;

  public FileContent(String filePath, List<String> lines) {
    this.filePath = filePath;
    this.lines = lines;
  }

  @Override
  public int compareTo(FileContent o) {
    return filePath.compareTo(o.filePath);
  }
}
