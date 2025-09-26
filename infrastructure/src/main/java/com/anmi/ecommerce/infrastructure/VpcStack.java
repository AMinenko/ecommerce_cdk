package com.anmi.ecommerce.infrastructure;

import lombok.Getter;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ec2.VpcProps;
import software.constructs.Construct;

@Getter
public class VpcStack extends Stack {

    private Vpc vpc;

    public VpcStack(Construct scope, String id, StackProps props) {
        super(scope, id, props);

        vpc = new Vpc(this, "ProductService", VpcProps.builder()
                .vpcName("EcommerceVpc")
                .maxAzs(2)
                .natGateways(0)
                .build());

    }

}
