package codelapps.transition_fa;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
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
        String[] filesList = { "audit_1.txt", "audit_2.txt" };

        FileSystem fileSystemMock = mock(FileSystem.class);
        when(fileSystemMock.getFilesList("assets")).thenReturn(filesList);
        when(fileSystemMock.readAllLines(Path.of("assets", "audit_2.txt"))).thenReturn(sampleLines());
        
        AuditManager sut = new AuditManager(3, "assets", fileSystemMock);
        Date date = new SimpleDateFormat("MM/dd/yyyy").parse("6/27/2020");

        // Act
        sut.addRecord("Codelapps", date);

        // Assert
        verify(fileSystemMock).writeNewFile(Path.of("assets", "audit_3.txt"),
                new String("Codelapps;Sat Jun 27 00:00:00 CDT 2020").getBytes());

    }
}
