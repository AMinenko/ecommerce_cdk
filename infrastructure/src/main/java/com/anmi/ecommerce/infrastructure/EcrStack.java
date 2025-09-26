package com.anmi.ecommerce.infrastructure;

import lombok.Getter;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecr.RepositoryProps;
import software.amazon.awscdk.services.ecr.TagMutability;
import software.constructs.Construct;


@Getter
public class EcrStack extends Stack {

    private Repository productServiceRepository;

    public EcrStack(Construct scope, String id, StackProps props) {
        super(scope, id, props);

        productServiceRepository = new Repository(this, "ProductsService", RepositoryProps.builder()
                .repositoryName("productsservice")
                .removalPolicy(RemovalPolicy.DESTROY)
                .imageTagMutability(TagMutability.MUTABLE)
                .build());

    }

}
