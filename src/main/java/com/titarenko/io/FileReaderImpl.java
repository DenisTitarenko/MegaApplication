package com.titarenko.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReaderImpl extends AbstractReader {

    private static final String FILE_READER_URL = "src/main/resources/input.txt";
    private static final FileReaderImpl INSTANCE = new FileReaderImpl();
    private List<String> list = new LinkedList<>();

    private FileReaderImpl() {
        try {
            String input = new String(Files.readAllBytes(Paths.get(FILE_READER_URL)));
            list = Stream.of(input.split("[\\r\\n|]+")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileReaderImpl getInstance() {
        return INSTANCE;
    }

    @Override
    public String readLine() {
        String result = null;
        if (list.size() > 0) {
            result = list.get(0);
            FileWriterImpl.getInstance().writeToOutputStream(result);
            list.remove(0);
        }
        return result;
    }
}
