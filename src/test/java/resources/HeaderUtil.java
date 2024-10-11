package resources;

import java.util.HashMap;
import java.util.Map;

public class HeaderUtil {
	public Map<String, String> addAttachmentInJira() {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("X-Atlassian-Token", "no-check");
		return headers;
	}
}
