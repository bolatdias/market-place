package com.example.marketplace.payload;

import lombok.Getter;

@Getter
public class Token {
    private String accessToken;
    private String tokenType = "Bearer";

    public Token(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
