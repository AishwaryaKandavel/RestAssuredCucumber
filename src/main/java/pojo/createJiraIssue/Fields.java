package pojo.createJiraIssue;

public class Fields {
	private Project project;
	private String summary;
	private Description description;
	private Issuetype issuetype;
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Description getDescription() {
		return description;
	}
	public void setDescription(Description description) {
		this.description = description;
	}
	public Issuetype getIssuetype() {
		return issuetype;
	}
	public void setIssuetype(Issuetype issuetype) {
		this.issuetype = issuetype;
	}
}
