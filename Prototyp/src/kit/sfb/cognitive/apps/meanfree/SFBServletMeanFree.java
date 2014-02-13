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
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

		writer.println("<html>");
		writer.println("<head></head>");
		writer.println("<body>");
		writer.println("<table border=\"0\"><tr><td>");
		writer.println("<textarea rows=\"50\" cols=\"120\">@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema/> .");
		writer.println("@prefix owl:     <http://www.w3.org/2002/07/owl#> .");
		writer.println("@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .");
		writer.println("@prefix test:    <http://localhost:8080/Prototyp/SFBServletMeanFree#> .");
		writer.println("@prefix xs:	 <http://www.w3.org/2001/XMLSchema#> .");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletMeanFree#Request");
		writer.println("test:Request");
		writer.println("		rdf:type	rdfs:Class;");
		writer.println("		rdfs:comment 	\"The class containing all requests.\".");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletMeanFree#hasInputImage");
		writer.println("test:hasInputImage");
		writer.println("		rdf:type 	rdf:Property;");
		writer.println("		rdfs:comment 	\"Input Image to be normalized.\";");
		writer.println("		rdfs:domain 	test:Request;");
		writer.println("		rdfs:range 	xs:string.");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletMeanFree#hasInputMaskImage");
		writer.println("test:hasInputMaskImage");
		writer.println("		rdf:type 	rdf:Property;");
		writer.println("		rdfs:comment 	\"Input Image Mask for Normalization or none.\";");
		writer.println("		rdfs:domain 	test:Request;");
		writer.println("		rdfs:range 	xs:string.");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletMeanFree#hasOutputImagePath");
		writer.println("test:hasOutputImagePath");
		writer.println("		rdf:type 	rdf:Property;");
		writer.println("		rdfs:comment 	\"Output Path of normalized Image.\";");
		writer.println("		rdfs:domain 	test:Request;");
		writer.println("		rdfs:range 	xs:string.");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletMeanFree#hasResult");
		writer.println("test:hasResult");
		writer.println("		rdf:type	rdf:Property;");
		writer.println("		rdfs:comment	\"Result of Image Normalization.\";");
		writer.println("		rdfs:domain	test:Request;");
		writer.println("		rdfs:range	xs:string.");
		writer.println("</textarea></td><td style=\"padding-left:100px\">RDF can be POSTed to <a href=\"MeanFree/description/index.html\">service</a><br/>Example:<a href=\"MeanFree/RDF_Input_Example.xml\">RDF In</a> and <a href=\"MeanFree/RDF_Output_Example.xml\">RDF Out</a></td></tr></table></body>");
		writer.println("</html>");

		writer.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		BufferedReader input = new BufferedReader(new StringReader(
				request.getParameter("RequestInput")));

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

			String rdf = "<rdf:RDF xmlns:test=\"http://localhost:8080/Prototyp/SFBServletMeanFree#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">"
					+ "<rdf:Description rdf:about=\""
					+ requestDataMeanFree.getRequestURI()
					+ "\">"
					+ "<rdf:type rdf:resource=\"&amp;test;Request\"/>"
					+ "<test:hasInputImage>"
					+ requestDataMeanFree.getInputImage()
					+ "</test:hasInputImage>"
					+ "<test:hasInputMaskImage>"
					+ requestDataMeanFree.getInputMaskImage()
					+ "</test:hasInputMaskImage>"
					+ "<test:hasOutputImagePath>"
					+ requestDataMeanFree.getOutputImagePath()
					+ "</test:hasOutputImagePath>"
					+ "<test:hasResult>"
					+ result
					+ "</test:hasResult>"
					+ "</rdf:Description></rdf:RDF>";

			System.out.println(rdf);
			response.getWriter().print(rdf);

		} catch (Throwable t) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND,
					"corrupt input");
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
			if (handler.getError() || handler.getInputImage() == null
					|| handler.getInputMaskImage() == null
					|| handler.getOutputImagePath() == null)
				return null;
		} catch (Throwable t) {
			return null;
		}
		return new RequestDataMeanFree(handler.getInputImage(),
				handler.getInputMaskImage(), handler.getOutputImagePath(),
				handler.getRequestURI());
	}

}