package resources;

import java.util.ArrayList;
import java.util.List;

import pojo.createJiraIssue.Content1;
import pojo.createJiraIssue.Content2;
import pojo.createJiraIssue.CreateIssueJira;
import pojo.createJiraIssue.Description;
import pojo.createJiraIssue.Fields;
import pojo.createJiraIssue.Issuetype;
import pojo.createJiraIssue.Project;
import pojo.putAssigneeJiraIssue.PutAssignee;

public class TestDataBuilder {
	public CreateIssueJira CreateIssueInJiraAPI() {
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
		
		Fields fields = new Fields();
		fields.setSummary("Incorrect total price calculation on the checkout page");
		
		fields.setDescription(desc);
		fields.setIssuetype(issuetype);
		fields.setProject(proj);
		
		CreateIssueJira createIssueJira = new CreateIssueJira();
		createIssueJira.setFields(fields);
		
		return createIssueJira;
	}
	public PutAssignee PutAssigneeInJiraIssue() {
		PutAssignee putAssignee = new PutAssignee();
		putAssignee.setAccountId(UtilityFunction.getProperty("accountId"));
		return putAssignee;
	}
}
