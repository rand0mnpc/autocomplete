package com.dparks.autocomplete.integration;

import com.dparks.autocomplete.application.AutocompleteApplication;
import com.dparks.autocomplete.model.AutocompleteResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.assertj.core.api.Assertions;

import java.util.stream.Stream;

@SpringBootTest(classes = AutocompleteApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutocompleteApplicationIntegrationTests {

    private static final String ENDPOINT_FORMAT_STRING = "http://localhost:%s/autocomplete?inputText=%s";

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void testAutocompleteParametrized(String prefix, String expected) {
        // trigger & verify
        String actual = getForObject(prefix).getPredictedText();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private AutocompleteResponse getForObject(String input) {
        return testRestTemplate.getForObject(
                String.format(AutocompleteApplicationIntegrationTests.ENDPOINT_FORMAT_STRING, port, input),
                AutocompleteResponse.class);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of("absorpta", "absorptance"),
                Arguments.of("accomplet", "accompletive"),
                Arguments.of("toyt", "toytown"),
                Arguments.of("0", null));
    }
}
