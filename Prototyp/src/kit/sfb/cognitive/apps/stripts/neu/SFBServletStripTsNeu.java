package kit.sfb.cognitive.apps.stripts.neu;

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

/**
 * Servlet implementation class SFBServletStripTs
 */
public class SFBServletStripTsNeu extends HttpServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/xml");
		PrintWriter writer = response.getWriter();
		writer.println("<rdf:RDF ");
		writer.println("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		writer.println("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		writer.println("xmlns:owl=\"http://www.w3.org/2002/07/owl#\"");
		writer.println("xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\"");
		writer.println("xmlns:sp=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/\">");
		writer.println("<rdf:Description rdf:about=\"http://localhost:8080/Prototyp/SFBServletStripTs#i\">");
		writer.println("<rdf:type rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/Category:Coginitive_App\"/>");
		writer.println("<rdfs:label xml:lang=\"de\">Cognitive-StripTs-Service</rdfs:label>");
		writer.println("<rdfs:label xml:lang=\"en\">Cognitive-StripTs-Service</rdfs:label>");
		writer.println("<owl:sameAs	rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/StripTs\"/>");
		writer.println("<lapis:hasCreator rdf:resource=\"http://surgipedia.sfb125.de/wiki/User:Philipp_G\"/>");
		writer.println("<lapis:hasAbstract xml:lang=\"de\">Segmentierung des Gehirns einer Kopfaufnahme.</lapis:hasAbstract>");
		writer.println("<lapis:hasAbstract xml:lang=\"en\">Segmentation of brain in a head scan.</lapis:hasAbstract>");
		writer.println("<lapis:hasSourceCode>https://code.google.com/p/cognitive-apps</lapis:hasSourceCode>");
		writer.println("<lapis:hasServiceDescription>http://localhost:8080/Prototyp/StripTs/description/index.html</lapis:hasServiceDescription>");
		writer.println("<lapis:hasInputDescription>T1 image of patient, atlasImage and atlasMask.</lapis:hasInputDescription>");
		writer.println("<lapis:hasOutputDescription>Stripped image and mask of T1 patient.</lapis:hasOutputDescription>");
		writer.println("<lapis:hasExampleRequest>http://localhost:8080/Prototyp/StripTs/RDF_Input_Example.xml</lapis:hasExampleRequest>");
		writer.println("<lapis:hasExampleResponse>http://localhost:8080/Prototyp/StripTs/RDF_Output_Example.xml</lapis:hasExampleResponse>");
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
		RequestDataStripTsNeu requestDataStripTs = null;

		String salt = null;
		String requestURI = null;
		String inputBrainAtlasImage = null;
		String inputBrainAtlasMask = null;
		String inputImage = null;
		
		long unixTimestamp = System.currentTimeMillis() / 1000L;
		int random = (int) ((Math.random()) * 999999999 + 1);
		
		String inputBrainAtlasImagePathOnDisc = "C:/Users/phiL/Desktop/tmp/" + unixTimestamp + "_" + random + "_";
		String inputBrainAtlasMaskPathOnDisc = "C:/Users/phiL/Desktop/tmp/" + unixTimestamp + "_" + random + "_";
		String inputImagePathOnDisc = "C:/Users/phiL/Desktop/tmp/" + unixTimestamp + "_" + random + "_";
		
		String resultImagePathOnDisc = "C:/Users/phiL/Desktop/tmp/";
		String resultMaskPathOnDisc = "C:/Users/phiL/Desktop/tmp/";
		
		String result = null;
		String downloadLinkImage = null;
		String downloadLinkMask = null;
		
		try {
		
			try {
				
				input = Helper.getRDFInformation(request);
				
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx RDF Information xxxxxxxxxxxxxxxxxxxxxxxxx");
				requestDataStripTs = importRequestDataStripTs(input);
				
				salt = requestDataStripTs.getSalt();
				System.out.println("salt: " + salt);
				
				requestURI = requestDataStripTs.getRequestURI();
				System.out.println("requestURI " + requestURI);
				
				inputBrainAtlasImage = requestDataStripTs.getInputBrainAtlasImage();
				System.out.println("inputBrainAtlasImage: " + inputBrainAtlasImage);
				
				inputBrainAtlasMask = requestDataStripTs.getInputBrainAtlasMask();
				System.out.println("inputBrainAtlasMask: " + inputBrainAtlasMask);
				
				inputImage = requestDataStripTs.getInputImage();
				System.out.println("inputImage: " + inputImage);
				
			} catch (Throwable t) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Corrupt input!");
			}
			
			if (!Helper.saltIsValid("StripTs", salt)) {

				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Corrupt input - salt already used recently!");

			} else {
				
				try {

					inputBrainAtlasImagePathOnDisc = Helper.getFileFromPostRequest(inputBrainAtlasImage, inputBrainAtlasImagePathOnDisc, request);
					System.out.println(inputBrainAtlasImagePathOnDisc);
					
					inputBrainAtlasMaskPathOnDisc = Helper.getFileFromPostRequest(inputBrainAtlasMask, inputBrainAtlasMaskPathOnDisc, request);
					System.out.println(inputBrainAtlasMaskPathOnDisc);
					
					inputImagePathOnDisc = Helper.getFileFromPostRequest(inputImage, inputImagePathOnDisc, request);
					System.out.println(inputImagePathOnDisc);
					
				} catch (Throwable t) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND, "Corrupt input!");
					throw new Exception();
				}
					
				try {	
					
					resultImagePathOnDisc 	+= salt + "_result_image" + ".nrrd";
					resultMaskPathOnDisc	+= salt + "_result_mask" + ".nrrd";
					
					List<String> parameters = new ArrayList<String>();
					parameters.add(inputBrainAtlasImagePathOnDisc);
					parameters.add(inputBrainAtlasMaskPathOnDisc);
					parameters.add(inputImagePathOnDisc);
					parameters.add(resultMaskPathOnDisc);
					parameters.add(resultImagePathOnDisc);
					
					result = Helper.RunCommandLineTool("StripTs", parameters);
					downloadLinkImage 	= Helper.moveFileToDownloadFolder(resultMaskPathOnDisc, "StripTs");
					downloadLinkMask 	= Helper.moveFileToDownloadFolder(resultImagePathOnDisc, "StripTs");
					
					response.setContentType("application/xml");

					String rdf = "<rdf:RDF xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"  xmlns:stripts=\"http://localhost:8080/Prototyp/StripTs/Ontology#\">"
					+ "<rdf:Description rdf:about=\""
					+ requestDataStripTs.getRequestURI()
					+ "\">"
					+ "<rdf:type rdf:resource=\"http://localhost:8080/Prototyp/Ontology/Lapis#Request\"/>"
					+ "<lapis:salt>"
					+ requestDataStripTs.getSalt()
					+ "</lapis:salt>"
					+ "<stripts:hasInputBrainAtlasImage>"
					+ requestDataStripTs.getInputBrainAtlasImage()
					+ "</stripts:hasInputBrainAtlasImage>"
					+ "<stripts:hasInputBrainAtlasMask>"
					+ requestDataStripTs.getInputBrainAtlasMask()
					+ "</stripts:hasInputBrainAtlasMask>"
					+ "<stripts:hasInputImage>"
					+ requestDataStripTs.getInputImage()
					+ "</stripts:hasInputImage>"
					+ "<lapis:hasResult>"
					+ result
					+ "</lapis:hasResult>"
					+ "<lapis:hasDownload>"
					+ downloadLinkMask
					+ "</lapis:hasDownload>"
					+ "<lapis:hasDownload>"
					+ downloadLinkImage
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
		}finally{
			Helper.deleteFileFromDisc(inputBrainAtlasImagePathOnDisc);
			Helper.deleteFileFromDisc(inputBrainAtlasMaskPathOnDisc);
			Helper.deleteFileFromDisc(inputImagePathOnDisc);
		}

	}

	private RequestDataStripTsNeu importRequestDataStripTs(BufferedReader reader) {
		RDImportHandlerStripTsNeu handler;

		try {
			handler = new RDImportHandlerStripTsNeu();
			SAXParser parser = new SAXParser();
			parser.setContentHandler(handler);
			parser.parse(new InputSource(reader));
			// new ByteArrayInputStream(data.getBytes())));
			if (handler.getError() || handler.getInputBrainAtlasImage() == null || handler.getInputBrainAtlasMask() == null
					|| handler.getInputImage() == null || handler.getSalt() == null)
				return null;
		} catch (Throwable t) {
			return null;
		}
		return new RequestDataStripTsNeu(handler.getInputBrainAtlasImage(), handler.getInputBrainAtlasMask(), handler.getInputImage(),
				handler.getSalt(), handler.getRequestURI());
	}

}
