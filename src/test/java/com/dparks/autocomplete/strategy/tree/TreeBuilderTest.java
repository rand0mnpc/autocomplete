package com.dparks.autocomplete.strategy.tree;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TreeBuilderTest {

    @Test
    void testBuild() {
        // setup & build expected
        TreeBuilder treeBuilder = new TreeBuilder();
        Map<Character, PredictionNode> expected = getExpectedTree();
        Set<String> sourceWords = new HashSet<>();
        sourceWords.add("a");
        sourceWords.add("at");
        sourceWords.add("attack");
        sourceWords.add("attach");


        // trigger
        Map<Character, PredictionNode> actual = treeBuilder.build(sourceWords);

        // verify
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Map<Character, PredictionNode> getExpectedTree() {
        Map<Character, PredictionNode> expected = new HashMap<>();

        expected.put('a', new PredictionNode('a', true));

        expected.get('a').getChildren()
                .put('t', new PredictionNode('t', true));

        expected.get('a').getChildren()
                .get('t').getChildren()
                .put('t', new PredictionNode('t', false));

        expected.get('a').getChildren()
                .get('t').getChildren()
                .get('t').getChildren()
                .put('a', new PredictionNode('a', false));

        expected.get('a').getChildren()
                .get('t').getChildren()
                .get('t').getChildren()
                .get('a').getChildren()
                .put('c', new PredictionNode('c', false));

        expected.get('a').getChildren()
                .get('t').getChildren()
                .get('t').getChildren()
                .get('a').getChildren()
                .get('c').getChildren()
                .put('k', new PredictionNode('k', true));

        expected.get('a').getChildren()
                .get('t').getChildren()
                .get('t').getChildren()
                .get('a').getChildren()
                .get('c').getChildren()
                .put('h', new PredictionNode('h', true));

        return expected;
    }
}
