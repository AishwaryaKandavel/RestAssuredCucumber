package stepDefinitions;

import java.util.Map;

import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import io.restassured.specification.RequestSpecification;
import pojo.createJiraIssue.response.CreateIssueJiraRes;
import resources.APIResources;
import resources.HeaderUtil;
import resources.TestDataBuilder;
import resources.UtilityFunction;

public class Test extends UtilityFunction{
	
	static String id, key, self;
	String currDir = System.getProperty("user.dir");
	RequestSpecification spec;
	CreateIssueJiraRes res;
	Response resp;
	TestDataBuilder testDataBuilder = new TestDataBuilder();
	HeaderUtil headerUtil = new HeaderUtil();
	RequestSpecBuilder resSpecBuilder;
	
	public String getVariable(String variable) throws Exception {
		return (String) Test.class.getDeclaredField(variable).get(new Test());
	}
	
	public void setVariable(String variable, String value) throws Exception {
		Test.class.getDeclaredField(variable).set(new Test(), value);
	}
	
	@Given("I add the payload for {string}")
	public void i_add_the_payload_for(String apiPayload) throws Exception {
		
		spec =	requestSpecification().setContentType(ContentType.JSON).build();
		spec = given().log().all().spec(spec)
				.body(getMethod(testDataBuilder, apiPayload).invoke(testDataBuilder));
	}

	@When("I call {string} with {string} http request")
	public void i_call_with_post_http_request(String api, String method) throws Exception {
		String val = APIResources.valueOf(api).getResource();
		switch (method) {
		case "POST":
			resp = spec.when().log().all().post(val);
			break;
		case "GET":
			resp = spec.when().log().all().get(val);
			break;
		case "PUT":
			resp = spec.when().log().all().put(val);
			break;
		case "DELETE":
			resp = spec.when().log().all().delete(val);
			break;
		default:
			break;
		}
	}

	@Then("I verify API call got success with staus code {int}")
	public void i_verify_api_call_got_success_with_status_code(int statusCode) {
		assertEquals(resp.getStatusCode(), statusCode);
	}
	
	@Then("I verify API response schema with JSON scehma {string}")
	public void i_verify_api_response_schema_with_json_schema(String schema) {
		resp.then().assertThat()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schema+".json"));
	}

	@Then("I capture the API response values for {string}")
	public void i_capture_the_api_response_values_for(String values) throws Exception {
		res = resp.then().extract().as(pojo.createJiraIssue.response.CreateIssueJiraRes.class);
		String[] vals = values.split(";");
		for (String val : vals) {
			String value = (String) getMethod(res, 
					"get"+val.substring(0,1).toUpperCase()+val.substring(1)).invoke(res);
			setVariable(val, value);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Given("I set headers for {string}")
	public void i_set_headers_for(String method) throws Exception {
		resSpecBuilder = resSpecBuilder!=null?resSpecBuilder:requestSpecification();
		resSpecBuilder = requestSpecification()
		.addHeaders((Map<String, String>) getMethod(headerUtil, method).invoke(headerUtil));
	}
	
	@Given("I set {string} path parameter with {string}")
	public void i_set_path_parameter(String param, String value) throws Exception {
		resSpecBuilder = resSpecBuilder!=null?resSpecBuilder:requestSpecification();
		String val = getVariable(value);
		resSpecBuilder = resSpecBuilder.addPathParam(param, val);
	}
	
	@Given("I set file value for {string}")
	public void i_set_file_value_for(String filePropertyName) throws Exception {
		resSpecBuilder = resSpecBuilder!=null?resSpecBuilder:requestSpecification();
		resSpecBuilder = addFileToForm(resSpecBuilder, filePropertyName);
	}
	
	@Then("I verify {string} value")
	public void i_verify_value(String value) throws Exception {
		String val = getVariable(value);
		assertEquals(getValueFromResponse(resp, value), val);
	}
	@Given("I set content-type as {string}")
	public void i_set_content_type_as(String contentType) throws Exception {
		resSpecBuilder = resSpecBuilder!=null?resSpecBuilder:requestSpecification();
		resSpecBuilder.setContentType(ContentType.valueOf(contentType));
	}
	
	@Given("I build the request")
	public void i_build_the_request() throws Exception {
		resSpecBuilder = resSpecBuilder!=null?resSpecBuilder:requestSpecification();
		spec = resSpecBuilder.build();
		spec = given().log().all().spec(spec);
	}
}