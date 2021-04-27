package Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/java/features"},
        glue = "steps",
        publish = true,
        plugin = {"json:target/custom-reportV1/cucumber.json", "html:target/custom-reportV1/cucumber.html"}
        )
public class TestRunner extends AbstractTestNGCucumberTests {

}
