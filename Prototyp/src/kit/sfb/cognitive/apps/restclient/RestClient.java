package kit.sfb.cognitive.apps.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class RestClient {
	static final String REST_URI = "http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/id/project/Example";
	static final String REST_URI2 ="http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/id/xnat";
	static final String REST_URI3 ="http://www.commontk.org/docs/html/ctkCmdLineModule.xsd";
	

	public static void main(String[] args) {

		URL url = null;
		HttpURLConnection connection = null;
	    BufferedReader rd  = null;
	    StringBuilder sb = null;
	    String line = null;

		try {
			url = new URL(REST_URI);
			
			//set up out communications stuff
	        connection = null;
			
			//Set up the initial connection
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
			connection.setRequestProperty("Accept", "application/rdf+xml");
			connection.connect();
			
			
			//read the result from the server
			rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        sb = new StringBuilder();
	        
	        while ((line = rd.readLine()) != null)
	          {
	              sb.append(line + '\n');
	          }
	        
	          System.out.println(sb.toString());

		} catch (MalformedURLException e) {
	          e.printStackTrace();
	      } catch (ProtocolException e) {
	          e.printStackTrace();
	      } catch (IOException e) {
	          e.printStackTrace();
	      }
	      finally
	      {
	          //close the connection, set all objects to null
	          connection.disconnect();
	          rd = null;
	          sb = null;
	          connection = null;
	      }

	}
}