package checkpoints;

import org.testng.Assert;

public class TransfersResponseAsserts {
    public static void validateStatusCode(Integer statusCode, Integer responseCode) {
        Assert.assertEquals(statusCode, responseCode,"Server response with incorrect error message, expected "
                + statusCode+" but was " +  responseCode);
        System.out.println("Response code validation OK: "
                + responseCode + " and was expected " + statusCode);
    }
}
