package com.dparks.autocomplete.model;

import java.util.Set;

public interface SourceTextLoader {

    /**
     * This interface provides a layer of abstraction to the processing of source text. This allows the application to
     * easily swap out it loads the source text with no changes. The time at which the application loads the source text
     * is purposefully delegated to the caller.
     * @return a set of source words
     */
    Set<String> load();
}
