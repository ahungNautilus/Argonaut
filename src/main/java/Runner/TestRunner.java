package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/java/features/transfers"},
        glue = "steps",
        publish = false,
        plugin = {"json:target/custom-reportV1/cucumber.json", "html:target/custom-reportV1/TransferSmokeTest.html"}
        )
public class TestRunner extends AbstractTestNGCucumberTests {

}
