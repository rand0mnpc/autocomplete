package com.dparks.autocomplete.model;

import java.util.Optional;

public interface AutocompletionStrategy {

    /**
     * This interface provides a layer of abstraction to how the application will perform the autocomplete allowing for
     * the usage of a more generic orchestration pattern in the controller.
     * @param inputText user input text
     * @return predicted text
     */
    Optional<String> complete(String inputText);
}
