package stepDefinitions;

import io.cucumber.java.Before;

public class Hooks {
	@Before("@deleteIssue")
	public void beforeSchenario() throws Exception {
		Test test = new Test();
		if(Test.id==null) {
			test.i_add_the_payload_for("CreateIssueInJiraAPI");
			test.i_call_with_post_http_request("createIssueResource", "POST");
			test.i_verify_api_call_got_success_with_status_code(201);
			test.i_verify_api_response_schema_with_json_schema("CreateIssue");
			test.i_capture_the_api_response_values_for("id;key;self");
		}
	}
}
