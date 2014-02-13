package kit.sfb.cognitive.apps.converter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Servlet implementation class SFBServletDescriber 
 */
public class SFBServletDescriber extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SFBServletDescriber() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/xml");
		PrintWriter writer = response.getWriter();
		writer.println("<rdf:RDF ");
		writer.println("xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"");
		writer.println("xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\"");
		writer.println("xmlns:owl=\"http://www.w3.org/2002/07/owl#\"");
		writer.println("xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\"");
		writer.println("xmlns:sp=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/\">");
		writer.println("<rdf:Description rdf:about=\"http://localhost:8080/Prototyp/SFBServletDescriber#i\">");
		writer.println("<rdf:type rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/Category:Coginitive_App\"/>");
		writer.println("<rdfs:label xml:lang=\"de\">Translator Service: RDF to SPARQL/N3</rdfs:label>");
		writer.println("<rdfs:label xml:lang=\"en\">Translator Service: RDF zu SPARQL/N3</rdfs:label>");
		writer.println("<owl:sameAs	rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/Describer\"/>");
		writer.println("<lapis:hasCreator rdf:resource=\"http://surgipedia.sfb125.de/wiki/User:Philipp_G\"/>");
		writer.println("<lapis:hasAbstract xml:lang=\"de\">Wandelt gegebene RDF Beschreibung von Service in Input/Output SPARQL-Graph-Pattern um.</lapis:hasAbstract>");
		writer.println("<lapis:hasAbstract xml:lang=\"en\">Casts a given rdf description of a service to an Input/Output SPARQL-Graph Pattern.</lapis:hasAbstract>");
		writer.println("<lapis:hasInputDescription>RDF/XML Description of Service.</lapis:hasInputDescription>");
		writer.println("<lapis:hasOutputDescription>SPARQL/N3 Graph Pattern.</lapis:hasOutputDescription>");
		writer.println("<lapis:hasExampleRequest>http://localhost:8080/Prototyp/Describer/Input_Example.xml</lapis:hasExampleRequest>");
		writer.println("<lapis:hasExampleResponse>http://localhost:8080/Prototyp/Describer/Output_Example.html</lapis:hasExampleResponse>");
		writer.println("</rdf:Description>");
		writer.println("</rdf:RDF>");
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
		
		//Generate file from request
		
		

		// Output of RequestInput
		System.out
				.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(request.getParameter("RequestInput"));
		System.out
				.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// Stuff for XSLT
		File xsltFile = new File("C:/Users/phiL/Desktop/lapis.xslt");
		Source xmlSource = new StreamSource(input);
		Source xsltSource = new StreamSource(xsltFile);

		TransformerFactory transFact = TransformerFactory.newInstance();

		try {
			Transformer trans;
			trans = transFact.newTransformer(xsltSource);

			// Response
			response.setContentType("text/html");
			PrintWriter writer = response.getWriter();
			StreamResult output = new StreamResult(writer);

			trans.transform(xmlSource, output);
			
			writer.close();

			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}