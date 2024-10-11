package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = {"stepDefinitions"},
monochrome = true, plugin = {"pretty","html:cucumberReports/cucumber.html", 
		"json:target/jsonReports/cucumber-report.json"})
//, tags="@deleteIssue")
public class TestRunner {
	
}
