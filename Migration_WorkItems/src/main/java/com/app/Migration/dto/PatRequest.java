package com.app.Migration.dto;

import com.app.Migration.Entity.TokenType;

public class PatRequest {
    private String token;
    private TokenType type;

    // Getters & Setters

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }
}