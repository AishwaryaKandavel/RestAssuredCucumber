package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UtilityFunction {
	protected static Properties prop = new Properties();
	static String currDir = System.getProperty("user.dir");
	
	RequestSpecBuilder spec;
	
	public static String getProperty(String propertyName) {
		if(prop.isEmpty()) {
			try {
				FileInputStream fis = new FileInputStream(
						new File(currDir + "/src/main/resources/runConfig.properties"));
				prop.load(fis);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return prop.getProperty(propertyName);
	}
	
	public RequestSpecBuilder requestSpecification() throws IOException {
		if(spec==null) {
			
			
			File logFile = new File("logs/log"+System.currentTimeMillis()+".txt");
	
			if (!logFile.exists()) {
			    logFile.getParentFile().mkdirs();
			    logFile.createNewFile(); 
			}
			PrintStream ps = new PrintStream(new FileOutputStream(logFile, true), true, "UTF-8"); 
			
			spec = new RequestSpecBuilder().setBaseUri(getProperty("baseURI"))
					.addFilter(RequestLoggingFilter.logRequestTo(ps))
					.addFilter(ResponseLoggingFilter.logResponseTo(ps))
			.addHeader("Authorization", getProperty("token"));
		}
		return spec;
	}
	
	public RequestSpecBuilder addFileToForm(RequestSpecBuilder rsb, String fileName) {
		return rsb.addMultiPart("file", new File(currDir+getProperty(fileName)));
	}
	
	public String getValueFromResponse(Response resp, String value) {
		JsonPath jp = new JsonPath(resp.asString());
		return jp.getString(value);
	}
	
	public Method getMethod(Object obj, String method) throws Exception {
		return obj.getClass().getMethod(method);
	}
}
