package kit.sfb.cognitive.apps.stripts;

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
public class SFBServletStripTs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String host = Helper.getProperties("host");
	

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
		writer.println("xmlns:lapis=\"" + host + "Prototyp/Ontology/Lapis#\"");
		writer.println("xmlns:sp=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/\">");
		writer.println("<rdf:Description rdf:about=\"" + host + "Prototyp/SFBServletStripTs#i\">");
		writer.println("<rdf:type rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/Category:Coginitive_App\"/>");
		writer.println("<rdfs:label xml:lang=\"de\">Cognitive-StripTs-Service</rdfs:label>");
		writer.println("<rdfs:label xml:lang=\"en\">Cognitive-StripTs-Service</rdfs:label>");
		writer.println("<owl:sameAs	rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/StripTs\"/>");
		writer.println("<lapis:hasCreator rdf:resource=\"http://surgipedia.sfb125.de/wiki/User:Philipp_G\"/>");
		writer.println("<lapis:hasAbstract xml:lang=\"de\">Segmentierung des Gehirns einer Kopfaufnahme.</lapis:hasAbstract>");
		writer.println("<lapis:hasAbstract xml:lang=\"en\">Segmentation of brain in a head scan.</lapis:hasAbstract>");
		writer.println("<lapis:hasSourceCode>https://code.google.com/p/cognitive-apps</lapis:hasSourceCode>");
		writer.println("<lapis:hasServiceDescription>" + host + "Prototyp/StripTs/description/index.html</lapis:hasServiceDescription>");
		writer.println("<lapis:hasInputDescription>T1 image of patient, atlasImage and atlasMask.</lapis:hasInputDescription>");
		writer.println("<lapis:hasOutputDescription>Stripped image and mask of T1 patient.</lapis:hasOutputDescription>");
		writer.println("<lapis:hasExampleRequest>" + host + "Prototyp/StripTs/RDF_Input_Example_1.xml</lapis:hasExampleRequest>");
		writer.println("<lapis:hasExampleResponse>" + host + "Prototyp/StripTs/RDF_Output_Example_1.xml</lapis:hasExampleResponse>");
		writer.println("<lapis:hasExampleRequest>" + host + "Prototyp/StripTs/RDF_Input_Example_2.xml</lapis:hasExampleRequest>");
		writer.println("<lapis:hasExampleResponse>" + host + "Prototyp/StripTs/RDF_Output_Example_2.xml</lapis:hasExampleResponse>");
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
		RequestDataStripTs requestDataStripTs = null;

		String salt = null;
		String requestURI = null;
		String inputBrainAtlasImage = null;
		String inputBrainAtlasMask = null;
		String inputImage = null;
		String tmp_dir = Helper.getProperties("tmp_dir");
		
		long unixTimestamp = System.currentTimeMillis() / 1000L;
		int random = (int) ((Math.random()) * 999999999 + 1);
		
		String inputBrainAtlasImagePathOnDisc = tmp_dir + unixTimestamp + "_" + random + "_";
		String inputBrainAtlasMaskPathOnDisc = tmp_dir + unixTimestamp + "_" + random + "_";
		String inputImagePathOnDisc = tmp_dir + unixTimestamp + "_" + random + "_";
		
		String resultImagePathOnDisc = tmp_dir;
		String resultMaskPathOnDisc = tmp_dir;
		
		String result = null;
		String downloadLinkImage = null;
		String downloadLinkMask = null;
		
		final String a = null;
		
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
					
					boolean hasDownload = true;
					if(result.equalsIgnoreCase("Failure! Please check quality of request data!")) hasDownload = false;
					
					downloadLinkImage 	= Helper.moveFileToDownloadFolder(resultMaskPathOnDisc, "StripTs");
					downloadLinkMask 	= Helper.moveFileToDownloadFolder(resultImagePathOnDisc, "StripTs");
					
					response.setContentType("application/xml");

					String rdf = "<rdf:RDF xmlns:lapis=\"" + host + "Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"  xmlns:stripts=\"" + host + "Prototyp/StripTs/Ontology#\">"
					+ "<rdf:Description rdf:about=\""
					+ requestDataStripTs.getRequestURI()
					+ "\">"
					+ "<rdf:type rdf:resource=\"" + host + "Prototyp/Ontology/Lapis#Request\"/>"
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
					+ "</lapis:hasResult>";
					
					if(hasDownload){
						rdf +=	"<lapis:hasDownload>"+ downloadLinkMask + "</lapis:hasDownload>"
							+	"<lapis:hasDownload>"+ downloadLinkImage + "</lapis:hasDownload>";
					}
							
						rdf +="</rdf:Description></rdf:RDF>";
					
					

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

	private RequestDataStripTs importRequestDataStripTs(BufferedReader reader) {
		RDImportHandlerStripTs handler;

		try {
			handler = new RDImportHandlerStripTs();
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
		return new RequestDataStripTs(handler.getInputBrainAtlasImage(), handler.getInputBrainAtlasMask(), handler.getInputImage(),
				handler.getSalt(), handler.getRequestURI());
	}

}
