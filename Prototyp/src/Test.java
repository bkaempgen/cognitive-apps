import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.RequestLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;


public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		// Client
				HttpClientBuilder clientbuilder = HttpClientBuilder.create();
				HttpClient client = clientbuilder.build();
				HttpResponse response;
				
				
				String requestXML1 = "<rdf:RDF xmlns:lapis=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:cast=\"http://141.52.218.34:8080/Prototyp/Cast/Ontology#\"> <rdf:Description rdf:about=\"http://www.example.com/Request_01_Example\"> <rdf:type rdf:resource=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request\"/> <cast:hasInputImage>/data/SFB/Input-Images/T1.nrrd</cast:hasInputImage> <cast:hasOutputImagePath>/data/SFB/Output/cast_result.nrrd</cast:hasOutputImagePath> </rdf:Description> </rdf:RDF>";

				// Request 1
				HttpPost post = new HttpPost("http://141.52.218.34:8080/Prototyp/SFBServletCast#i");
				post.setHeader("Content-Type", "application/xml");

				try {
					post.setEntity(new StringEntity(requestXML1));
					Header[] allheaders = post.getAllHeaders();
					// Debug
					for (Header header : allheaders) {
						System.out.println("Key : " + header.getName() + " ,Value : " + header.getValue());
					}
					
					
					
					client = clientbuilder.build();
					response = client.execute(post);
					RequestLine line = post.getRequestLine();
					HttpParams para= post.getParams();
					System.out.println(para.toString());
					System.out.println(line.toString());
					
					HttpEntity entity = post.getEntity();
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					entity.writeTo(os);
					System.out.println(os.toString());
					
					Header[] allheaders_response = response.getAllHeaders();

					// Debug
					for (Header header : allheaders_response) {
						System.out.println("Key : " + header.getName() + " ,Value : " + header.getValue());
					}

				} catch (IOException e) {
					e.printStackTrace();
				}


	}

}
