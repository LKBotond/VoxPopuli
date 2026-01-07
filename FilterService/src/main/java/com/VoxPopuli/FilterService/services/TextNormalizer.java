package com.VoxPopuli.FilterService.services;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
        return input
                .replace('@', 'a')
                .replace('4', 'a')
                .replace('8', 'b')
                .replace('(', 'c')
                .replace('<', 'c')
                .replace('{', 'c')
                .replace('[', 'c')
                .replace('3', 'e')
                .replace('6', 'g')
                .replace('9', 'g')
                .replace('1', 'i')
                .replace('!', 'i')
                .replace('|', 'i')
                .replace('0', 'o')
                .replace('$', 's')
                .replace('5', 's')
                .replace('7', 't')
                .replace('+', 't')
                .replace('2', 'z');
    }

    private String punctuationNormalization(String input) {
        log.info(input.replaceAll("[^\\p{L}\\p{Nd}]+", " "));
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
