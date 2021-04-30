package steps.Transfers;

import endpoints.Transfers;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.Reporter;
import utils.RequestMakers;

public class GetTransfersSteps {

    Response response;
    JSONObject body;

    @When("I search for a transfer by id {int}")
    public void iSearchForATransferById(int Id) {
        response = RequestMakers.makeGetRequest(String.format(Transfers.getTransferById,Id));
        Reporter.log("Transfer API response with status code: " + Integer.toString(response.getStatusCode()),true);
        Assert.assertTrue(200 == response.getStatusCode(),"TRA-201");
        body = new JSONObject(response.getBody().asString());
    }

    @Then("the response should have the source_user_id as {string} and the status should be {string}")
    public void theResponseShouldHaveTheSource_user_idAsAndTheStatusShouldBe(String source_user_id, String status) {
        Reporter.log("Response contains the following source_user_id: ".concat(body.get("source_user_id").toString()),true);
        Assert.assertEquals(body.get("source_user_id").toString(),source_user_id);
        Reporter.log("Response contains the following status: ".concat(body.get("status").toString()),true);
        Assert.assertEquals(body.get("status").toString(),status);
    }
}
