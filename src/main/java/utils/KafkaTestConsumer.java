package utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class KafkaTestConsumer {

    public void readKafkaMessagess (){

        String topic = "transfer";

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "boostrap: nt-use1-qa-ehn-eh1.servicebus.windows.net:9093");

        //convert bytes to objects
        props.setProperty("key.deserializer", StringDeserializer.class.getName());
        props.setProperty("value.deserializer", StringDeserializer.class.getName());

        //handle autentication
        props.setProperty("security.protocol", "SASL_SSL");
        props.setProperty("sasl.mechanism", "PLAIN");
        props.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=\"Endpoint=sb://nt-use1-qa-ehn-eh1.servicebus.windows.net/;SharedAccessKeyName=temporalAccess;SharedAccessKey=W2HG8FkO2zCMKQQp719UUqVlUO2KTgD3+uiwCVGs4KU=\";");

        props.setProperty("group.id","nautilus");

        //Create consumer object using KafkaConsumer class
        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(props);

        //From wich topic you have to consume
        consumer.subscribe(Collections.singleton(topic));

        while(true){
            ConsumerRecords<String, String> records = consumer.poll(100);
            for(ConsumerRecord<String,String> record:records){
                System.out.println(record.headers().);
                System.out.println(record.value());
            }
        }




    }
}
