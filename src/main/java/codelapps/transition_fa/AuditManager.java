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

  public AuditManager(int maxEntriesPerFile, String directoryPath) {
    this.maxEntriesPerFile = maxEntriesPerFile;
    this.directoryPath = directoryPath;
  }

  public void addRecord(String visitorName, Date timeOfVisit) throws IOException {
    File directory = new File(this.directoryPath);
    System.out.println(directory.getAbsolutePath());
    String[] filepaths = directory.list();
    Arrays.sort(filepaths);

    String newRecord = visitorName + ";" + timeOfVisit;

    if (filepaths.length == 0) {
      Path newFilePath = Path.of(this.directoryPath, "audit_1.txt");
      Files.write(newFilePath, newRecord.getBytes(), StandardOpenOption.CREATE_NEW);
      return;
    }

    Path currentFilePath = Path.of(this.directoryPath, filepaths[filepaths.length - 1]);
    List<String> lines = Files.readAllLines(currentFilePath);
    String currentFileName = currentFilePath.getFileName().toString();
    currentFileName = currentFileName.substring(0, currentFileName.indexOf("."));
    int currentIndex = Integer.parseInt(currentFileName.split("_")[1]);

    if (lines.size() < this.maxEntriesPerFile) {
      lines.add(newRecord);
      Files.write(currentFilePath, lines, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
    } else {
      int newIndex = currentIndex + 1;
      String newFileName = "audit_" + newIndex + ".txt";
      Path newFilePath = Path.of(this.directoryPath, newFileName);
      Files.write(newFilePath, newRecord.getBytes(), StandardOpenOption.CREATE_NEW);
    }

  }

  public static void main(String[] args) {
    AuditManager auditManager = new AuditManager(3, "assets");
    try {
      auditManager.addRecord("Andrew", new Date());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}