package com.example.demo.Config;

import graphql.schema.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class GraphQLScalarConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> {
            wiringBuilder.scalar(longScalar());
            wiringBuilder.scalar(dateScalar());
        };
    }

    @Bean
    public GraphQLScalarType longScalar() {
        return GraphQLScalarType.newScalar()
                .name("Long")
                .coercing(new Coercing<Long, Long>() {
                    @Override
                    public Long serialize(Object data) {
                        if (data instanceof Number) return ((Number) data).longValue();
                        if (data instanceof String) return Long.parseLong((String) data);
                        return null;
                    }

                    @Override
                    public Long parseValue(Object input) {
                        return serialize(input);
                    }

                    @Override
                    public Long parseLiteral(Object input) {
                        return serialize(input);
                    }
                }).build();
    }

    @Bean
    public GraphQLScalarType dateScalar() {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .coercing(new Coercing<Date, String>() {
                    private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

                    @Override
                    public String serialize(Object data) {
                        if (data instanceof Date) return format.format((Date) data);
                        return null;
                    }

                    @Override
                    public Date parseValue(Object input) {
                        try {
                            return format.parse(input.toString());
                        } catch (Exception e) {
                            return null;
                        }
                    }

                    @Override
                    public Date parseLiteral(Object input) {
                        return parseValue(input);
                    }
                }).build();
    }
}