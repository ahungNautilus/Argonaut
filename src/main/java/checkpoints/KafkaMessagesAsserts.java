package checkpoints;

import org.testng.Assert;
import utils.EventsConstants;
import utils.KafkaTestConsumer;

import java.util.concurrent.TimeUnit;


public class KafkaMessagesAsserts {

    private KafkaTestConsumer kafkaTestConsumer;
    private String topicName = "ncb_operations";

    public void NcbOperationsTopic() throws Exception {
        //Validate Kafka Event



        /*
        Assert.assertEquals(EventsConstants.CE_TYPE.trim(), kafkaTestConsumer.HeaderBuilder("ce_type"));
        Assert.assertEquals(EventsConstants.CE_SOURCE.toString().trim(), kafkaTestConsumer.HeaderBuilder("ce_source"));
        Assert.assertEquals(EventsConstants.CE_TYPE.trim(), kafkaTestConsumer.HeaderBuilder("content-type"));
        Assert.assertEquals(EventsConstants.CE_SPECVERSION.trim(), kafkaTestConsumer.HeaderBuilder("ce_specversion"));
        Assert.assertEquals(EventsConstants.CE_PAYLOADVERSION.trim(), kafkaTestConsumer.HeaderBuilder("ce_payloadversion"));

         */
    }
}
