package com.dparks.autocomplete.integration;

import com.dparks.autocomplete.application.AutocompleteApplication;
import com.dparks.autocomplete.model.AutocompleteResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.assertj.core.api.Assertions;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(classes = AutocompleteApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AutocompleteApplicationIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void testAutocomplete() {
        // setup
        Map<String, String> inputToExpectedMap = new HashMap<>();
        inputToExpectedMap.put("absorpta", "absorptance");
        inputToExpectedMap.put("accomplet", "accompletive");
        inputToExpectedMap.put("toyt", "toytown");
        inputToExpectedMap.put("0", null);
        inputToExpectedMap.put("", null);
        String endpointFormatString = "http://localhost:%s/autocomplete?inputText=%s";

        // trigger & verify
        inputToExpectedMap.forEach((input, expected) -> {
            String actual = getForObject(endpointFormatString, input).getPredictedText();
            Assertions.assertThat(actual).isEqualTo(expected);
        });
    }

    private AutocompleteResponse getForObject(String endpointFormatString, String input) {
        return testRestTemplate.getForObject(String.format(endpointFormatString, port, input), AutocompleteResponse.class);
    }
}
