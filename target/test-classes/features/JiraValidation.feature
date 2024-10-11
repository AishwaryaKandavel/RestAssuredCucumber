@JiraValidation
Feature: Validate E2E Jira Issue creation, modification and deletion

@createIssue
Scenario: Validate Jira Issue Creation
	Given I add the payload for "CreateIssueInJiraAPI"
	When I call "createIssueResource" with "POST" http request
	Then I verify API call got success with staus code 201
	Then I verify API response schema with JSON scehma "CreateIssue"
	Then I capture the API response values for "id;key;self"

@addAttachment
Scenario: Validate Jira Add Attachment
	Given I set headers for "addAttachmentInJira"
	Given I set "issueId" path parameter with "id"
	Given I set file value for "addAttachmentFile"
	Given I build the request
	When I call "attachmentIssueResource" with "POST" http request
	Then I verify API call got success with staus code 200
	
@getIssue
Scenario: Validate JIRA Issue Fields
	Given I set "issueId" path parameter with "id"
	Given I build the request
	When I call "getIssueResource" with "GET" http request
	Then I verify API call got success with staus code 200
	Then I verify "id" value
	Then I verify "self" value
	Then I verify "key" value
	
@updateAssignee
Scenario: Validate Jira Issue Updation
	Given I add the payload for "PutAssigneeInJiraIssue"
	Given I set "issueIdOrKey" path parameter with "id"
	Given I build the request
	When I call "putIssueResource" with "PUT" http request
	Then I verify API call got success with staus code 204

@deleteIssue
Scenario: Delete JIRA Issue
	Given I set "issueId" path parameter with "id"
	Given I build the request
	When I call "deleteIssueResource" with "DELETE" http request
	Then I verify API call got success with staus code 204