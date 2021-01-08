package codelapps.transition_fa;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Persister {
  public FileContent[] readFilesListContent(String directoryPath) throws IOException {
    File directory = new File(directoryPath);

    List<FileContent> filesContent = new ArrayList<FileContent>();

    for (String filePath : directory.list()) {
      filesContent.add(new FileContent(filePath, Files.readAllLines(Path.of(directoryPath, filePath))));
    }
    return filesContent.toArray(new FileContent[0]);
  }

  public void applyUpdate(String directoryPath, FileUpdate fileUpdate) throws IOException {
    boolean isThereAfile = Files.exists(Path.of(directoryPath, fileUpdate.filePath));
    if (!isThereAfile)
      Files.createFile(Path.of(directoryPath, fileUpdate.filePath));

    Files.writeString(Path.of(directoryPath, fileUpdate.filePath), (isThereAfile ? "\n" : "") + fileUpdate.newLine,
        StandardOpenOption.APPEND);
  }
}
