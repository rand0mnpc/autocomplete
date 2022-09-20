package com.dparks.autocomplete.data;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class LocalFileSourceTextLoaderTest {

    @Test
    void testLoad() {
        // setup & build expected
        Set<String> expected = new HashSet<>();
        expected.add("cat");
        expected.add("rat");
        expected.add("fat");
        expected.add("cake");
        expected.add("rake");
        expected.add("lake");
        expected.add("creation");
        expected.add("a");
        expected.add("at");

        LocalFileSourceTextLoader localFileSourceTextLoader = new LocalFileSourceTextLoader("src/main/resources/static/words_test.txt");

        // trigger
        Set<String> actual = localFileSourceTextLoader.load();

        // verify
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
