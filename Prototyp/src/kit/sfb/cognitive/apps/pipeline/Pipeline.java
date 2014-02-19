package kit.sfb.cognitive.apps.pipeline;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Pipeline {

	public static void main(String[] args) {

		// Use Case: needed: brain segmentation and normalization of a given t1
		// scan.

		// given input
		String t1Image = "C:/Users/phiL/Desktop/T1.nrrd";
		String atlasImage = "C:/Users/phiL/Desktop/atlasImage.mha";
		String atlasMask = "C:/Users/phiL/Desktop/atlasMask.mha";

		// user respectively client defined output paths
		String castAtlasOutputImageFile = "C:/Users/phiL/Desktop/pipeline/atlasImage.nrrd";
		String castAtlasOutputMaskFile = "C:/Users/phiL/Desktop/pipeline/atlasMask.nrrd";

		String striptsOutputMaskFile = "C:/Users/phiL/Desktop/pipeline/stripped_t1_mask.nrrd";
		String striptsOutputImageFile = "C:/Users/phiL/Desktop/pipeline/stripped_t1_image.nrrd";

		String meanfreeOutputImageFile = "C:/Users/phiL/Desktop/pipeline/meanfree_t1_normalized_image.nrrd";

		// Service URIs
		String castURI = "http://141.52.218.34:8080/Prototyp/SFBServletCast#i";
		String striptsURI = "http://141.52.218.34:8080/Prototyp/SFBServletStripTs";
		String meanfreeURI = "http://141.52.218.34:8080/Prototyp/SFBServletMeanFree";

		// Requests

		// Cast atlasImage.mha to atlasImage.nrrd
		String requestXML1 = "<rdf:RDF xmlns:lapis=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:cast=\"http://141.52.218.34:8080/Prototyp/Cast/Ontology#\">"
				+ "<rdf:Description rdf:about=\"http://www.example.com/Request_01_Example\">"
				+ "<rdf:type rdf:resource=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request\"/>"
				+ "<cast:hasInputImage>"
				+ atlasImage
				+ "</cast:hasInputImage>"
				+ "<cast:hasOutputImagePath>"
				+ castAtlasOutputImageFile
				+ "</cast:hasOutputImagePath>" + "</rdf:Description></rdf:RDF>";

		// Cast atlasMask.mha to atlasMask.nrrd
		String requestXML2 = "<rdf:RDF xmlns:lapis=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:cast=\"http://141.52.218.34:8080/Prototyp/Cast/Ontology#\">"
				+ "<rdf:Description rdf:about=\"http://www.example.com/Request_02_Example\">"
				+ "<rdf:type rdf:resource=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request\"/>"
				+ "<cast:hasInputImage>"
				+ atlasMask
				+ "</cast:hasInputImage>"
				+ "<cast:hasOutputImagePath>"
				+ castAtlasOutputMaskFile
				+ "</cast:hasOutputImagePath>"
				+ "</rdf:Description></rdf:RDF>";

		// brain segmentation of T1 Image using atlasImage.nrrd and
		// atlasMask.nrrd
		String requestXML3 = "<rdf:RDF xmlns:lapis=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:stripts=\"http://141.52.218.34:8080/Prototyp/StripTs/Ontology#\">"
				+ "<rdf:Description rdf:about=\"http://www.example.com/Request_03_Example\">"
				+ "<rdf:type rdf:resource=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request\"/>"
				+ "<stripts:hasInputBrainAtlasImage>"
				+ castAtlasOutputImageFile
				+ "</stripts:hasInputBrainAtlasImage>"
				+ "<stripts:hasInputBrainAtlasMask>"
				+ castAtlasOutputMaskFile
				+ "</stripts:hasInputBrainAtlasMask>"
				+ "<stripts:hasInputImage>"
				+ t1Image
				+ "</stripts:hasInputImage>"
				+ "<stripts:hasOutputMaskPath>"
				+ striptsOutputMaskFile
				+ "</stripts:hasOutputMaskPath>"
				+ "<stripts:hasOutputImagePath>"
				+ striptsOutputImageFile
				+ "</stripts:hasOutputImagePath>" + "</rdf:Description></rdf:RDF>";

		// Normalization of tissue
		String requestXML4 = "<rdf:RDF xmlns:lapis=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:meanfree=\"http://141.52.218.34:8080/Prototyp/MeanFree/Ontology#\">"
				+ "<rdf:Description rdf:about=\"http://www.example.com/Request_04_Example\">"
				+ "<rdf:type rdf:resource=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request\"/>"
				+ "<meanfree:hasInputImage>"
				+ striptsOutputImageFile
				+ "</meanfree:hasInputImage>"
				+ "<meanfree:hasInputMaskImage>"
				+ striptsOutputMaskFile
				+ "</meanfree:hasInputMaskImage>"
				+ "<meanfree:hasOutputImagePath>"
				+ meanfreeOutputImageFile
				+ "</meanfree:hasOutputImagePath>" + "</rdf:Description></rdf:RDF>";

		// Client
		HttpClientBuilder clientbuilder = HttpClientBuilder.create();
		HttpClient client = clientbuilder.build();
		HttpResponse response;

		// Request 1
		HttpPost post = new HttpPost(castURI);
		post.setHeader("Content-Type", "application/xml");

		try {
			post.setEntity(new StringEntity(requestXML1));
			client = clientbuilder.build();
			response = client.execute(post);
			printHeader(response);

		} catch (IOException e) {
			e.printStackTrace();
		}

		// Request 2
		try {
			post.setEntity(new StringEntity(requestXML2));
			client = clientbuilder.build();
			response = client.execute(post);
			printHeader(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Request 3
		post = new HttpPost(striptsURI);
		post.setHeader("Content-Type", "application/xml");
		try {
			post.setEntity(new StringEntity(requestXML3));
			client = clientbuilder.build();
			response = client.execute(post);
			printHeader(response);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Request 3
		post = new HttpPost(meanfreeURI);
		post.setHeader("Content-Type", "application/xml");
		try {
			post.setEntity(new StringEntity(requestXML4));
			client = clientbuilder.build();
			response = client.execute(post);
			printHeader(response);
			System.out.println("++++++++++++++++++++RESULT++++++++++++++++++++++");
			System.out.println(meanfreeOutputImageFile);
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void printHeader(HttpResponse response) {

		Header[] allheaders = response.getAllHeaders();

		// Debug
		for (Header header : allheaders) {
			System.out.println("Key : " + header.getName() + " ,Value : " + header.getValue());
		}
	}

}
