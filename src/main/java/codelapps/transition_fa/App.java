package codelapps.transition_fa;

import java.io.IOException;
import java.util.Date;

public class App {

  private String directoryPath;
  private AuditManager auditManager;
  private Persister persister;

  public App(String directoryPath, int maxEntriesPerFile) {
    this.directoryPath = directoryPath;
    this.auditManager = new AuditManager(maxEntriesPerFile);
    this.persister = new Persister();
  }

  public void addRecord(String visitorName, Date timeOfVisit) throws IOException {
    FileContent[] files = this.persister.readFilesListContent(this.directoryPath);
    FileUpdate update = this.auditManager.addRecord(files, visitorName, timeOfVisit);
    this.persister.applyUpdate(directoryPath, update);
  }

  public static void main(String[] args) {
    try {
      App app = new App("assets", 3);
      app.addRecord("Andrew", new Date());
    } catch (IOException e) {

      e.printStackTrace();
    }
  }

}
