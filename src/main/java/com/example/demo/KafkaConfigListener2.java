package com.example.demo;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfigListener2 {

    @Bean
    public KafkaTemplate<String, String> kafkaTemplateListener2() {
        return new KafkaTemplate<>(producerFactoryListener2());
    }

    public Map<String, Object> producerConfigsListener2() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, System.getenv("BOOTSTRAP_SERVERS_2"));
        props.put("sasl.jaas.config", System.getenv("SASL_JAAS_CONFIG_2"));
        props.put("security.protocol", System.getenv("SECURITY_PROTOCOL_2"));
        props.put("ssl.endpoint.identification.algorithm", System.getenv("ENDPOINT_ID_ALGORITHM_2)"));
        props.put("sasl.mechanism", System.getenv("SASL_MECHANISM_2"));
        props.put("retries", "0");
        props.put("delivery.timeout.ms", "4000");
        props.put("request.timeout.ms", "3000");
        props.put("key.serializer", StringSerializer.class);
        props.put("value.serializer", StringSerializer.class);
        return props;
    }

    public ProducerFactory<String, String> producerFactoryListener2() {
        return new DefaultKafkaProducerFactory<>(producerConfigsListener2());
    }

}
