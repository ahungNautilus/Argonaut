package steps.transfers;

import checkpoints.TransfersResponseAsserts;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;
import utils.CreateBodyContent;
import utils.RequestMakers;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;
import utils.KafkaTestConsumer;

public class common {

    JSONObject inputBody;
    JSONObject jsonResponse;
    Response response;


    @Given("a payload:")
    public void a_payload(io.cucumber.datatable.DataTable dataTable) {

        KafkaTestConsumer kafkaConsumer = new KafkaTestConsumer();

        kafkaConsumer.readKafkaMessagess();





        ArrayList<String> keys = new ArrayList(), values = new ArrayList();
        keys.add("source_account_id");
        keys.add("source_user_id");
        keys.add("amount");
        keys.add("currency");
        keys.add("comments");
        keys.add("transfer_type");
        values.add(dataTable.cell(0,1));
        values.add(dataTable.cell(1,1));
        values.add(dataTable.cell(2,1));
        values.add(dataTable.cell(3,1));
        values.add(dataTable.cell(4,1));
        values.add(dataTable.cell(5,1));

        inputBody = CreateBodyContent.setBodyPostAccount(keys, values);

    }
    @When("perform a POST to transfers")
    public void perform_a_post_to_transfers() {
        String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJodHRwczovL2x5bmsudXMvdGllciI6IlZVMiIsImlzcyI6Imh0dHBzOi8vZGV2LXlpdTg3a3BjLnVzLmF1dGgwLmNvbS8iLCJzdWIiOiJhdXRoMHw2MWE1NTBjZGE3MDc2NTAwNmExNWZhODkiLCJhdWQiOlsiaHR0cDovL2FwcGNvbnN1bWVyYXV0aGJmZiIsImh0dHBzOi8vZGV2LXlpdTg3a3BjLnVzLmF1dGgwLmNvbS91c2VyaW5mbyJdLCJpYXQiOjE2MjMzNTg2NzEsImV4cCI6MTYyNDAzMzQ0MSwiYXpwIjoidGRpNHoxb2ZSZ0xVRlhUVUtUOVJsYTdhcEZsczNyMXYiLCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIG9mZmxpbmVfYWNjZXNzIiwiZ3R5IjoicGFzc3dvcmQiLCJqdGkiOiIzM2Y4YmFjYy02YzU5LTRlNGUtODcyMy1jYzk5ZWIwMTlkZjAifQ.tizAQTsIA5sud3QquJz7oKokTHvvD1jC_gQGqFs9ugc";
        response = RequestMakers.makePostRequestWithToken("http://nonprod-aks.api-nautilus.net:30616/transfers/"
                , inputBody.toString(),jwt);
        jsonResponse = new JSONObject(response.getBody().asString());
        System.out.println(jsonResponse);

    }
    @Then("the status code is {string}")
    public void the_status_code_is(String statusCode) {
        TransfersResponseAsserts.validateStatusCode(parseInt(statusCode),response.getStatusCode());
    }
    @Then("new transfer is in db")
    public void new_transfer_is_in_db() {
    }
}
