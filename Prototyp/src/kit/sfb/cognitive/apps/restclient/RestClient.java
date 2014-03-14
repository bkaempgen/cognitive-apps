package kit.sfb.cognitive.apps.restclient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class RestClient {

	//Bsp. URIs
	static final String REST_URI			= "http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/data/xnat";
	static final String REST_URI_Project 	= "http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/data/project/TP";
	static final String REST_URI_Subject 	= "http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/data/project/TP/subject/sfb125_S00002";
	static final String REST_URI_Experiment = "http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/data/experiment/sfb125_E00004";
	static final String xnat_file 			= "https://xnat.sfb125.de/data/projects/TP/resources/2/files/test_upload.xml";

	public static void main(String[] args) {

		//HTTPClient
		HttpClientBuilder clientbuilder = HttpClientBuilder.create();
		HttpClient httpclient = clientbuilder.build();


		HttpGet httpGet = new HttpGet("http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/data/project/TP");
		httpGet.setHeader("Accept" , "application/rdf+xml");
		

		try {
			HttpResponse response = httpclient.execute(httpGet);
			
			BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder total = new StringBuilder();
			String line = null;
			while ((line = r.readLine()) != null) {
			   total.append(line+ '\n');
			}
			System.out.println(total.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		//URL Connection
		
		
//		URL url = null;
//		HttpURLConnection connection = null;
//		BufferedReader rd = null;
//		StringBuilder sb = null;
//		String line = null;
//
//		try {
//			url = new URL(REST_URI_Project);
//
//			// set up out communications stuff
//			connection = null;
//
//			// Set up the initial connection
//			connection = (HttpURLConnection) url.openConnection();
//			connection.setRequestMethod("GET");
//			connection.setDoOutput(true);
//			connection.setReadTimeout(10000);
//			connection.setRequestProperty("Accept", "application/rdf+xml");
//			connection.connect();
//
//			// read the result from the server
//			rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//			sb = new StringBuilder();
//
//			while ((line = rd.readLine()) != null) {
//				sb.append(line + '\n');
//			}
//
//			System.out.println(sb.toString());
//
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		} catch (ProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			// close con and set all to null
//			connection.disconnect();
//			rd = null;
//			sb = null;
//			connection = null;
//		}

	}
}