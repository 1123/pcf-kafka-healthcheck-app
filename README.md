## Spring Boot Health Check Application to be Deployed to PCF for Testing Connection to Kafka

This is a Spring Boot application to check connectivity to Kafka on an ongoing basis. The result of the HealthCheck is written to the logs. 

### Running the APP

* copy application-cloud.properites to your own property file. E.g. application-dev.properties
* fill in your credentials for connecting to Kafka or Confluent Cloud
* adjust your desired delivery.timeout.ms in the Kafka producer configuration
* edit manifest.yml and set your SPRING_PROFILIES_ACTIVE environment variable to "dev". 
* build the app: mvn clean package
* login to cloudfoundry via the cli
* push your app: cf push -f manifest.yml
