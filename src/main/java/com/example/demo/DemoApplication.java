package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@Slf4j
@EnableKafka
@EnableScheduling
@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

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

	@Override
	public void run(String... args) {
	}

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
		log.info("Doing healthcheck");
		log.info(kafkaTemplate.getProducerFactory().getConfigurationProperties().toString());
		ListenableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, "foo1");
		try {
			result.get();
			log.info("healthcheck succeeded");
		} catch (Throwable e) {
			log.warn("healthcheck failed");
			log.info(e.getMessage());
		}

	}


}



