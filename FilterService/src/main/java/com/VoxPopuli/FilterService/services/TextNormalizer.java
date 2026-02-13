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

    // patterns

    /**
     * Regular expression pattern used to extract individual words from text.
     *
     * <p>
     * This pattern matches one or more Unicode letters {@code (`\p{L}+`)},
     * which includes letters from all languages and scripts, ensuring
     * internationalization support.
     *
     * <p>
     * It does <strong>not</strong> match numbers, punctuation, or symbols.
     * Primarily used in tokenization after normalization steps to split
     * text into discrete words for dictionary-based moderation.
     *
     */
    private static final Pattern WORD_PATTERN = Pattern.compile("\\p{L}+");

    /**
     * Regular expression pattern used to detect artificially separated
     * sequences of letters in user input.
     *
     * <p>
     * This pattern identifies groups of at least three letters where
     * single letters are separated by one or more whitespace characters,
     * and the group is not directly adjacent to other letters.
     *
     * <p>
     * Pattern breakdown:
     * <ul>
     * <li>{@code  (?<!\p{L})} negative lookbehind to ensure the sequence
     * is not immediately preceded by a letter</li>
     * <li>{@code (?:\p{L}\s+){2,}} matches at least two letters each
     * followed by whitespace</li>
     * <li>{@code \p{L}} matches the final letter of the sequence</li>
     * <li>{@code (?!\p{L})} negative lookahead to ensure the sequence
     * is not immediately followed by a letter</li>
     * </ul>
     *
     */
    private static final Pattern SEPARATION_PATTERN = Pattern.compile("(?<!\\p{L})(?:\\p{L}\\s+){2,}\\p{L}(?!\\p{L})");

    /**
     * Immutable character substitution map used to normalize common
     * "leet-speak" and symbol-based obfuscations into their canonical
     * alphabetic equivalents.
     *
     * <p>
     * This mapping is applied during text normalization to prevent users
     * from bypassing profanity detection by substituting visually similar
     * characters (e.g., "@" for "a", "3" for "e", "$" for "s").
     *
     * <p>
     * The map is static and immutable to ensure thread safety
     * and consistent normalization behavior across requests.
     */
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
     * <p>
     * This method normalizes the input by:
     * <ol>
     * <li>Removing Unicode accents</li>
     * <li>Removing leet-speek</li>
     * <li>Replacing any remaining non letters with a single white space</li> 
     * <li>Collapsing 3 letter combinations sepparated by white spaces</li> 
     * <li>limiting repeating character length to 2</li> 
     * </ol>
     * , filters out whitespaces between single letter combinations,
     * replaces leet-speak, and removes excessive repeating characters.
     * 
     * @param input the string to filter
     * @return the normalized text as a List
     */
    public List<String> normalizeInputIntoList(String input) {
        String filtered;
        filtered = caseNormalization(input);
        filtered = cleverityFilter(filtered);
        filtered = punctuationNormalization(filtered);
        filtered = separationFilter(filtered);
        filtered = repetitionFilter(filtered);
        return listifyText(filtered);
    }

    /**
     * Normalizes the input string into a canonical, case-insensitive form
     *
     * <p>
     * This method performs the following transformations:
     * <ul>
     * <li>Applies Unicode normalization using {@link Normalizer.Form#NFKD}
     * to decompose accented and compatibility characters.</li>
     * <li>Removes all Unicode combining marks, meaning
     * it strips accents ("é" → "e").</li>
     * <li>Converts the result to lowercase using {@link Locale#ROOT}
     * to ensure locale-independent case normalization.</li>
     * </ul>
     *
     * @param input the raw user-provided text;
     * @return a normalized, lowercase, accent-free representation of the input
     */
    private String caseNormalization(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
    }

    /**
     * Implements the {@code cleverityMap} to filter out leet-speek
     * <p>
     * This method iterates over each character in the input string and
     * substitutes it using {@code cleverityMap} when a mapping exists.
     * Characters not present in the map remain unchanged.
     *
     * @param input the raw or partially normalized text; must not be {@code null}
     * @return a new string with leet-style substitutions normalized
     */
    private String cleverityFilter(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = cleverityMap.getOrDefault(chars[i], chars[i]);
        }
        return new String(chars);
    }

    /**
     * Normalizes punctuation and special characters by replacing any sequence
     * of non-alphanumeric Unicode characters with a single space.
     *
     * <p>
     * This method preserves:
     * <ul>
     * <li>{@code \p{L}} — any kind of letter from any language</li>
     * <li>{@code \p{Nd}} — any Unicode decimal digit</li>
     * </ul>
     *
     * <p>
     * All other characters (e.g., punctuation, symbols, emojis, control
     * characters) are replaced with a space. Consecutive non-alphanumeric
     * characters are collapsed into a single space.
     *
     * @param input the text to normalize; must not be {@code null}
     * @return a string where non-letter and non-digit sequences are replaced by
     *         spaces
     */
    private String punctuationNormalization(String input) {
        return input.replaceAll("[^\\p{L}\\p{Nd}]+", " ");
    }

    /**
     * Detects and collapses artificially separated single-letter sequences
     * into contiguous words.
     *
     * <p>
     * This method targets evasion patterns where users insert whitespace
     * between characters to bypass moderation rules.
     *
     * <p>
     * The {@code SEPARATION_PATTERN} identifies sequences of individual
     * letters separated by whitespace (with a minimum length threshold),
     * and removes the internal spaces while preserving surrounding text.
     *
     *
     * @param input a partially normalized text; must not be {@code null}
     * @return a string where separated single-letter sequences are merged
     */
    private String separationFilter(String input) {
        return SEPARATION_PATTERN.matcher(input)
                .replaceAll(m -> m.group().replaceAll("\\s+", ""));
    }

    /**
     * Collapses sequences of repeated characters in the input text to a maximum
     * of two consecutive occurrences.
     *
     * <p>
     * This method is intended to normalize exaggerated character repetition
     * often used to bypass moderation or for stylistic emphasis in user input.
     *
     * <p>
     * The regular expression {@code (.)\1{2,}} matches any character repeated
     * three or more times consecutively, and replaces it with exactly two
     * occurrences of that character.
     *
     * @param input the text to normalize;
     * @return a string with long character repetitions reduced to two consecutive
     *         characters
     */
    private String repetitionFilter(String input) {
        return input.replaceAll("(.)\\1{2,}", "$1$1");
    }

    /**
     * This method transforms the normalized input into a list format.
     * 
     * @param input normalized text
     * @return a list of wwords
     */
    private List<String> listifyText(String input) {
        List<String> words = new ArrayList<>();
        Matcher matcher = WORD_PATTERN.matcher(input);
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return words;
    }

}
