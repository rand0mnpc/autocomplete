package com.dparks.autocomplete.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
@ComponentScan(basePackages = "com.dparks.autocomplete")
public class AutocompleteSpringConfiguration {

    @Bean("systemUtcClock")
    Clock systemUtcClock() {
        return Clock.systemUTC();
    }

    @Bean("sourceFilePath")
    String sourceFilePath() {
        return "src/main/resources/static/words.txt";
    }
}
