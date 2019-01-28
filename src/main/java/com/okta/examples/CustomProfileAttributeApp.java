package com.okta.examples;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.okta.sdk.authc.credentials.TokenClientCredentials;
import com.okta.sdk.client.Client;
import com.okta.sdk.client.Clients;
import com.okta.sdk.resource.ExtensibleResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class CustomProfileAttributeApp {
    
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {

        InputStream in = CustomProfileAttributeApp.class.getResourceAsStream("/new-profile-attribute.json");
        Map<String, Object> apiBodyFromJson = mapper.readValue(in, new TypeReference<Map<String, Object>>(){});

        Client client = Clients.builder()
            .setOrgUrl(args[0])
            .setClientCredentials(new TokenClientCredentials(args[1]))
            .build();

        ExtensibleResource resource = client.instantiate(ExtensibleResource.class);
        resource.put("definitions", apiBodyFromJson.get("definitions"));

        ExtensibleResource result = client.http()
            .setBody(resource)
            .post("/api/v1/meta/schemas/user/default", ExtensibleResource.class);
    }
}
