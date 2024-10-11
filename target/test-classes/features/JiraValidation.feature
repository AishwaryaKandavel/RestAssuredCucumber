Feature: Validate E2E Jira Issue creation, modification and deletion

Scenario: Validate Jira Issue Creation
	Given I add the payload for "CreateIssueInJiraAPI"
	When I call "CreateIssueInJiraAPI" with "POST" http request
	Then verify API call got success with staus code 201
	Then verify API response schema with JSON scehma "createJiraIssueSchema"
	Then capture the API response values for "id;key;self"
	
Scenario: Validate Jira Add Attachment
	Given I add the payload for "CreateIssueInJiraAPI"
	When I call "" with "POST" http request