package kit.sfb.cognitive.apps.xnat;

import java.io.File;
import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;

public class Uploader {

	final static String base_uploader_uri = "http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatuploader/id/";

	// Helper method for doing post
	private static HttpResponse doPost(String uri, String filename) {

		HttpClientBuilder clientbuilder = HttpClientBuilder.create();
		HttpClient httpclient = clientbuilder.build();

		HttpPost httpPost = new HttpPost(uri);
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		File file = new File(filename);
		FileBody filebody = new FileBody(file);

		builder.addPart("file", filebody);
		HttpEntity entity = builder.build();

		HttpResponse response = null;

		try {
			httpPost.setEntity(entity);
			response = httpclient.execute(httpPost);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;
	}

	private static String getLocationFromHeader(HttpResponse response) {

		Header[] allheaders = response.getAllHeaders();

		// Debug
		for (Header header : allheaders) {
			System.out.println("Key : " + header.getName() + " ,Value : " + header.getValue());
		}
		String location = response.getFirstHeader("Location").getValue();

		System.out.println("+++++++++++++++++++++++++Location of File in XNAT+++++++++++++++++++++++++++++");
		System.out.println(location);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		return location;
	}

	// Upload File to Project and return Location URI
	public static String uploadToProject(String project_id, String filename) {

		String uri = base_uploader_uri + "project/" + project_id;
		HttpResponse response = doPost(uri, filename);

		return getLocationFromHeader(response);

	}

	// Upload File to Subject in a Project and return Location URI
	public static String uploadToSubject(String project_id, String subject_id, String filename) {

		String uri = base_uploader_uri + "project/" + project_id + "/subject/" + subject_id;
		HttpResponse response = doPost(uri, filename);

		return getLocationFromHeader(response);

	}

	// Upload File to Experiment and return Location URI
	public static String uploadToExperiment(String experiment_id, String filename) {

		String uri = base_uploader_uri + "experiment/" + experiment_id;
		HttpResponse response = doPost(uri, filename);

		return getLocationFromHeader(response);

	}

}
