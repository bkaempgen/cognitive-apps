package kit.sfb.cognitive.apps.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.deri.xsparql.evaluator.XSPARQLEvaluator;

/**
 * Servlet implementation class SFBServletXSPARQL
 */
public class SFBServletXSPARQL extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SFBServletXSPARQL() {
		super();
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
		writer.println("xmlns:lapis=\"http://141.52.218.34:8080/Prototyp/Ontology/Lapis#\"");
		writer.println("xmlns:sp=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/\">");
		writer.println("<rdf:Description rdf:about=\"http://141.52.218.34:8080/Prototyp/SFBServletXSPARQL#i\">");
		writer.println("<rdf:type rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/Category:Coginitive_App\"/>");
		writer.println("<rdfs:label xml:lang=\"de\">Converter Service: XML to RDF / RDF to XML</rdfs:label>");
		writer.println("<rdfs:label xml:lang=\"en\">Converter Service: XML zu RDF / RDF zu XML</rdfs:label>");
		writer.println("<owl:sameAs	rdf:resource=\"http://surgipedia.sfb125.de/wiki/Special:URIResolver/XSPARQL\"/>");
		writer.println("<lapis:hasCreator rdf:resource=\"http://surgipedia.sfb125.de/wiki/User:Philipp_G\"/>");
		writer.println("<lapis:hasAbstract xml:lang=\"de\">Konvertierung einer gegeben XML/RDF-Datei via XSPARQL-Regeln.</lapis:hasAbstract>");
		writer.println("<lapis:hasAbstract xml:lang=\"en\">Converts a given XML/RDF file via XSPARQL rules.</lapis:hasAbstract>");
		writer.println("<lapis:hasInputDescription>XSPARQL-File.</lapis:hasInputDescription>");
		writer.println("<lapis:hasOutputDescription>Converted data according to specified data format.</lapis:hasOutputDescription>");
		writer.println("</rdf:Description>");
		writer.println("</rdf:RDF>");
		writer.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader inputXSPARQL = null;
		BufferedReader inputXMLRDF = null;
		String requeststringXSPARQL = null;
		String requeststringXMLRDF = null;
		String result = null;
		int case_id;

		try{
			inputXSPARQL = new BufferedReader(new StringReader(request.getParameter("RequestXSPARQLInput")));
			requeststringXSPARQL = request.getParameter("RequestXSPARQLInput");
			
			}catch(Exception e){}
		
		try{
			inputXMLRDF = new BufferedReader(new StringReader(request.getParameter("RequestXMLRDFInput")));
			requeststringXMLRDF = request.getParameter("RequestXMLRDFInput");
			}catch(Exception e){}
		
		try{
			inputXSPARQL = new BufferedReader(new StringReader(request.getParameter("RequestInput")));
			requeststringXSPARQL = request.getParameter("RequestInput");
			}catch(Exception e){}
		
		
		System.out.println("++++++++++++++++++++++++++++XSPARQL+++++++++++++++++++++++++++++++++");
		System.out.println(requeststringXSPARQL);
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		try{System.out.println("++++++++++++++++++++++++++++XML/RDF+++++++++++++++++++++++++++++++++");
			System.out.println(requeststringXMLRDF);
			System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			}catch(Exception e){}


		// CASE 1: Convertert.jsp
		if (request.getParameter("RequestXMLRDFInput") == null) {
			// Convert
			XSPARQLEvaluator xe = new XSPARQLEvaluator();
			Writer o = new StringWriter();
			try {
				xe.evaluate(inputXSPARQL, o);
				result = o.toString();
			} catch (Exception e) {
				result = "Invalid Input!";
			}

			// Response
			response.setContentType("text/html");
			String html = "<html>"
					+ "<head><title>XSPARQL - RDF/XML Converter</title></head>"
					+ "<body>"
					+ "<script type=\"text/javascript\">function showDiv() {document.getElementById('request').style.display = \"block\";}</script>"

					+ "<h1 align=\"center\">Converter</h1>"
					+ "<div align=\"center\">Result:</div>"
					+ "<div align=\"center\"><textarea cols=\"115\" rows=\"12\">"
					+ result
					+ "</textarea></div>"
					+ "<div align=\"center\"><input type=\"submit\" value=\"Show Request\" onclick=\"showDiv();this.style.visibility= 'hidden';\"></div>"
					+ "<div align=\"center\" id=\"request\" style=\"display: none;\" class=\"answer_list\"><div align=\"center\">Request:</div><textarea cols=\"115\" rows=\"12\">"
					+ requeststringXSPARQL + "</textarea></div>" + "</body>" + "</html>";
			response.getWriter().println(html);
			inputXSPARQL.close();
		}

