package com.kerrrusha.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriterUtil {

    public static void write(String filename, String content) {
        try (BufferedWriter writer = new BufferedWriter
                (new OutputStreamWriter(Files.newOutputStream(Paths.get(filename)), StandardCharsets.UTF_8))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
