package pojo.createJiraIssue;

import java.util.List;

public class Description {
	private List<Content1> content;
	private String type;
	private int version;
	public List<Content1> getContent() {
		return content;
	}
	public void setContent(List<Content1> content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int i) {
		this.version = i;
	}	
}
