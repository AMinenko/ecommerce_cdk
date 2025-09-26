package com.anmi.ecommerce.infrastructure;

import lombok.Getter;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ClusterProps;
import software.constructs.Construct;

@Getter
public class ClusterStack extends Stack {

    private final Cluster cluster;

    public ClusterStack(Construct scope, String id, StackProps props, ClusterStackProps clusterStackProps) {
        super(scope, id, props);

        cluster = new Cluster(this, "Cluster", ClusterProps.builder()
                .clusterName("ECommerce")
                .vpc(clusterStackProps.vpc())
                .containerInsights(true)
                .build());

    }
}

record ClusterStackProps(
        Vpc vpc
) {
}
