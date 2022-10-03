package com.kenzie.appserver.utilities;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;

public class DynamoDbClientProvider {

    public static AmazonDynamoDB getDynamoDBClient() {
        return getDynamoDBClient(Regions.US_EAST_1);
    }

    public static AmazonDynamoDB getDynamoDBClient(Regions region) {
        if (null == region) {
            throw new IllegalArgumentException("Region cannot be null!");
        }

        return AmazonDynamoDBClientBuilder
                .standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion(region)
                .build();
    }
}
