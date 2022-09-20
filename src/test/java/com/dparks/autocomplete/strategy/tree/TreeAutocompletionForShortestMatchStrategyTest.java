package com.dparks.autocomplete.strategy.tree;

import com.dparks.autocomplete.model.SourceTextLoader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TreeAutocompletionForShortestMatchStrategyTest {

    private static final Map<Character, PredictionNode> ENTRY_NODES = getEntryNodes();

    @Mock
    SourceTextLoader sourceTextLoaderMock;

    @Mock
    TreeBuilder treeBuilderMock;

    @InjectMocks
    TreeAutocompletionForShortestMatchStrategy treeAutocompletionForShortestMatchStrategy;

    @Test
    void testPostConstruct() {
        // setup
        when(sourceTextLoaderMock.load()).thenReturn(Collections.emptySet());

        // trigger
        treeAutocompletionForShortestMatchStrategy.postConstruct();

        // verify
        Mockito.verify(sourceTextLoaderMock).load();
        Mockito.verify(treeBuilderMock).build(Collections.emptySet());
    }

    @Test
    void testCompleteReturnsShortestMatch() {
        // setup & build expected
        when(sourceTextLoaderMock.load()).thenReturn(Collections.emptySet());
        when(treeBuilderMock.build(Collections.emptySet())).thenReturn(ENTRY_NODES);

        Map<String, Optional<String>> inputToExpectedMatch = new HashMap<>();
        inputToExpectedMatch.put("z", Optional.empty());
        inputToExpectedMatch.put("a", Optional.of("a"));
        inputToExpectedMatch.put("at", Optional.of("at"));
        inputToExpectedMatch.put("att", Optional.of("attach"));
        inputToExpectedMatch.put("atta", Optional.of("attach"));
        inputToExpectedMatch.put("attaz", Optional.empty());
        inputToExpectedMatch.put("attac", Optional.of("attach"));
        inputToExpectedMatch.put("attack", Optional.of("attack"));
        inputToExpectedMatch.put("attach", Optional.of("attach"));

        // trigger
        treeAutocompletionForShortestMatchStrategy.postConstruct();
        Map<String, Optional<String>> inputToActualMatch = inputToExpectedMatch.keySet()
                .stream()
                .collect(Collectors.toMap(Function.identity(), treeAutocompletionForShortestMatchStrategy::complete));

        // verify
        Mockito.verify(sourceTextLoaderMock).load();
        Mockito.verify(treeBuilderMock).build(Collections.emptySet());
        Assertions.assertThat(inputToActualMatch).isEqualTo(inputToExpectedMatch);
    }

    private static Map<Character, PredictionNode> getEntryNodes() {
        Map<Character, PredictionNode> entryNodes = new HashMap<>();

        entryNodes.put('a', new PredictionNode('a', true));

        entryNodes.get('a').getChildren()
                .put('t', new PredictionNode('t', true));

        entryNodes.get('a').getChildren()
                .get('t').getChildren()
                .put('t', new PredictionNode('t', false));

        entryNodes.get('a').getChildren()
                .get('t').getChildren()
                .get('t').getChildren()
                .put('a', new PredictionNode('a', false));

        entryNodes.get('a').getChildren()
                .get('t').getChildren()
                .get('t').getChildren()
                .get('a').getChildren()
                .put('c', new PredictionNode('c', false));

        entryNodes.get('a').getChildren()
                .get('t').getChildren()
                .get('t').getChildren()
                .get('a').getChildren()
                .get('c').getChildren()
                .put('k', new PredictionNode('k', true));

        entryNodes.get('a').getChildren()
                .get('t').getChildren()
                .get('t').getChildren()
                .get('a').getChildren()
                .get('c').getChildren()
                .put('h', new PredictionNode('h', true));

        return entryNodes;
    }

}
