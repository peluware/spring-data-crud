package com.peluware.springframework.crud.core.autoconfigurations;

import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

@Slf4j
@AutoConfiguration
public class SpringDataCrudAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Converter<String, Node> rsqlQueryConverter() {
        final var parser = new RSQLParser();
        return source -> {
            if (source.isBlank()) {
                return null; // Return null for empty or null input
            }
            try {
                return parser.parse(source);
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid RSQL query: " + source, e);
            }
        };
    }
}
