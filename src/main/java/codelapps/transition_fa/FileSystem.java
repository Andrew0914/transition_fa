package codelapps.transition_fa;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import codelapps.transition_fa.interfaces.IFileSystem;

import java.nio.charset.StandardCharsets;

public class FileSystem implements IFileSystem {

    public FileSystem() {
    }

    @Override
    public String[] getFilesList(String directoryPath) {
        File directory = new File(directoryPath);
        return directory.list();
    }

    @Override
    public List<String> readAllLines(Path filePath) throws IOException {
        return Files.readAllLines(filePath);
    }

    @Override
    public void writeNewFile(Path filePath, byte[] bytesToWrite) throws IOException {
        Files.write(filePath, bytesToWrite, StandardOpenOption.CREATE_NEW);
    }

    @Override
    public void writeAllLines(Path filePath, List<String> lines) throws IOException {
        Files.write(filePath, lines, StandardCharsets.UTF_8, StandardOpenOption.WRITE);
    }

}
