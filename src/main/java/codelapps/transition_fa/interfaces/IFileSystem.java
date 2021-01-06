package codelapps.transition_fa.interfaces;

import java.io.IOException;
import java.util.List;
import java.nio.file.Path;

public interface IFileSystem {

    String[] getFilesList(String directoryPath);

    List<String> readAllLines(Path filePath) throws IOException;

    void writeNewFile(Path filePath, byte[] bytesToWrite) throws IOException;

    void writeAllLines(Path filePath, List<String> lines) throws IOException;
}
