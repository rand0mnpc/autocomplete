package com.dparks.autocomplete.strategy.tree;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TreeBuilder {

    /**
     * I have delegates this component out for readability and testability. This component will read the lines of the
     * input text and construct an n-ary tree based on those inputs.
     * @param words source text
     * @return n-ary tree where words sharing a prefix share a path from the same root node.
     */
    public Map<Character, PredictionNode> build(Set<String> words) {
        Map<Character, PredictionNode> entryNodes = words
                .stream()
                .map(word -> word.charAt(0))
                .distinct()
                .collect(Collectors.toMap(Function.identity(), character -> new PredictionNode(character, false)));

        for(String word : words) {
            PredictionNode currentNode = entryNodes.get(word.charAt(0));

            for(int i = 1; i < word.length(); i++) {
                Character nextCharacter = word.charAt(i);
                currentNode.getChildren().putIfAbsent(nextCharacter, new PredictionNode(nextCharacter, false));
                currentNode = currentNode.getChildren().get(nextCharacter);
            }

            currentNode.setWordEnd(true);
        }

        return entryNodes;
    }
}
