package com.dparks.autocomplete.application;

import com.dparks.autocomplete.model.AutocompleteRequest;
import com.dparks.autocomplete.model.AutocompleteResponse;
import com.dparks.autocomplete.model.AutocompletionStrategy;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Clock;
import java.util.Optional;

@RestController
public class AutocompleteController {

    @NonNull
    private final Clock systemUtcClock;

    @NonNull
    private final AutocompletionStrategy autocompletionStrategy;

    @Autowired
    public AutocompleteController(
            @Qualifier("systemUtcClock") Clock systemUtcClock,
            @Qualifier("treeAutocompletionForShortestMatchStrategy") AutocompletionStrategy autocompletionStrategy) {

        this.systemUtcClock = systemUtcClock;
        this.autocompletionStrategy = autocompletionStrategy;
    }

    /**
     * This mapping generates requestIDs based on time stamp and a hash of the input. It also contains the generic
     * orchestration for the autocompletion workflow.
     * @param inputText user input text
     * @return auto complete response. Can vary based on strategy
     */
    @GetMapping("/autocomplete")
    public AutocompleteResponse autocomplete(@RequestParam(value = "inputText") String inputText) {
        String requestID = String.format("%s-%s", systemUtcClock.instant().toEpochMilli(), inputText.hashCode());
        AutocompleteRequest autocompleteRequest = new AutocompleteRequest(requestID, inputText);
        Optional<String> autoCompletedString = autocompletionStrategy.complete(autocompleteRequest.inputText());
        return new AutocompleteResponse(requestID, autoCompletedString.orElse(null));
    }
}
