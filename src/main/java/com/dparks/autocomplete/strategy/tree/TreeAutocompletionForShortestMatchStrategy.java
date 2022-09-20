package com.dparks.autocomplete.strategy.tree;

import com.dparks.autocomplete.model.AutocompletionStrategy;
import com.dparks.autocomplete.model.SourceTextLoader;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

@Component("treeAutocompletionForShortestMatchStrategy")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TreeAutocompletionForShortestMatchStrategy implements AutocompletionStrategy {

    private final SourceTextLoader sourceTextLoader;
    private final TreeBuilder treeBuilder;
    private Map<Character, PredictionNode> entryNodes;

    /**
     * The building of the tree is resource-intensive and only need be done once per service activation. Hence why I
     * have placed it in a post construct.
     */
    @PostConstruct
    public void postConstruct() {
        Set<String> sourceText = sourceTextLoader.load();
        entryNodes = treeBuilder.build(sourceText);
    }

    /**
     * This strategy for autocompletion relies on an n-ary tree. On service activation, this strategy loads the source
     * test into an n-ary tree where letters sharing prefixes also share paths from the set of root nodes. The set of
     * root nodes are the unique starting letters of the source text words. In order to match an input string, this
     * strategy performs a tree traversal, maintaining a min-heap of the shortest match containing all input characters
     * as a prefix. There is no correction mechanism here. The strategy will only return a possible match if all the
     * characters of the input text match a path in the tree beginning from one of the root nodes.
     * @param inputText user input text.
     * @return predicted text
     */
    @Override
    public Optional<String> complete(@NonNull String inputText) {

        PredictionNode startingNode = entryNodes.get(inputText.charAt(0));
        if (Objects.isNull(startingNode)) {
            return Optional.empty();
        }

        if(startingNode.isWordEnd() && inputText.length() == 1) {
            return Optional.of(startingNode.getCharacter().toString());
        }

        PredictionNode nextNode = startingNode;
        for (int i = 1; i < inputText.length(); i++) {
            nextNode = nextNode.getChildren().get(inputText.charAt(i));
            if (Objects.isNull(nextNode)) {
                return Optional.empty();
            }
        }

        PriorityQueue<List<PredictionNode>> priorityQueue = new PriorityQueue<>(1, Comparator.comparing(List::size));
        traverseForShortestMatch(nextNode, new ArrayList<>(), priorityQueue);

        StringBuilder shortestAutoCompletedString = new StringBuilder(inputText.substring(0, inputText.length()-1));
        Optional.ofNullable(priorityQueue.peek()).orElse(Collections.emptyList())
                .stream()
                .map(PredictionNode::getCharacter)
                .forEach(shortestAutoCompletedString::append);

        return Optional.of(shortestAutoCompletedString.toString());
    }

    private void traverseForShortestMatch(
            PredictionNode predictionNode,
            List<PredictionNode> currentPath,
            PriorityQueue<List<PredictionNode>> priorityQueue) {

        currentPath.add(predictionNode);
        if(predictionNode.getChildren().isEmpty() || predictionNode.isWordEnd()) {
            priorityQueue.add(currentPath);
        }

        predictionNode.getChildren().values()
                .forEach(child -> traverseForShortestMatch(child, new ArrayList<>(currentPath), priorityQueue));
    }

}
