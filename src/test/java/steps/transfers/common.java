package steps.transfers;

import checkpoints.TransfersResponseAsserts;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.google.gson.Gson;
import io.restassured.response.Response;
import models.KafkaEvents.TopTransfer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.json.JSONObject;
import org.testng.Assert;
import utils.CreateBodyContent;
import utils.KafkaTestConsumer;
import utils.RequestMakers;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class common {

    JSONObject inputBody, jsonResponse, jsonKafkaEventValue;
    Response response;
    TopTransfer inputTopTransfer, responseTopTransfer;
    KafkaTestConsumer kafkaConsumer;
    KafkaConsumer<String, String> consumer;


    String accountReference;
    String bankIdentifier;



    @Given("a payload:")
    public void a_payload(io.cucumber.datatable.DataTable dataTable) {

        ArrayList<String> keys = new ArrayList(), values = new ArrayList();
        keys.add("source_account_id");
        keys.add("source_user_id");
        keys.add("amount");
        keys.add("currency");
        keys.add("comments");
        keys.add("transfer_type");
        keys.add("external_reference_id");
        values.add(dataTable.cell(0,1));
        values.add(dataTable.cell(1,1));
        values.add(dataTable.cell(2,1));
        values.add(dataTable.cell(3,1));
        values.add(dataTable.cell(4,1));
        values.add(dataTable.cell(5,1));
        values.add(dataTable.cell(6,1));
        inputBody = CreateBodyContent.setBodyPostAccount(keys, values);

        accountReference = dataTable.cell(7,1);
        bankIdentifier = dataTable.cell(8,1);

    }

    @When("perform a POST to transfers")
    public void perform_a_post_to_transfers() {
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJodHRwczovL2x5bmsudXMvdGllciI6IlZVMiIsImlzcyI6Imh0dHBzOi8vZGV2LXlpdTg3a3BjLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2MjcwMTJlYzhjZjRmNTAwNjhhMmJiYmYiLCJhdWQiOlsiaHR0cDovL2FwcGNvbnN1bWVyYXV0aGJmZiIsImh0dHBzOi8vZGV2LXlpdTg3a3BjLnVzLmF1dGgwLmNvbS91c2VyaW5mbyJdLCJpYXQiOjE2MjMzNTg2NzEsImV4cCI6MTYyNDAzMzQ0MSwiYXpwIjoidGRpNHoxb2ZSZ0xVRlhUVUtUOVJsYTdhcEZsczNyMXYiLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIG9mZmxpbmVfYWNjZXNzIiwiZ3R5IjoicGFzc3dvcmQiLCJqdGkiOiIzM2Y4YmFjYy02YzU5LTRlNGUtODcyMy1jYzk5ZWIwMTlkZjAifQ.r23n6VVO-DFLaxFcwioNx_FNjv6uhR7n4htFkuSYt_g";
        response = RequestMakers.makePostRequestWithToken("http://nonprod-aks.api-nautilus.net:30616/transfers/"
                ,inputBody.toString(),jwt);
        jsonResponse = new JSONObject(response.getBody().asString());
        System.out.println(jsonResponse);

    }

    @Then("verify that the status code it is {string}")
    public void verify_that_the_status_code_it_is(String statusCode) {
        TransfersResponseAsserts.validateStatusCode(parseInt(statusCode),response.getStatusCode());
    }

    @And("all the data in the response body is correct and valid")
    public void all_the_data_in_the_response_body_is_correct_and_valid() {
        Gson g = new Gson();
        inputTopTransfer = g.fromJson(inputBody.toString(), TopTransfer.class);
        responseTopTransfer = g.fromJson(jsonResponse.toString(), TopTransfer.class);
        inputTopTransfer.setTransfer_id(responseTopTransfer.getTransfer_id());
        //esto hay que arreglarlo y pasarle la fecha actual con java y cortar los minutos para que siempre coincida
        inputTopTransfer.setDatetime(responseTopTransfer.getDatetime());
        inputTopTransfer.setStatus("PENDING");
        inputTopTransfer.setCategory("PERSONAL");
        try{
            TransfersResponseAsserts.topResponseComparer(inputTopTransfer, responseTopTransfer);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @And("the team.nautilus.event.transfer.ncb.topup event was generated on the ncb_operations topic")
    public void the_team_nautilus_event_transfer_ncb_topup_event_was_generated_on_the_ncb_operations_topic() {
        kafkaConsumer = new KafkaTestConsumer();
        consumer = kafkaConsumer.SetConsumerObject();
        ConsumerRecord<String,String> record = kafkaConsumer.readKafkaMessagess(consumer,"ncb_operations",jsonResponse.get("transfer_id").toString(),"team.nautilus.event.transfer.ncb.topup");
        jsonKafkaEventValue = new JSONObject(record.value());

        //headers verification
            Assert.assertEquals("team.nautilus.event.transfer.ncb.topup",kafkaConsumer.HeaderBuilder(record,"ce_type"), "Kafka headers - ncb_operations topic - ce_type does not match");
        Assert.assertEquals("/nautilus_core/transfer_ms",kafkaConsumer.HeaderBuilder (record,"ce_source"),"Kafka headers - ncb_operations topic - ce_source does not match");
        Assert.assertEquals("1.0",kafkaConsumer.HeaderBuilder(record,"ce_specversion"),"Kafka headers - ncb_operations topic - ce_specversion does not match");
        Assert.assertEquals("application/json",kafkaConsumer.HeaderBuilder(record,"content-type"),"Kafka headers - ncb_operations topic - content-type does not match");
        Assert.assertEquals("0.0.1",kafkaConsumer.HeaderBuilder(record,"ce_payloadversion"),"Kafka headers - ncb_operations topic - ce_payloadversion does not match");

        System.out.println("Kafka - header validations OK");

        //value verification
        Assert.assertEquals(String.valueOf(inputTopTransfer.getTransfer_id()).trim(),jsonKafkaEventValue.get("transferId").toString().trim(), "Kafka Value - ncb_operations topic - transferId does not match");
        Assert.assertEquals(inputTopTransfer.getSource_user_id().trim(),jsonKafkaEventValue.get("userId").toString().trim(), "Kafka Value - ncb_operations topic - userId does not match");
        Assert.assertEquals(bankIdentifier,jsonKafkaEventValue.get("bankIdentifier").toString().trim(), "Kafka Value - ncb_operations topic - bankIdentifier does not match");
        Assert.assertEquals(accountReference,jsonKafkaEventValue.get("accountReference").toString().trim(), "Kafka Value - ncb_operations topic - accountReference does not match");
        Assert.assertEquals(inputTopTransfer.getSource_account_id().trim(),jsonKafkaEventValue.get("accountId").toString().trim(), "Kafka Value - ncb_operations topic - accountId does not match");

        Double amount = Double.valueOf(jsonKafkaEventValue.get("amount").toString().trim());
        DecimalFormat format = new DecimalFormat("0.#");

        Assert.assertEquals(inputTopTransfer.getAmount().trim(),format.format(amount), "Kafka Value - ncb_operations topic - amount does not match");
        Assert.assertEquals(inputTopTransfer.getCurrency().trim(),jsonKafkaEventValue.get("currency").toString().trim(), "Kafka Value - ncb_operations topic - currency does not match");

        System.out.println("Kafka - Value validations OK");

    }

    @And("the team.nautilus.event.balance.ncb.topup event was generated on the balance topic")
    public void the_team_nautilus_event_balance_ncb_topup_event_was_generated_on_the_balance_topic() {

        kafkaConsumer = new KafkaTestConsumer();
        ConsumerRecord<String,String> record = kafkaConsumer.readKafkaMessagess(consumer,"balance",jsonResponse.get("transfer_id").toString(),"team.nautilus.event.balance.ncb.topup");
        jsonKafkaEventValue = new JSONObject(record.value());

        //headers verification
        Assert.assertEquals("team.nautilus.event.balance.ncb.topup",kafkaConsumer.HeaderBuilder(record,"ce_type"), "Kafka headers - balance topic - ce_type does not match");
        Assert.assertEquals("/external_banks/ncb_stream_processor",kafkaConsumer.HeaderBuilder (record,"ce_source"),"Kafka headers - balance topic - ce_source does not match");
        Assert.assertEquals("1.0",kafkaConsumer.HeaderBuilder(record,"ce_specversion"),"Kafka headers - balance topic - ce_specversion does not match");
        Assert.assertEquals("application/json",kafkaConsumer.HeaderBuilder(record,"content-type"),"Kafka headers - balance topic - content-type does not match");
        Assert.assertEquals("0.0.2",kafkaConsumer.HeaderBuilder(record,"ce_payloadversion"),"Kafka headers - balance topic - ce_payloadversion does not match");

        System.out.println("Kafka - header validations OK");

        Assert.assertEquals(String.valueOf(inputTopTransfer.getTransfer_id()).trim(),jsonKafkaEventValue.get("transferId").toString().trim(), "Kafka Value - ncb_operations topic - transferId does not match");
        Assert.assertEquals(inputTopTransfer.getSource_account_id().trim(),jsonKafkaEventValue.get("accountId").toString().trim(), "Kafka Value - ncb_operations topic - accountId does not match");
        Assert.assertEquals(inputTopTransfer.getSource_user_id().trim(),jsonKafkaEventValue.get("userId").toString().trim(), "Kafka Value - ncb_operations topic - userId does not match");

        Double amount = Double.valueOf(jsonKafkaEventValue.get("amount").toString().trim());
        DecimalFormat format = new DecimalFormat("0.#");

        Assert.assertEquals(inputTopTransfer.getAmount().trim(),format.format(amount), "Kafka Value - ncb_operations topic - amount does not match");
        Assert.assertEquals(inputTopTransfer.getCurrency().trim(),jsonKafkaEventValue.get("currency").toString().trim(), "Kafka Value - ncb_operations topic - currency does not match");

        //value verification


        Assert.assertEquals(bankIdentifier,jsonKafkaEventValue.get("bankIdentifier").toString().trim(), "Kafka Value - ncb_operations topic - bankIdentifier does not match");
        Assert.assertEquals(accountReference,jsonKafkaEventValue.get("accountReference").toString().trim(), "Kafka Value - ncb_operations topic - accountReference does not match");




        System.out.println("Kafka - Value validations OK");
    }

    @Then("new transfer is in db")
    public void new_transfer_is_in_db() {

    }
}
