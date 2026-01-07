package com.VoxPopuli.FilterService.utils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.FileNotFoundException;
import java.io.IOException;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SourceReader {

    private static final String BASE_PATH = "/profanitySources/";

    /**
     * Source reader for files from the resources folder
     * Tested formats: JSON, CSV, TXT
     * @param filename filename inside resources/profanitySources/
     * @return whole source file in a String
     * @throws IOException if file is not found or cannot be read
     */
    public static final String readSourceIntoString(String filename) throws IOException {
        String fullPath = BASE_PATH + filename;
        try (InputStream inputStream = SourceReader.class.getResourceAsStream(fullPath)) {

            if (inputStream == null) {
                throw new FileNotFoundException("Resource file not found: " + fullPath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}