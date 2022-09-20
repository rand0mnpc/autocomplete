package com.dparks.autocomplete.strategy.tree;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * The building block of the data structure {@link TreeAutocompletionForShortestMatchStrategy} uses for autocompletion.
 * Is equivalent to an n-ary tree node with some additional data.
 */
@Getter
@ToString
@EqualsAndHashCode
public class PredictionNode {
    private final Character character;
    private final Map<Character, PredictionNode> children;

    @Setter
    private boolean isWordEnd;

    public PredictionNode(Character character, boolean isWordEnd) {
        this.character = character;
        this.children = new HashMap<>();
        this.isWordEnd = isWordEnd;
    }
}
