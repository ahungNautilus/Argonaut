package utils;


import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.stream.StreamSupport;

@Component
@Data
public class KafkaTestConsumer2 {

    private CountDownLatch latch = new CountDownLatch(1);
    private ConsumerRecord payload = null;

    @KafkaListener(topics = "ncb_operations", groupId = "1")
    public void receive(ConsumerRecord<?,?> consumerRecord) {
        setPayload(consumerRecord);
        latch.countDown();
    }

    public String HeaderBuilder(String field){
        return new String(StreamSupport
                .stream(this.getPayload().headers().spliterator(), false)
                .filter(header -> header.key().equals(field))
                .findFirst()
                .get()
                .value());
    }
}
