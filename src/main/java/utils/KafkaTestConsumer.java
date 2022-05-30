package utils;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;
import java.util.stream.StreamSupport;

public class KafkaTestConsumer {


    public KafkaConsumer<String, String> SetConsumerObject () {

        Properties props = new Properties();
        props.setProperty("bootstrap.servers", "boostrap: nt-use1-qa-ehn-eh1.servicebus.windows.net:9093");

        //convert bytes to objects
        props.setProperty("key.deserializer", StringDeserializer.class.getName());
        props.setProperty("value.deserializer", StringDeserializer.class.getName());

        //handle autentication
        props.setProperty("security.protocol", "SASL_SSL");
        props.setProperty("sasl.mechanism", "PLAIN");
        props.setProperty("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"$ConnectionString\" password=\"Endpoint=sb://nt-use1-qa-ehn-eh1.servicebus.windows.net/;SharedAccessKeyName=temporalAccess;SharedAccessKey=W2HG8FkO2zCMKQQp719UUqVlUO2KTgD3+uiwCVGs4KU=\";");

        props.setProperty("group.id", "nautilus");

        //Create consumer object using KafkaConsumer class
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        return consumer;
    }

    public ConsumerRecord<String,String> readKafkaMessagess (KafkaConsumer<String, String> consumer,String topic, String transferId, String event) {

        //From wich topic you have to consume
        consumer.subscribe(Collections.singleton(topic));

        //The message is searched by transfer id and only one message is returned
        boolean recordFound = false;
        int i = 0;
        ConsumerRecord<String,String> recordToReturn = null;
        while(true){

            ConsumerRecords<String, String> records = consumer.poll(10);
            for(ConsumerRecord<String,String> record:records){
                if (record.value().contains(transferId) && this.HeaderBuilder(record,"ce_type").equals(event)){
                    recordToReturn = record;
                    recordFound = true;
                    break;
                }
            }
            i++;
            if(i>175 || recordFound == true) break;
        }
        return recordToReturn;
    }

    public String HeaderBuilder(ConsumerRecord<String,String> record,String field){
        return new String(StreamSupport
                .stream(record.headers().spliterator(), false)
                .filter(header -> header.key().equals(field))
                .findFirst()
                .get()
                .value());
    }


}
