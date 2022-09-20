package com.dparks.autocomplete.model;

import lombok.ToString;
import lombok.Value;

@Value
@ToString
public class AutocompleteRequest {
    String requestID;
    String inputText;
}
