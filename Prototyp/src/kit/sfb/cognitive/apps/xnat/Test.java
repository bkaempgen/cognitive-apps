package kit.sfb.cognitive.apps.xnat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class Test {
	
	
	static final String REST_URI                    = "http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/data/xnat";
    static final String REST_URI_Project    = "http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/data/project/TP";
    static final String REST_URI_Subject    = "http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/data/project/TP/subject/sfb125_S00002";
    static final String REST_URI_Experiment = "http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/data/experiment/sfb125_E00004";
    static final String xnat_file                   = "https://xnat.sfb125.de/data/projects/TP/resources/2/files/test_upload.xml";

	public static void main(String[] args) throws URISyntaxException, IOException {
		
		//System.out.println(Uploader.uploadToProject("CAUC", "C:/Users/phiL/Desktop/dummy.xml"));
        		
		//HTTPClient
        HttpClientBuilder clientbuilder = HttpClientBuilder.create();
        HttpClient httpclient = clientbuilder.build();


        HttpGet httpGet = new HttpGet("http://aifb-ls3-vm2.aifb.kit.edu:8080/xnatwrapper/data/project/CAUC");
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
        
		
	}
}