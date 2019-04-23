package com.evil.cbs_ticketvalidator.data;

public class AccessToken {
    private String accessToken;
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        // OAuth requires the first letter of Authorization HTTP
        // header value for token type to be uppercase
        if (!Character.isUpperCase(tokenType.charAt(0))) {
            tokenType = Character.toString(tokenType.charAt(0))
                    .toUpperCase() + tokenType.substring(1);
        }

        return tokenType;
    }
}