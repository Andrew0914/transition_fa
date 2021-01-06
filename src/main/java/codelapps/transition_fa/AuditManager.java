package codelapps.transition_fa;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AuditManager {
  private int maxEntriesPerFile;
  private String directoryPath;
  private FileSystem fileSystem;

  public AuditManager(int maxEntriesPerFile, String directoryPath, FileSystem fileSystem) {
    this.maxEntriesPerFile = maxEntriesPerFile;
    this.directoryPath = directoryPath;
    this.fileSystem = fileSystem;
  }

  public void addRecord(String visitorName, Date timeOfVisit) throws IOException {
    String[] filepaths = this.fileSystem.getFilesList(this.directoryPath);
    Arrays.sort(filepaths);

    String newRecord = visitorName + ";" + timeOfVisit;

    if (filepaths.length == 0) {
      Path newFilePath = Path.of(this.directoryPath, "audit_1.txt");
      this.fileSystem.writeNewFile(newFilePath, newRecord.getBytes());
      return;
    }

    Path currentFilePath = Path.of(this.directoryPath, filepaths[filepaths.length - 1]);
    List<String> lines = this.fileSystem.readAllLines(currentFilePath);
    String currentFileName = currentFilePath.getFileName().toString();
    currentFileName = currentFileName.substring(0, currentFileName.indexOf("."));
    int currentIndex = Integer.parseInt(currentFileName.split("_")[1]);

    if (lines.size() < this.maxEntriesPerFile) {
      lines.add(newRecord);
      this.fileSystem.writeAllLines(currentFilePath, lines);
    } else {
      int newIndex = currentIndex + 1;
      String newFileName = "audit_" + newIndex + ".txt";
      Path newFilePath = Path.of(this.directoryPath, newFileName);
      this.fileSystem.writeNewFile(newFilePath, newRecord.getBytes());
    }

  }

  public static void main(String[] args) {
    FileSystem fileSystem = new FileSystem();
    AuditManager auditManager = new AuditManager(3, "assets", fileSystem);
    try {
      auditManager.addRecord("Andrew", new Date());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}