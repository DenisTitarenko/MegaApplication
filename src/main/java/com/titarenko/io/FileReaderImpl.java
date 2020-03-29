package com.titarenko.io;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

@Component
public class FileReaderImpl extends AbstractReader {

    private static final String FILE_READER_URL = "src/main/resources/input.txt";
    private LinkedList<String> list = new LinkedList<>();

    private FileReaderImpl() {
        try {
            String input = Files.readString(Paths.get(FILE_READER_URL));
            list = new LinkedList<>(Arrays.asList(input.split("[\\r\\n|]+")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String readLine() {
        return list.pollFirst();
    }
}
