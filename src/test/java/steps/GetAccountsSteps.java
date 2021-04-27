package steps;

import endpoints.Accounts;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import utils.RequestMakers;

import java.lang.reflect.Array;

public class GetAccountsSteps {

    JSONObject responseJson;
    Response response;


    @When("I Search an user by the id number {int}")
    public void searchUserById(int accountID) {
        response = RequestMakers.makeGetRequest(String.format(Accounts.getAccountById, accountID));
        responseJson = new JSONObject(response.getBody().asString());
        System.out.println(responseJson);
    }

    @Then("I should be able to see the response from the API")
    public void shouldBeAbleToSeeResponse() {
        Assert.assertTrue(200 == response.getStatusCode(), "Server response with incorrect status code");
        System.out.println(response.getBody().asString());

    }

    @Then("I should be able to see the status code {int}")
    public void iShouldBeAbleToSeeTheStatusCode(int statusCode) {
        Assert.assertTrue(statusCode == response.getStatusCode(),"Server response with incorrect status code");
    }

    @And("the error description should be {string}")
    public void theErrorDescriptionShouldBe(String error_message) {
        Assert.assertEquals(error_message,responseJson.get("error_description"), "Server response with incorrect error message");
    }

    @And("the error code should be {string}")
    public void theErrorCodeShouldBe(String error_code) {
        Assert.assertEquals(error_code,responseJson.get("error_code"), "Server response with incorrect error code");
    }
}
