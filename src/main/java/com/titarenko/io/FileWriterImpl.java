package com.titarenko.io;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterImpl implements Writer {

    private static final String FILE_WRITER_URL = "src/main/resources/output.txt";
    private static final FileWriterImpl INSTANCE = new FileWriterImpl();

    private FileWriterImpl() {
        try (FileWriter ignored = new FileWriter(FILE_WRITER_URL)) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FileWriterImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public void writeToOutputStream(String text) {
        try (FileWriter writer = new FileWriter(FILE_WRITER_URL, true)) {
            writer.write(text);
            writer.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
