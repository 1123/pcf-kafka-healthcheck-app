package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@EnableKafka
@EnableScheduling
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplateListener1;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplateListener2;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplateListener3;

	@Value("${healthcheck-topic}")
	private String topic;

	@Scheduled(fixedDelay = 1000)
	public void healthCheck() {
		log.info("Checking health of listener1");
		this.healthCheckForListener(kafkaTemplateListener1);
		log.info("Checking health of listener2");
		this.healthCheckForListener(kafkaTemplateListener2);
		log.info("Checking health of listener3");
		this.healthCheckForListener(kafkaTemplateListener3);
	}

	private void healthCheckForListener(KafkaTemplate<String, String> kafkaTemplate) {
		log.info("Health check for listener {}", kafkaTemplate.getProducerFactory().getConfigurationProperties().toString());
		ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, "" + System.currentTimeMillis());
		try {
			result.get();
			log.info("healthcheck succeeded");
		} catch (Throwable e) {
			log.warn("healthcheck failed");
			log.info(e.getMessage());
		}
	}

}



