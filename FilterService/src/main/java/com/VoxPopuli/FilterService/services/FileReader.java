package com.VoxPopuli.FilterService.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.VoxPopuli.FilterService.utils.SourceReader;

import lombok.NoArgsConstructor;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
@NoArgsConstructor
public class FileReader {
    private final ObjectMapper mapper = new ObjectMapper();

    public Set<String> loadJsonSourceIntoSet(String fileName) throws IOException {
        String jsonString = SourceReader.readSourceIntoString(fileName);
        return mapper.readValue(jsonString, new TypeReference<Set<String>>() {
        });
    }

    public Set<String> loadVerticalCsvSourceIntoSet(String fileName) throws IOException {
        String csvString = SourceReader.readSourceIntoString(fileName);

        return verticalSplit(csvString);
    }

    public Set<String> loadHorizontalCsvSourceIntoSet(String fileName) throws IOException {
        String csvString = SourceReader.readSourceIntoString(fileName);
        return horizontalSplit(csvString);
    }

    public Set<String> loadTxtSourceIntoSet(String fileName) throws IOException {
        String txtString = SourceReader.readSourceIntoString(fileName);
        return verticalSplit(txtString);
    }

    /**
     * Splits words into a set via the default comma ","
     * 
     * @param source String separated by commas
     * @return set of individual words
     */
    private Set<String> horizontalSplit(String source) {
        return Arrays.stream(source.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }

    /**
     * Splits words into a set via regex new line "\\R"
     * 
     * @param source String separated by "\\R"
     * @return set of individual words
     */
    private Set<String> verticalSplit(String source) {
        return Arrays.stream(source.split("\\R"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }

    /**
     * Splits string into a set of words based on the provided separator, could be
     * anything.
     * 
     * @param source    String where words are separated via the separator
     * @param separator param based on which splitting happens
     * @return Set of words
     */

   
    @SuppressWarnings("unused")
    private Set<String> customSeparatorSplit(String source, String separator) {
        return Arrays.stream(source.split(separator))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());
    }
}
