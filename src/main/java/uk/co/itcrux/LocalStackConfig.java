package uk.co.itcrux;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsync;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClientBuilder;
import com.amazonaws.services.kinesis.AmazonKinesisAsync;
import com.amazonaws.services.kinesis.AmazonKinesisAsyncClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalStackConfig {

    @Value("${cloud.aws.credentials.accesskey}")
    private String awsAccessKey;

    @Value("${cloud.aws.credentials.secretkey}")
    private String awsSecretKey;

    @Bean
    AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)
        );
    }

    @Bean
    public AmazonKinesisAsync localStackKinesis(AWSCredentialsProvider awsCredentialsProvider,
                                                @Value("${kinesis.endpoint}") String endpoint,
                                                @Value("${kinesis.region}") String awsRegion) {
        System.setProperty("com.amazonaws.sdk. disableCbor", "1");
        return AmazonKinesisAsyncClient.asyncBuilder()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(endpoint, awsRegion)
                )
                .withCredentials(awsCredentialsProvider)
                .build();
    }


    @Bean
    public AmazonDynamoDBAsync localDynamoDB(AWSCredentialsProvider awsCredentialsProvider,
                                             @Value("${dynamo.endpoint}") String endpoint,
                                             @Value("${dynamo.region}") String awsRegion) {
        return AmazonDynamoDBAsyncClientBuilder.standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(endpoint, awsRegion)
                )
                .withCredentials(awsCredentialsProvider)
                .build();
    }

}
