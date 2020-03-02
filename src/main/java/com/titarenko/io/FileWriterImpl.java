package com.titarenko.io;

import java.io.FileWriter;
import java.io.IOException;

public class FileWriterImpl implements Writer {

    private static final String FILE_WRITER_URL = "src/main/resources/output.txt";

    @Override
    public void writeToOutputStream(String text) {
        try {
            FileWriter writer = new FileWriter(FILE_WRITER_URL, true);
            writer.write(text);
            writer.append("\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void clearFile() {
        try {
            FileWriter writer = new FileWriter(FILE_WRITER_URL);
            writer.write("");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
