package stepDefinitions;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import io.cucumber.java.en.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;

import io.restassured.specification.RequestSpecification;
import pojo.createJiraIssue.Content1;
import pojo.createJiraIssue.Content2;
import pojo.createJiraIssue.CreateIssueJira;
import pojo.createJiraIssue.Description;
import pojo.createJiraIssue.Fields;
import pojo.createJiraIssue.Issuetype;
import pojo.createJiraIssue.Project;
import pojo.createJiraIssue.response.CreateIssueJiraRes;

public class Test {
	
	String baseURI = "https://aishwaryakandavel.atlassian.net/";
	String createIssueResource = "rest/api/3/issue/";
	String attachmentIssueResource = createIssueResource+"{issueId}/attachments";
	String getIssueResource = createIssueResource+"{issueId}";
	String id, key, self = "";
	String currDir = System.getProperty("user.dir");
	protected static Properties prop = new Properties();
	RequestSpecification spec;
	RequestSpecification createOrderReq;
	CreateIssueJiraRes res;
	Response resp;
	
	@Given("I add the payload for {string}")
	public void i_add_the_payload_for(String apiPayload) {
		
		Project proj = new Project();
		proj.setKey("SCRUM");
		
		Content2 content2 = new Content2();
		content2.setText(
				"Given: I have added products to the cart. When: I navigate to the checkout page. "
				+ "Then: Expected: The total price should be calculated correctly. "
				+ "Actual: The total price is higher than the actual price of products in the cart");
		content2.setType("text");
		List<Content2> listOfContent2 = new ArrayList<Content2>();
		listOfContent2.add(content2);
		
		Content1 content1 = new Content1();
		content1.setContent(listOfContent2);
		content1.setType("paragraph");
		
		List<Content1> listOfContent1 = new ArrayList<Content1>();
		listOfContent1.add(content1);
		
		Description desc = new Description();
		desc.setContent(listOfContent1);
		desc.setType("doc");
		desc.setVersion(1);
		
		Issuetype issuetype = new Issuetype();
		issuetype.setName("Bug");
		System.out.println(issuetype.getName());
		
		Fields fields = new Fields();
		fields.setSummary("Incorrect total price calculation on the checkout page");
		
		fields.setDescription(desc);
		fields.setIssuetype(issuetype);
		fields.setProject(proj);
		
		CreateIssueJira createIssueJira = new CreateIssueJira();
		createIssueJira.setFields(fields);
		System.out.println(createIssueJira.getFields().getIssuetype().getName());
		
		try {
			FileInputStream fis = new FileInputStream(
					new File(System.getProperty("user.dir") + "/src/main/resources/runConfig.properties"));
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		spec =	new RequestSpecBuilder().setBaseUri("https://aishwaryakandavel.atlassian.net/")
				.addHeader("Authorization", prop.getProperty("token"))
				.setContentType(ContentType.JSON)
				.build();
		
		createOrderReq=given().log().all().spec(spec).body(createIssueJira);
	}

	@When("I call {string} with {string} http request")
	public void i_call_with_post_http_request(String api, String method) {
		resp = createOrderReq.when().log().all().post(createIssueResource).then().extract().response();
	}

	@Then("verify API call got success with staus code {int}")
	public void verify_api_call_got_success_with_status_code(int statusCode) {
		assertEquals(resp.getStatusCode(), statusCode);
	}
	
	@Then("verify API response schema with JSON scehma {string}")
	public void verify_api_call_got_success_with_status_code(String schema) {
		resp.then().assertThat()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("CreateIssue.json"));
	}

	@Then("capture the API response values for {string}")
	public void capture_the_api_response_values_for(String string) {
		res = resp.then().extract().as(pojo.createJiraIssue.response.CreateIssueJiraRes.class);
		this.id = res.getId();
		this.key = res.getKey();
		this.self = res.getSelf();
	}

}