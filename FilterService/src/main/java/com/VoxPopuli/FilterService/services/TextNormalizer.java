package com.VoxPopuli.FilterService.services;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Slf4j
public class TextNormalizer {

    // patternsc
    private static final Pattern WORD_PATTERN = Pattern.compile("\\p{L}+");
    private static final Pattern SEPARATION_PATTERN = Pattern.compile("(?<!\\p{L})(?:\\p{L}\\s+){2,}\\p{L}(?!\\p{L})");

    // map for substitute Characters some clever people use to try avoid being
    // caught.
    private static final Map<Character, Character> cleverityMap = Map.ofEntries(
            Map.entry('@', 'a'),
            Map.entry('4', 'a'),
            Map.entry('8', 'b'),
            Map.entry('(', 'c'),
            Map.entry('<', 'c'),
            Map.entry('{', 'c'),
            Map.entry('[', 'c'),
            Map.entry('3', 'e'),
            Map.entry('6', 'g'),
            Map.entry('9', 'g'),
            Map.entry('1', 'i'),
            Map.entry('!', 'i'),
            Map.entry('|', 'i'),
            Map.entry('0', 'o'),
            Map.entry('$', 's'),
            Map.entry('5', 's'),
            Map.entry('7', 't'),
            Map.entry('+', 't'),
            Map.entry('2', 'z'));

    /**
     * Should be used for user inputs, not dict inputs.
     * It filters out whitespaces between single letter combinations,
     * replaces leet-speak, and removes excessive repeating characters.
     * 
     * @param input the string to filter
     * @return the normalized text as a List
     */
    public List<String> normalizeInputIntoList(String input) {
        String filtered = repetitionFilter(
                separationFilter(
                        punctuationNormalization(
                                cleverityFilter(
                                        caseNormalization(input)))));
        return listifyText(filtered);
    }

    private String caseNormalization(String input) {
        log.info(Normalizer.normalize(input, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT));
        return Normalizer.normalize(input, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
    }

    private String cleverityFilter(String input) {

        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = cleverityMap.getOrDefault(chars[i], chars[i]);
        }
        return new String(chars);
    }

    private String punctuationNormalization(String input) {
        return input.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
    }

    private String separationFilter(String input) {
        log.info(SEPARATION_PATTERN.matcher(input)
                .replaceAll(m -> m.group().replaceAll("\\s+", "")));
        return SEPARATION_PATTERN.matcher(input)
                .replaceAll(m -> m.group().replaceAll("\\s+", ""));
    }

    private String repetitionFilter(String input) {
        log.info(input.replaceAll("(.)\\1{2,}", "$1$1"));
        return input.replaceAll("(.)\\1{2,}", "$1$1");
    }

    private List<String> listifyText(String input) {
        List<String> words = new ArrayList<>();
        Matcher matcher = WORD_PATTERN.matcher(input);
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return words;
    }

}
