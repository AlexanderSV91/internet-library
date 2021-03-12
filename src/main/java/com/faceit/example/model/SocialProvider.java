package com.faceit.example.model;

public enum SocialProvider {

    GOOGLE("google"),
    GITHUB("github"),
    LOCAL("local");

    private final String providerType;

    public String getProviderType() {
        return providerType;
    }

    SocialProvider(String providerType) {
        this.providerType = providerType;
    }
}
