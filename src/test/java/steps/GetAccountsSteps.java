package steps;

import endpoints.Accounts;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import utils.RequestMakers;

public class GetAccountsSteps {

    JSONObject responseJson;
    Response response;

    @When("I Search an user by the id number {int}")
    public void searchUserById(int accountID) {
        response = RequestMakers.makeGetRequest(String.format(Accounts.getAccountById, accountID));
        responseJson = new JSONObject(response.getBody().asString());
    }

    @Then("I should be able to see the response from the API")
    public void shouldBeAbleToSeeResponse() {
        Assert.assertTrue(200 == response.getStatusCode(), "Server response with incorrect status code");
        System.out.println(response.getBody().asString());

    }
}
