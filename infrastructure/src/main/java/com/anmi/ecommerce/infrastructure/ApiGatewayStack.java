package com.anmi.ecommerce.infrastructure;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigatewayv2.CfnApi;
import software.amazon.awscdk.services.apigatewayv2.CfnIntegration;
import software.amazon.awscdk.services.apigatewayv2.CfnRoute;
import software.amazon.awscdk.services.apigatewayv2.CfnStage;
import software.amazon.awscdk.services.elasticloadbalancingv2.ApplicationLoadBalancer;
import software.constructs.Construct;

public class ApiGatewayStack extends Stack {

    public ApiGatewayStack(Construct scope, String id, StackProps props, ApiGatewayStackProps apiProps) {
        super(scope, id, props);

        // Create HTTP API
        CfnApi httpApi = CfnApi.Builder.create(this, "HttpApi")
                .name("ECommerceHttpApi")
                .protocolType("HTTP")
                .build();

        // Integration with ALB
        CfnIntegration albIntegration = CfnIntegration.Builder.create(this, "AlbIntegration")
                .apiId(httpApi.getRef())
                .integrationType("HTTP_PROXY")
                .integrationMethod("ANY")
                .integrationUri("http://" + apiProps.applicationLoadBalancer().getLoadBalancerDnsName())
                .payloadFormatVersion("1.0")
                .build();

        // Add ANY route
        CfnRoute.Builder.create(this, "DefaultRoute")
                .apiId(httpApi.getRef())
                .routeKey("ANY /{proxy+}")
                .target("integrations/" + albIntegration.getRef())
                .build();

        // Default stage
        CfnStage.Builder.create(this, "DefaultStage")
                .apiId(httpApi.getRef())
                .stageName("$default")
                .autoDeploy(true)
                .build();
    }
}

record ApiGatewayStackProps(
        ApplicationLoadBalancer applicationLoadBalancer
) {
}
