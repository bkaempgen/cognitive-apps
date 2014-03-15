package kit.sfb.cognitive.apps.meanfree.neu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.sfb.cognitive.apps.helper.Helper;

import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;

public class SFBServletMeanFreeNeu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/xml");
		PrintWriter writer = response.getWriter();
		writer.println("<rdf:RDF ");
		writer.println("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		writer.println("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		writer.println("xmlns:owl=\"http://www.w3.org/2002/07/owl#\"");
		writer.println("xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\"");
		writer.println("xmlns:sp=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/\">");
		writer.println("<rdf:Description rdf:about=\"http://localhost:8080/Prototyp/SFBServletMeanFree#i\">");
		writer.println("<rdf:type rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/Category:Coginitive_App\"/>");
		writer.println("<rdfs:label xml:lang=\"de\">Cognitive-MeanFree-Service</rdfs:label>");
		writer.println("<rdfs:label xml:lang=\"en\">Cognitive-MeanFree-Service</rdfs:label>");
		writer.println("<owl:sameAs	rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/MeanFree\"/>");
		writer.println("<lapis:hasCreator rdf:resource=\"http://surgipedia.sfb125.de/wiki/User:Philipp_G\"/>");
		writer.println("<lapis:hasAbstract xml:lang=\"de\">Normalisierung d. Gewebefaerbung.</lapis:hasAbstract>");
		writer.println("<lapis:hasAbstract xml:lang=\"en\">Normalization of tissue colour.</lapis:hasAbstract>");
		writer.println("<lapis:hasSourceCode>https://code.google.com/p/cognitive-apps</lapis:hasSourceCode>");
		writer.println("<lapis:hasServiceDescription>http://localhost:8080/Prototyp/MeanFree/description/index.html</lapis:hasServiceDescription>");
		writer.println("<lapis:hasInputDescription>T1 input image of patient and mask image (optional).</lapis:hasInputDescription>");
		writer.println("<lapis:hasOutputDescription>Normalized t1 image regarding tissue colour.</lapis:hasOutputDescription>");
		writer.println("<lapis:hasExampleRequest>http://localhost:8080/Prototyp/MeanFree/RDF_Input_Example.xml</lapis:hasExampleRequest>");
		writer.println("<lapis:hasExampleResponse>http://localhost:8080/Prototyp/MeanFree/RDF_Output_Example.xml</lapis:hasExampleResponse>");
		writer.println("</rdf:Description>");
		writer.println("</rdf:RDF>");
		writer.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BufferedReader input = null;
		RequestDataMeanFreeNeu requestDataMeanFree = null;

		String salt = null;
		String requestURI = null;
		String inputImage = null;
		String inputMaskImage = null;

		long unixTimestamp = System.currentTimeMillis() / 1000L;
		int random = (int) ((Math.random()) * 999999999 + 1);

		String inputImagePathOnDisc = "C:/Users/phiL/Desktop/tmp/" + unixTimestamp + "_" + random + "_";
		String inputMaskImagePathOnDisc = "C:/Users/phiL/Desktop/tmp/" + unixTimestamp + "_" + random + "_";

		String resultPathOnDisc = "C:/Users/phiL/Desktop/tmp/";

		String result = null;
		String downloadLink = null;

		try {

			try {

				input = Helper.getRDFInformation(request);

				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx RDF Information xxxxxxxxxxxxxxxxxxxxxxxxx");
				requestDataMeanFree = importRequestDataMeanFree(input);

				salt = requestDataMeanFree.getSalt();
				System.out.println("salt: " + salt);

				requestURI = requestDataMeanFree.getRequestURI();
				System.out.println("requestURI " + requestURI);

				inputImage = requestDataMeanFree.getInputImage();
				System.out.println("inputImage: " + inputImage);

				inputMaskImage = requestDataMeanFree.getInputMaskImage();
				System.out.println("inputMaskImage: " + inputMaskImage);
				

			} catch (Throwable t) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Corrupt input!");
			}

			if (!Helper.saltIsValid("MeanFree", salt)) {

				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Corrupt input - salt already used recently!");

			} else {

				try {

					inputImagePathOnDisc = Helper.getFileFromPostRequest(inputImage, inputImagePathOnDisc, request);
					System.out.println(inputImagePathOnDisc);

					if (inputMaskImage.equalsIgnoreCase("none")){
						inputMaskImagePathOnDisc = "none";
					}else{
						inputMaskImagePathOnDisc = Helper.getFileFromPostRequest(inputMaskImage, inputMaskImagePathOnDisc, request);
						System.out.println(inputMaskImagePathOnDisc);
					}

				} catch (Throwable t) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Corrupt input!");
					throw new Exception();
				}

				try {

					resultPathOnDisc += salt + "_result" + ".nrrd";

					List<String> parameters = new ArrayList<String>();
					parameters.add(inputImagePathOnDisc);
					parameters.add(inputMaskImagePathOnDisc);
					parameters.add(resultPathOnDisc);

					result = Helper.RunCommandLineTool("MeanFree", parameters);
					downloadLink = Helper.moveFileToDownloadFolder(resultPathOnDisc, "MeanFree");

					response.setContentType("application/xml");

					String rdf = "<rdf:RDF xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:meanfree=\"http://localhost:8080/Prototyp/MeanFree/Ontology#\">"
							+ "<rdf:Description rdf:about=\""
							+ requestDataMeanFree.getRequestURI()
							+ "\">"
							+ "<rdf:type rdf:resource=\"http://localhost:8080/Prototyp/Ontology/Lapis#Request\"/>"
							+ "<lapis:salt>"
							+ requestDataMeanFree.getSalt()
							+ "</lapis:salt>"
							+ "<meanfree:hasInputImage>"
							+ requestDataMeanFree.getInputImage()
							+ "</meanfree:hasInputImage>"
							+ "<meanfree:hasInputMaskImage>"
							+ requestDataMeanFree.getInputMaskImage()
							+ "</meanfree:hasInputMaskImage>"
							+ "<lapis:hasResult>"
							+ result
							+ "</lapis:hasResult>"
							+ "<lapis:hasDownload>"
							+ downloadLink
							+ "</lapis:hasDownload>"
							+ "</rdf:Description></rdf:RDF>";

					System.out.println(rdf);
					response.getWriter().print(rdf);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			Helper.deleteFileFromDisc(inputImagePathOnDisc);
			Helper.deleteFileFromDisc(inputMaskImagePathOnDisc);
		}
	}

	private RequestDataMeanFreeNeu importRequestDataMeanFree(BufferedReader reader) {
		RDImportHandlerMeanFreeNeu handler;

		try {
			handler = new RDImportHandlerMeanFreeNeu();
			SAXParser parser = new SAXParser();
			parser.setContentHandler(handler);
			parser.parse(new InputSource(reader));
			// new ByteArrayInputStream(data.getBytes())));
			if (handler.getError() || handler.getInputImage() == null || handler.getInputMaskImage() == null || handler.getSalt() == null)
				return null;
		} catch (Throwable t) {
			return null;
		}
		return new RequestDataMeanFreeNeu(handler.getInputImage(), handler.getInputMaskImage(), handler.getSalt(), handler.getRequestURI());
	}

}