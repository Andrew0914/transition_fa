package codelapps.transition_fa;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AuditManagerTest {

  public List<String> sampleLines() {
    List<String> lines = new ArrayList<String>();
    lines.add("Andrew;2020-04-04T16:30:00");
    lines.add("Alan;2020-04-09T16:30:00");
    lines.add("Dany;2020-04-07T16:30:00");
    return lines;
  }

  @Test
  public void a_new_file_is_created_when_current_file_is_overflows() throws IOException, ParseException {
    // Arrange
    FileContent[] files = new FileContent[1];
    files[0] = new FileContent("audit_1.txt", sampleLines());
    Date date = new SimpleDateFormat("MM/dd/yyyy").parse("6/27/2020");
    AuditManager sut = new AuditManager(3);
    // Act
    FileUpdate update = sut.addRecord(files, "Codelapps", date);
    // Assert
    assertEquals(update.filePath, "audit_2.txt");
    assertEquals(update.newLine, "Codelapps;Sat Jun 27 00:00:00 CDT 2020");
  }
}
