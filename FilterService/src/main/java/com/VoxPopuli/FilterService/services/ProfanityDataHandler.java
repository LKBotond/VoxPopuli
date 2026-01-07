package com.VoxPopuli.FilterService.services;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class ProfanityDataHandler {
    private final ConcurrentHashMap<String, Set<String>> collection = new ConcurrentHashMap<>();
    private FileReader reader;

    public ProfanityDataHandler(FileReader reader) {
        this.reader = reader;
        loadDictionary("eng", loadBasedict());
    }

    public boolean foundWord(String word) {
        for (Set<String> dict : collection.values()) {
            if (dict.contains(word)) {
                return true;
            }
        }
        return false;
    }

    // CRUD
    public void loadDictionary(String languageTag, Set<String> newSet) {
        collection.put(languageTag, newSet);
    }

    public void dropDictionary(String languageTag) {
        collection.remove(languageTag);
    }

    public Set<String> getDictByTag(String tag) {
        return collection.get(tag);
    }

    private Set<String> loadBasedict() {
        try {
            return reader.loadJsonSourceIntoSet("DefaultEnProfanities.json");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
