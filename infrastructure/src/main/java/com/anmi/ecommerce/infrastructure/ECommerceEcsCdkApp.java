package com.anmi.ecommerce.infrastructure;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.HashMap;
import java.util.Map;

public class ECommerceEcsCdkApp {
    public static void main(final String[] args) {
        String account = System.getenv("AWS_ACCOUNT");
        String region = System.getenv("AWS_REGION");

        App app = new App();

        Environment env = Environment.builder()
                .account(account)
                .region(region)
                .build();

        new EcrStack(app, "Ecr", StackProps.builder()
                .env(env)
                .build());

        Map<String, String> infraTag = new HashMap<>();
        infraTag.put("team", "com.anmi");
        infraTag.put("cost", "ECommerceInfra");

        Map<String, String> productsServiceTags = new HashMap<>();
        infraTag.put("team", "com.anmi");
        infraTag.put("cost", "ProductsService");

        app.synth();
    }
}

