package checkpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import utils.EventsConstants;
import utils.KafkaTestConsumer;
import utils.KafkaTestConsumer2;

import java.util.concurrent.TimeUnit;


public class KafkaMessagesAsserts {

    @Autowired
    private KafkaTestConsumer2 kafkaTestConsumer;

    private String topicName = "ncb_operations";

    public void NcbOperationsTopic() throws Exception {
        //Validate Kafka Event

        kafkaTestConsumer.getLatch().await(100000, TimeUnit.MILLISECONDS);
        Assert.assertEquals(0L, kafkaTestConsumer.getLatch().getCount());
        //Validate kafka topic
        Assert.assertEquals(kafkaTestConsumer.getPayload().topic(), topicName);

        //Validate CloudEvent Headers
        Assert.assertEquals(EventsConstants.CE_TYPE.trim(), kafkaTestConsumer.HeaderBuilder("ce_type"));
        Assert.assertEquals(EventsConstants.CE_SOURCE.toString().trim(), kafkaTestConsumer.HeaderBuilder("ce_source"));
        Assert.assertEquals(EventsConstants.CE_TYPE.trim(), kafkaTestConsumer.HeaderBuilder("content-type"));
        Assert.assertEquals(EventsConstants.CE_SPECVERSION.trim(), kafkaTestConsumer.HeaderBuilder("ce_specversion"));
        Assert.assertEquals(EventsConstants.CE_PAYLOADVERSION.trim(), kafkaTestConsumer.HeaderBuilder("ce_payloadversion"));
    }
}
