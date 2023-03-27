package com.devwinter.postservice.config;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarkDownPlainText {
    @Bean
    public Parser parser() {
        return Parser.builder()
                     .build();
    }

    @Bean
    public TextContentRenderer textContentRenderer() {
        return TextContentRenderer.builder()
                                  .build();
    }
}
