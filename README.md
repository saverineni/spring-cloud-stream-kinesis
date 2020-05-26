## RUNNING DOCKER:               
  
  ```   
        docker run -e LAMBDA_EXECUTOR=docker -p 4568:4568 -e SERVICES=lambda -p 8080:8080 localstack/localstack &          
        docker run -p 4568:4568  -p4569:4569 SERVICES="kinesis,dynamodb" localstack/localstack
        docker run -d -e DEBUG=1 -e SERVICES="kinesis,dynamodb" -e DEFAULT_REGION="us-east-1" -e TEST_AWS_ACCOUNT_ID="0000000000" --rm --privileged --name localstack_main -p 4568:4568  -p4569:4569 -v "/tmp/localstack:/tmp/localstack" -v "/var/run/docker.sock:/var/run/docker.sock" -e DOCKER_HOST="unix:///var/run/docker.sock" -e HOST_TMP_FOLDER="/tmp/localstack" "localstack/localstack"
  ```
 
### Kinesis  
         aws --endpoint-url=http://localhost:4568 kinesis list-streams
         aws --endpoint-url=http://localhost:4568 kinesis create-stream --stream-name "Chapter4KinesisStream" --shard-count 2
         aws --endpoint-url=http://localhost:4568 kinesis describe-stream --stream-name "Chapter4KinesisStream"
         
         PUT
         aws --endpoint-url=http://localhost:4568 kinesis put-record --stream-name "Chapter4KinesisStream" --data "DUMMYDATA" --partition-key "123"
         
         get-shard-iterator
         aws --endpoint-url=http://localhost:4568 kinesis get-shard-iterator --stream-name "Chapter4KinesisStream" --shard-id "shardId-000000000000" --shard-iterator-type "LATEST"
         
         --shard-iterator
         aws kinesis --endpoint-url=http://localhost:4568 get-records --shard-iterator "AAAAAAAAAAGtyEHIh0Mfjilv7Q0OdS3Vfx4tBdOsrNQrE2SoQIH9JHbv8raP3Jvuj4w9OhFSnsZl80cGgI2S8i/UVM3nUMRsHF6gj5iZGandpMARfwdTzMa0St6wJXx90MpBjiUWYQZe2UKsnh3xHkxZaLMX8EXpx32Db5sZAAZd4UlH3zW8qOEbrRaZpNXCxV+1FxEP4xbVmxAevl+6FYC18+EQmWQV"

### DynamoDB
         
         aws --endpoint-url=http://localhost:4569 dynamodb list-tables
         aws --endpoint-url=http://localhost:4569 dynamodb scan --table-name app-lockregistry
         aws --endpoint-url=http://localhost:4569 dynamodb delete-table --table-name app-metadata-store
