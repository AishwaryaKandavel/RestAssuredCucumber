package resources;

public enum APIResources {
	createIssueResource("rest/api/3/issue/"),
	attachmentIssueResource("rest/api/3/issue/{issueId}/attachments"),
	getIssueResource("rest/api/3/issue/{issueId}"),
	deleteIssueResource("rest/api/3/issue/{issueId}"),
	putIssueResource("/rest/api/3/issue/{issueIdOrKey}/assignee");
	
	String resource;
	
	APIResources(String resouce){
		this.resource = resouce;
	}
	
	public String getResource() {
		return this.resource;
	}
}
