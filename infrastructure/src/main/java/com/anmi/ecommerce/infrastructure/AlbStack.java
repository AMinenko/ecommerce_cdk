package com.anmi.ecommerce.infrastructure;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.elasticloadbalancingv2.ApplicationLoadBalancer;
import software.amazon.awscdk.services.elasticloadbalancingv2.ApplicationLoadBalancerProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

public class AlbStack extends Stack {

    private final ApplicationLoadBalancer applicationLoadBalancer;

    public AlbStack(Construct scope, String id, StackProps props, AlbStackProps albProps) {
        super(scope, id, props);

        this.applicationLoadBalancer = new ApplicationLoadBalancer(this, "PublicAlb",
                ApplicationLoadBalancerProps.builder()
                        .vpc(albProps.vpc())
                        .loadBalancerName("ECommercePublicAlb")
                        .internetFacing(true)
                        .build());
    }

    public ApplicationLoadBalancer getApplicationLoadBalancer() {
        return applicationLoadBalancer;
    }
}

record AlbStackProps(
        Vpc vpc
) {
}