package com.polarbookshop.catalog_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// Segna la classe come origine per proprietà di configurazione che iniziano col prefisso "polar"
@ConfigurationProperties(prefix = "polar")
public class PolarProperties {

    private String greeting; // campo per il polar.greeting custom(prefisso + nome campo) passato come String

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