		// CASE 2: Convertert_2.jsp
		if (request.getParameter("RequestXMLRDFInput") != null) {


			 //Store in TMP-FIle
			int random = (int)((Math.random()) * 9999 + 1);
			String filename = "/data/SFB/xsparql/tmp" + random + ".tmp";
			System.out.println("+++++++++++++++++++++++++++FILENAME++++++++++++++++++++++++++++++++++++");
			System.out.println(filename);
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			
			File tmp = new File(filename);
			if (!tmp.exists()) {
				tmp.createNewFile();
			}
			
			FileWriter fw = new FileWriter(tmp.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(requeststringXMLRDF);
			bw.close();
			
			//Extract source and add tmp file as source
			String mydata = requeststringXSPARQL;
			
			//Cases
			
			
			Pattern pattern = Pattern.compile("[M][y][S][p][e][c][i][f][i][e][d][D][a][t][a]");
			Matcher matcher = pattern.matcher(mydata);
			
			if (matcher.find())
			{
			    System.out.println("Match founded!");
			    mydata = mydata.replaceAll("[M][y][S][p][e][c][i][f][i][e][d][D][a][t][a]", filename);
			    System.out.println("+++++++++++++++++++++++++++newXSPARQL++++++++++++++++++++++++++++++++++++");
			    System.out.println(mydata);
			    System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			}
			
			//Convert
			InputStream inputstream = new ByteArrayInputStream(mydata.getBytes());
			BufferedReader inputXSPARQLnew = new BufferedReader(new InputStreamReader(inputstream));

			XSPARQLEvaluator xe = new XSPARQLEvaluator();
			Writer o = new StringWriter();
			try {
				xe.evaluate(inputXSPARQLnew, o);
				result = o.toString();
			} catch (Exception e) {
				result = "Invalid Input!";
			}
			
			//Delete Tmp-File
			try{
				tmp.delete();
				System.out.println("++++++++++++File deleted++++++++++++++");
			}catch(Exception e){		 
	    		e.printStackTrace();
	    	}

			// Response
			response.setContentType("text/html");
			String html = "<html>"
					+ "<head><title>XSPARQL - RDF/XML Converter</title></head>"
					+ "<body>"
					+ "<script type=\"text/javascript\">function showDiv() {document.getElementById('request').style.display = \"block\";}</script>"

					+ "<h1 align=\"center\">Converter</h1>"
					+ "<div align=\"center\">Result:</div>"
					+ "<div align=\"center\"><textarea cols=\"115\" rows=\"12\">"
					+ result
					+ "</textarea></div>"
					+ "<div align=\"center\"><input type=\"submit\" value=\"Show Request\" onclick=\"showDiv();this.style.visibility= 'hidden';\"></div>"
					+ "<div align=\"center\" id=\"request\" style=\"display: none;\" class=\"answer_list\"><div align=\"center\">Request:</div><table><tr><td><textarea cols=\"80\" rows=\"25\">"
					+ requeststringXSPARQL + "</textarea></td><td><textarea cols=\"80\" rows=\"25\">"
					+ requeststringXMLRDF + "</textarea></td></tr></table></div>" + "</body>" + "</html>";
			response.getWriter().println(html);
			inputXSPARQL.close();
		}

	}

}
