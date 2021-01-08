package codelapps.transition_fa;

import java.util.Arrays;
import java.util.Date;

public class AuditManager {
  private int maxEntriesPerFile;

  public AuditManager(int maxEntriesPerFile) {
    this.maxEntriesPerFile = maxEntriesPerFile;
  }

  public FileUpdate addRecord(FileContent[] files, String visitorName, Date timeOfVisit) {

    Arrays.sort(files);

    String newRecord = visitorName + ";" + timeOfVisit;

    if (files.length == 0)
      return new FileUpdate("audit_1.txt", newRecord);

    FileContent lastFile = files[files.length - 1];
    String currentFileName = lastFile.filePath.substring(0, lastFile.filePath.indexOf("."));
    int currentIndex = Integer.parseInt(currentFileName.split("_")[1]);
    if (lastFile.lines.size() < this.maxEntriesPerFile)
      return new FileUpdate(currentFileName + ".txt", newRecord);

    return new FileUpdate("audit_" + (currentIndex + 1) + ".txt", newRecord);

  }
}