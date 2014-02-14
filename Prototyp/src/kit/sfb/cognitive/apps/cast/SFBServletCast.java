package kit.sfb.cognitive.apps.cast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.sfb.cognitive.apps.helper.Helper;

import org.xml.sax.InputSource;

import com.google.common.io.CharStreams;
import com.sun.org.apache.xerces.internal.parsers.SAXParser;

/**
 * Servlet implementation class SFBServletCast
 */
public class SFBServletCast extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SFBServletCast() {
		super();
		// TODO Auto-generated constructor stub
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BufferedReader input = null;

		// Parse input according to request method
		if (request.getParameter("RequestInput") != null) {
			input = new BufferedReader(new StringReader(request.getParameter("RequestInput")));
			System.out.println("+++++++JSP++++++++");
			System.out.println(request.getParameter("RequestInput"));
		}else{
			input = new BufferedReader(new StringReader(CharStreams.toString(request.getReader())));
			System.out.println("++++++Client++++++"); 
			System.out.println(CharStreams.toString(request.getReader())); 
		}


		try {
			RequestDataCast requestDataCast = importRequestDataCast(input);
			String inputImage = requestDataCast.getInputImage();
			String outputImagePath = requestDataCast.getOutputImagePath();

			// New execution style with helper class
			List<String> parameters = new ArrayList<String>();
			parameters.add(inputImage);
			parameters.add(outputImagePath);
			String result = Helper.RunCommandLineTool("Cast", parameters);

			// Response
			response.setContentType("application/xml");

			String rdf = "<rdf:RDF xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:cast=\"http://localhost:8080/Prototyp/Cast/Ontology#\">"
					+ "<rdf:Description rdf:about=\""
					+ requestDataCast.getRequestURI()
					+ "\">"
					+ "<rdf:type rdf:resource=\"http://localhost:8080/Prototyp/Ontology/Lapis#Request\"/>"
					+ "<cast:hasInputImage>"
					+ requestDataCast.getInputImage()
					+ "</cast:hasInputImage>"
					+ "<cast:hasOutputImagePath>"
					+ requestDataCast.getOutputImagePath()
					+ "</cast:hasOutputImagePath>"
					+ "<lapis:hasResult>"
					+ result
					+ "</lapis:hasResult>" + "</rdf:Description></rdf:RDF>";

			System.out.println(rdf);
			response.getWriter().print(rdf);

		} catch (Throwable t) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "corrupt input");
		}
	}

	private RequestDataCast importRequestDataCast(BufferedReader reader) {
		RDImportHandlerCast handler;

		try {
			handler = new RDImportHandlerCast();
			SAXParser parser = new SAXParser();
			parser.setContentHandler(handler);
			parser.parse(new InputSource(reader));
			// new ByteArrayInputStream(data.getBytes())));
			if (handler.getError() || handler.getInputImage() == null || handler.getOutputImagePath() == null)
				return null;
		} catch (Throwable t) {
			return null;
		}
		return new RequestDataCast(handler.getInputImage(), handler.getOutputImagePath(), handler.getRequestURI());
	}

}