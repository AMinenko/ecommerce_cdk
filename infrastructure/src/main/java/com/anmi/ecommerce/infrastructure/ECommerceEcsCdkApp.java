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

        Map<String, String> infraTag = new HashMap<>();
        infraTag.put("team", "com.anmi");
        infraTag.put("cost", "ECommerceInfra");

        Map<String, String> productsServiceTags = new HashMap<>();
        infraTag.put("team", "com.anmi");
        infraTag.put("cost", "ProductsService");

        Environment env = Environment.builder()
                .account(account)
                .region(region)
                .build();

        App app = new App();

        EcrStack ecr = new EcrStack(app, "Ecr", StackProps.builder()
                .env(env)
                .build());

        VpcStack vpcStack = new VpcStack(app, "Vpc", StackProps.builder()
                .env(env)
                .tags(infraTag)
                .build());

        ClusterStack clusterStack = new ClusterStack(app, "Cluster", StackProps.builder()
                .env(env)
                .tags(infraTag)
                .build(), new ClusterStackProps(vpcStack.getVpc()));

        clusterStack.addDependency(vpcStack);

        AlbStack albStack = new AlbStack(app, "Alb", StackProps.builder()
                .env(env)
                .tags(infraTag)
                .build(), new AlbStackProps(vpcStack.getVpc()));

        ApiGatewayStack apiGatewayStack = new ApiGatewayStack(app, "ApiGW", StackProps.builder()
                .env(env)
                .tags(infraTag)
                .build(), new ApiGatewayStackProps(albStack.getApplicationLoadBalancer()));

        ProductsServiceStack productsServiceStack = new ProductsServiceStack(app, "ProductsService",
                StackProps.builder().env(env)
                        .tags(productsServiceTags)
                        .build(),

                new ProductsServiceProps(
                        vpcStack.getVpc(),
                        clusterStack.getCluster(),
                        albStack.getApplicationLoadBalancer(),
                        ecr.getProductServiceRepository())
        );

        productsServiceStack.addDependency(vpcStack);
        productsServiceStack.addDependency(clusterStack);
        productsServiceStack.addDependency(albStack);
        productsServiceStack.addDependency(ecr);

        app.synth();
    }
}

