package com.dparks.autocomplete.data;

import com.dparks.autocomplete.model.SourceTextLoader;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;

@Component("localFileSourceTextLoader")
public class LocalFileSourceTextLoader implements SourceTextLoader {

    private final String sourceFilePath;

    @Autowired
    public LocalFileSourceTextLoader(@Qualifier("sourceFilePath") String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    /**
     * This implementation of the loader reads a text file from the local repository. After running tests, I found that
     * for a file as small as 400k words, multithreading here by way of parallelStream() actually yielded worse
     * performance by around 50ms.
     * @return a set of alphabetic strings from the source file. This implementation filters for only alphabetic strings
     * and makes everything lowercase.
     */
    @Override
    @SneakyThrows
    public Set<String> load() {
        return Files.readAllLines(Paths.get(sourceFilePath))
                .stream()
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(StringUtils::isAlpha)
                .collect(Collectors.toUnmodifiableSet());
    }
}
