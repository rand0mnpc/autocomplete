package com.dparks.autocomplete.application;

import com.dparks.autocomplete.model.AutocompleteResponse;
import com.dparks.autocomplete.model.AutocompletionStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AutocompleteControllerTest {

    private static final long ANY_EPOCH_MILLI = 1663638391;
    private static final String EXPECTED_REQUEST_ID = "1663638391--1268878963";
    private static final String ANY_INPUT = "foobar";

    @Mock
    Clock clockMock;

    @Mock
    AutocompletionStrategy autocompletionStrategyMock;

    @InjectMocks
    AutocompleteController autocompleteController;

    @Test
    void testAutocomplete() {
        // setup
        when(clockMock.instant()).thenReturn(Instant.ofEpochMilli(ANY_EPOCH_MILLI));
        when(autocompletionStrategyMock.complete(ANY_INPUT)).thenReturn(Optional.of(ANY_INPUT));
        AutocompleteResponse expected = new AutocompleteResponse(EXPECTED_REQUEST_ID, ANY_INPUT);

        // trigger
        AutocompleteResponse actual = autocompleteController.autocomplete(ANY_INPUT);

        // verify
        Assertions.assertThat(actual).isEqualTo(expected);
        Mockito.verify(autocompletionStrategyMock).complete(ANY_INPUT);
    }
}
