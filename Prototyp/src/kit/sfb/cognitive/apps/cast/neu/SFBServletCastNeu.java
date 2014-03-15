package kit.sfb.cognitive.apps.cast.neu;

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

public class SFBServletCastNeu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Data input: file upload or online resource (any or XNAT)
		// Data output: URI for download result via GET

		BufferedReader input = null;
		RequestDataCastNeu requestDataCast = null;

		String salt = null;
		String requestURI = null;
		String inputImage = null;

		long unixTimestamp = System.currentTimeMillis() / 1000L;
		int random = (int) ((Math.random()) * 999999999 + 1);

		String inputImagePathOnDisc = "C:/Users/phiL/Desktop/tmp/" + unixTimestamp + "_" + random + "_";
		String resultImagePathOnDisc = "C:/Users/phiL/Desktop/tmp/";

		String result = null;
		String downloadLink = null;

		try {

			// Get RDF Request Information via: urlencoded (no multipart), file
			// (no multipart), urlencoded (multipart), file (multipart)
			try {
				// RDF-URL-Encoded (no multipart): curl -X POST -v -d "RequestInput=%3Crdf%3ARDF%0D%0A++++xmlns%3Alapis%3D%22http%3A%2F%2Flocalhost%3A8080%2FPrototyp%2FOntology%2FLapis%23%22%0D%0A++++xmlns%3Ardf%3D%22http%3A%2F%2Fwww.w3.org%2F1999%2F02%2F22-rdf-syntax-ns%23%22%0D%0A++++xmlns%3Acast%3D%22http%3A%2F%2Flocalhost%3A8080%2FPrototyp%2FCast%2FOntology%23%22%3E+%0D%0A++%0D%0A%3Crdf%3ADescription+rdf%3Aabout%3D%22http%3A%2F%2Fwww.example.com%2FRequest_01_Example%22%3E%0D%0A++++%3Crdf%3Atype+rdf%3Aresource%3D%22http%3A%2F%2Flocalhost%3A8080%2FPrototyp%2FOntology%2FLapis%23Request%22%2F%3E%0D%0A%09%3Ccast%3AhasInputImage%3Ehttp%3A%2F%2Fwww.w3.org%2FPeople%2FBerners-Lee%2Fcard.rdf%3C%2Fcast%3AhasInputImage%3E%0D%0A%09%3Ccast%3AhasOutputImagePath%3Emethod%3Dfile%3C%2Fcast%3AhasOutputImagePath%3E%0D%0A%3C%2Frdf%3ADescription%3E%0D%0A%3C%2Frdf%3ARDF%3E" http://localhost:8080/Prototyp/SFBServletCastNeu#i
				// RDF-File Upload (no multipart): curl -X POST -v -d "@C:/Users/phiL/Desktop/new_3.xml " -H "Content-Type: application/xml+rdf" -H "Accept: application/xml+rdf" http://localhost:8080/Prototyp/SFBServletCastNeu#i
				// All-File Uploaded (multipart): curl -X POST -v --form RequestInput=@C:/Users/phiL/Desktop/new_3.xml --form file=@xyz.mha http://localhost:8080/Prototyp/SFBServletCastNeu#i
				// RDF-URL-Encoded & File Uploaded (multipart): curl -X POST -v --form "RequestInput= ...<RDF>.. --form file=@xyz.mha http://localhost:8080/Prototyp/SFBServletCastNeu#i

				input = Helper.getRDFInformation(request);

				// Get needed information from RDF-Request for CDL execution
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx RDF Information xxxxxxxxxxxxxxxxxxxxxxxxx");

				requestDataCast = importRequestDataCast(input);

				salt = requestDataCast.getSalt();
				System.out.println("salt: " + salt);
				
				requestURI = requestDataCast.getRequestURI();
				System.out.println("requestURI " + requestURI);
				
				inputImage = requestDataCast.getInputImage();
				System.out.println("inputImage: " + inputImage);


			} catch (Throwable t) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Corrupt input!");
			}

			if (!Helper.saltIsValid("Cast", salt)) {

				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Corrupt input - salt already used recently!");

			} else {
				try {
					// Get file for execution
					// InputImage

					inputImagePathOnDisc = Helper.getFileFromPostRequest(inputImage, inputImagePathOnDisc, request);
					System.out.println(inputImagePathOnDisc);

				} catch (Throwable t) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Corrupt input!");
					throw new Exception();
				}

				try {
					// New execution style cdl with helper class

					resultImagePathOnDisc += salt + "_result" + ".nrrd";

					List<String> parameters = new ArrayList<String>();
					parameters.add(inputImagePathOnDisc);
					parameters.add(resultImagePathOnDisc);

					result = Helper.RunCommandLineTool("Cast", parameters);
					downloadLink = Helper.moveFileToDownloadFolder(resultImagePathOnDisc, "Cast");

					// Response
					response.setContentType("application/xml");
					
					String rdf = "<rdf:RDF xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:cast=\"http://localhost:8080/Prototyp/Cast/Ontology#\">"
							+ "<rdf:Description rdf:about=\""
							+ requestDataCast.getRequestURI()
							+ "\">"
							+ "<rdf:type rdf:resource=\"http://localhost:8080/Prototyp/Ontology/Lapis#Request\"/>"
							+ "<lapis:salt>"
							+ requestDataCast.getSalt()
							+ "</lapis:salt>"
							+ "<cast:hasInputImage>"
							+ requestDataCast.getInputImage()
							+ "</cast:hasInputImage>"
							+ "<lapis:hasResult>"
							+ result
							+ "</lapis:hasResult>"
							+ "<lapis:hasDownload>"
							+ downloadLink
							+ "</lapis:hasDownload>" + "</rdf:Description>" + "</rdf:RDF>";
					
					System.out.println(rdf);
					response.getWriter().print(rdf);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			Helper.deleteFileFromDisc(inputImagePathOnDisc);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/xml");
		PrintWriter writer = response.getWriter();
		writer.println("<rdf:RDF ");
		writer.println("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		writer.println("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		writer.println("xmlns:owl=\"http://www.w3.org/2002/07/owl#\"");
		writer.println("xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\"");
		writer.println("xmlns:sp=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/\">");
		writer.println("<rdf:Description rdf:about=\"http://localhost:8080/Prototyp/SFBServletCast#i\">");
		writer.println("<rdf:type rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/Category:Coginitive_App\"/>");
		writer.println("<rdfs:label xml:lang=\"de\">Cognitive-Cast-Service</rdfs:label>");
		writer.println("<rdfs:label xml:lang=\"en\">Cognitive-Cast-Service</rdfs:label>");
		writer.println("<owl:sameAs	rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/Cast\"/>");
		writer.println("<lapis:hasCreator rdf:resource=\"http://surgipedia.sfb125.de/wiki/User:Philipp_G\"/>");
		writer.println("<lapis:hasAbstract xml:lang=\"de\">Wandelt gegebenes Inputbild in aequivalentes .nrrd-File um.</lapis:hasAbstract>");
		writer.println("<lapis:hasAbstract xml:lang=\"en\">Casts a given input image to an aquivalent .nrrd-File.</lapis:hasAbstract>");
		writer.println("<lapis:hasSourceCode>https://code.google.com/p/cognitive-apps</lapis:hasSourceCode>");
		writer.println("<lapis:hasServiceDescription>http://localhost:8080/Prototyp/Cast/description/index.html</lapis:hasServiceDescription>");
		writer.println("<lapis:hasInputDescription>Input image to be casted and output image path of casted image.</lapis:hasInputDescription>");
		writer.println("<lapis:hasOutputDescription>Casted image.</lapis:hasOutputDescription>");
		writer.println("<lapis:hasExampleRequest>http://localhost:8080/Prototyp/Cast/RDF_Input_Example.xml</lapis:hasExampleRequest>");
		writer.println("<lapis:hasExampleResponse>http://localhost:8080/Prototyp/Cast/RDF_Output_Example.xml</lapis:hasExampleResponse>");
		writer.println("</rdf:Description>");
		writer.println("</rdf:RDF>");
		writer.close();

	}

	private RequestDataCastNeu importRequestDataCast(BufferedReader reader) {
		RDImportHandlerCastNeu handler;

		try {
			handler = new RDImportHandlerCastNeu();
			SAXParser parser = new SAXParser();
			parser.setContentHandler(handler);
			parser.parse(new InputSource(reader));
			// new ByteArrayInputStream(data.getBytes())));
			if (handler.getError() || handler.getInputImage() == null || handler.getSalt() == null)
				return null;
		} catch (Throwable t) {
			return null;
		}
		return new RequestDataCastNeu(handler.getInputImage(), handler.getSalt(), handler.getRequestURI());
	}

}