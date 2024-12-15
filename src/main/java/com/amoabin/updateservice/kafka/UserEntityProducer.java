package com.amoabin.updateservice.kafka;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Service
public class UserEntityProducer {

    private final String topicName = "generation-thirteen";
    private final Producer<String, byte[]> producer;
    private final Schema schema;

    public UserEntityProducer() {
        try {
            // Load the Avro schema file from the resources folder
            InputStream schemaStream = new ClassPathResource("avro/UserUpdate.avsc").getInputStream();
            schema = new Schema.Parser().parse(schemaStream);

            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.ByteArraySerializer");

            producer = new KafkaProducer<>(props);
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize UserEntityProducer", e);
        }
    }

    public void publishUserEntity(GenericRecord userEntityRecord) throws IOException {
        // ... existing implementation ...
    }

    // ... Rest of your code including producer close method
}
