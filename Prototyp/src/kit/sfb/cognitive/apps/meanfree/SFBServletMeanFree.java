package kit.sfb.cognitive.apps.meanfree;

import java.io.BufferedReader;
import java.io.IOException;
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
 * Servlet implementation class SFBServletMeanFree
 */
public class SFBServletMeanFree extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SFBServletMeanFree() {
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

		// Parse input according to request method
		if (request.getParameter("RequestInput") != null) {
			input = new BufferedReader(new StringReader(request.getParameter("RequestInput")));
			System.out.println("+++++++JSP++++++++");
			System.out.println(request.getParameter("RequestInput"));
		} else {
			input = new BufferedReader(new StringReader(CharStreams.toString(request.getReader())));
			System.out.println("++++++Client++++++");
			System.out.println(CharStreams.toString(request.getReader()));
		}

		try {
			RequestDataMeanFree requestDataMeanFree = importRequestDataMeanFree(input);
			String inputImage = requestDataMeanFree.getInputImage();
			String inputMaskImage = requestDataMeanFree.getInputMaskImage();
			String outputImagePath = requestDataMeanFree.getOutputImagePath();

			// New execution style with helper class
			List<String> parameters = new ArrayList<String>();
			parameters.add(inputImage);
			parameters.add(inputMaskImage);
			parameters.add(outputImagePath);
			String result = Helper.RunCommandLineTool("MeanFree", parameters);

			// Response
			response.setContentType("text/xml");

			String rdf ="<rdf:RDF xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:meanfree=\"http://localhost:8080/Prototyp/MeanFree/Ontology#\">"
					+ "<rdf:Description rdf:about=\""
					+ requestDataMeanFree.getRequestURI()
					+ "\">"
					+ "<rdf:type rdf:resource=\"http://localhost:8080/Prototyp/Ontology/Lapis#Request\"/>"
					+ "<meanfree:hasInputImage>"
					+ requestDataMeanFree.getInputImage()
					+ "</meanfree:hasInputImage>"
					+ "<meanfree:hasInputMaskImage>"
					+ requestDataMeanFree.getInputMaskImage()
					+ "</meanfree:hasInputMaskImage>"
					+ "<meanfree:hasOutputImagePath>"
					+ requestDataMeanFree.getOutputImagePath()
					+ "</meanfree:hasOutputImagePath>" + "<lapis:hasResult>" + result + "</lapis:hasResult>" + "</rdf:Description></rdf:RDF>";

			System.out.println(rdf);
			response.getWriter().print(rdf);

		} catch (Throwable t) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "corrupt input");
		}
	}

	private RequestDataMeanFree importRequestDataMeanFree(BufferedReader reader) {
		RDImportHandlerMeanFree handler;

		try {
			handler = new RDImportHandlerMeanFree();
			SAXParser parser = new SAXParser();
			parser.setContentHandler(handler);
			parser.parse(new InputSource(reader));
			// new ByteArrayInputStream(data.getBytes())));
			if (handler.getError() || handler.getInputImage() == null || handler.getInputMaskImage() == null
					|| handler.getOutputImagePath() == null)
				return null;
		} catch (Throwable t) {
			return null;
		}
		return new RequestDataMeanFree(handler.getInputImage(), handler.getInputMaskImage(), handler.getOutputImagePath(),
				handler.getRequestURI());
	}

}