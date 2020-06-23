package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
	private KafkaTemplate<String, String> template;

	@Value("${healthcheck-topic}")
	private String topic;

	@Override
	public void run(String... args) {
	}

	@Scheduled(fixedDelay = 1000)
	public void healthCheck() {
		log.info("Doing healthcheck");
		ListenableFuture<SendResult<String, String>> result = this.template.send(topic, "foo1");
		try {
			log.info("healthcheck succeeded");
			result.get();
		} catch (Throwable e) {
			log.warn("healthcheck failed");
			log.info(e.getMessage());
		}
	}
}



