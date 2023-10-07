package ru.job4j.io;

import java.io.*;
import java.util.Objects;
import java.util.function.Predicate;

public final class ReadFile {
    private final File file;

    public ReadFile(File file) {
        this.file = file;
    }

    public synchronized String getContent(Predicate<Integer> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test(data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public synchronized String firstFilter() throws IOException {
        Predicate<Integer> filter = data -> data < 0x80;
        return getContent(filter);
    }

    public synchronized String withoutFilter() throws IOException {
        return getContent(Objects::nonNull);
    }
}