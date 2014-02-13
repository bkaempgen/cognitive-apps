package kit.edu;

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

import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;

/**
 * Servlet implementation class SFBServletStripTs
 */
public class SFBServletStripTs extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SFBServletStripTs() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/html");
		PrintWriter writer = response.getWriter();

		writer.println("<html>");
		writer.println("<head></head>");
		writer.println("<body>");
		writer.println("<table border=\"0\"><tr><td>");
		writer.println("<textarea rows=\"65\" cols=\"120\">@prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema/> .");
		writer.println("@prefix owl:     <http://www.w3.org/2002/07/owl#> .");
		writer.println("@prefix rdf:     <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .");
		writer.println("@prefix test:    <http://localhost:8080/Prototyp/SFBServletStripTs#> .");
		writer.println("@prefix xs:	 <http://www.w3.org/2001/XMLSchema#> .");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletStripTs#Request");
		writer.println("test:Request");
		writer.println("		rdf:type	rdfs:Class;");
		writer.println("		rdfs:comment 	\"The class containing all requests.\".");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletStripTs#hasInputBrainAtlasImage");
		writer.println("test:hasInputBrainAtlasImage");
		writer.println("		rdf:type 	rdf:Property;");
		writer.println("		rdfs:comment 	\"Input Atlas Image for Stripping.\";");
		writer.println("		rdfs:domain 	test:Request;");
		writer.println("		rdfs:range 	xs:string.");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletStripTs#hasInputBrainAtlasMask");
		writer.println("test:hasInputBrainAtlasMask");
		writer.println("		rdf:type 	rdf:Property;");
		writer.println("		rdfs:comment 	\"Input Atlas Mask for Stripping.\";");
		writer.println("		rdfs:domain 	test:Request;");
		writer.println("		rdfs:range 	xs:string.");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletStripTs#hasInputImage");
		writer.println("test:hasInputImage");
		writer.println("		rdf:type 	rdf:Property;");
		writer.println("		rdfs:comment 	\"Input Image to be stripped.\";");
		writer.println("		rdfs:domain 	test:Request;");
		writer.println("		rdfs:range 	xs:string.");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletStripTs#hasOutputMaskPath	");
		writer.println("test:hasOutputMaskPath");
		writer.println("		rdf:type 	rdf:Property;");
		writer.println("		rdfs:comment 	\"Output Path of stripped Mask.\";");
		writer.println("		rdfs:domain 	test:Request;");
		writer.println("		rdfs:range 	xs:string.");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletStripTs#hasOutputImagePath");
		writer.println("test:hasOutputImagePath");
		writer.println("		rdf:type 	rdf:Property;");
		writer.println("		rdfs:comment 	\"Output Path of stripped Image.\";");
		writer.println("		rdfs:domain 	test:Request;");
		writer.println("		rdfs:range 	xs:string.");
		writer.println("");
		writer.println("");
		writer.println("###  http://localhost:8080/Prototyp/SFBServletStripTs#hasResult");
		writer.println("test:hasResult");
		writer.println("		rdf:type	rdf:Property;");
		writer.println("		rdfs:comment	\"Result of Image Stripping.\";");
		writer.println("		rdfs:domain	test:Request;");
		writer.println("		rdfs:range	xs:string.");
		writer.println("</textarea></td><td style=\"padding-left:100px\">RDF can be POSTed to <a href=\"StripTs/description/index.html\">service</a><br/>Example:<a href=\"StripTs/RDF_Input_Example.xml\">RDF In</a> and <a href=\"StripTs/RDF_Output_Example.xml\">RDF Out</a></td></tr></table></body>");
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
			RequestDataStripTs requestDataStripTs = importRequestDataStripTs(input);

			String inputBrainAtlasImage = requestDataStripTs
					.getInputBrainAtlasImage();
			String inputBrainAtlasMask = requestDataStripTs
					.getInputBrainAtlasMask();
			String inputImage = requestDataStripTs.getInputImage();
			String outputMaskPath = requestDataStripTs.getOutputMaskPath();
			String outputImagePath = requestDataStripTs.getOutputImagePath();

			// New execution style with helper class
			List<String> parameters = new ArrayList<String>();
			parameters.add(inputBrainAtlasImage);
			parameters.add(inputBrainAtlasMask);
			parameters.add(inputImage);
			parameters.add(outputMaskPath);
			parameters.add(outputImagePath);
			String result = Helper.RunCommandLineTool("StripTs", parameters);

			// Response
			response.setContentType("text/xml");

			String rdf = "<rdf:RDF xmlns:test=\"http://localhost:8080/Prototyp/SFBServletStripTs#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">"
					+ "<rdf:Description rdf:about=\""
					+ requestDataStripTs.getRequestURI()
					+ "\">"
					+ "<rdf:type rdf:resource=\"&amp;test;Request\"/>"
					+ "<test:hasInputBrainAtlasImage>"
					+ requestDataStripTs.getInputBrainAtlasImage()
					+ "</test:hasInputBrainAtlasImage>"
					+ "<test:hasInputBrainAtlasMask>"
					+ requestDataStripTs.getInputBrainAtlasMask()
					+ "</test:hasInputBrainAtlasMask>"
					+ "<test:hasInputImage>"
					+ requestDataStripTs.getInputImage()
					+ "</test:hasInputImage>"
					+ "<test:hasOutputMaskPath>"
					+ requestDataStripTs.getOutputMaskPath()
					+ "</test:hasOutputMaskPath>"
					+ "<test:hasOutputImagePath>"
					+ requestDataStripTs.getOutputImagePath()
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

	private RequestDataStripTs importRequestDataStripTs(BufferedReader reader) {
		RDImportHandlerStripTs handler;

		try {
			handler = new RDImportHandlerStripTs();
			SAXParser parser = new SAXParser();
			parser.setContentHandler(handler);
			parser.parse(new InputSource(reader));
			// new ByteArrayInputStream(data.getBytes())));
			if (handler.getError() || handler.getInputBrainAtlasImage() == null
					|| handler.getInputBrainAtlasMask() == null
					|| handler.getInputImage() == null
					|| handler.getOutputMaskPath() == null
					|| handler.getOutputImagePath() == null)
				return null;
		} catch (Throwable t) {
			return null;
		}
		return new RequestDataStripTs(handler.getInputBrainAtlasImage(),
				handler.getInputBrainAtlasMask(), handler.getInputImage(),
				handler.getOutputMaskPath(), handler.getOutputImagePath(),
				handler.getRequestURI());
	}

}
