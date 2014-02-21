package kit.sfb.cognitive.apps.converter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.deri.xsparql.evaluator.XSPARQLEvaluator;

import com.google.common.io.CharStreams;

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

		//Case 2:
		if(request.getParameter("RequestXSPARQLInput") != null && request.getParameter("RequestXMLRDFInput") != null ){
			inputXSPARQL = new BufferedReader(new StringReader(request.getParameter("RequestXSPARQLInput")));
			inputXMLRDF = new BufferedReader(new StringReader(request.getParameter("RequestXMLRDFInput")));
			requeststringXSPARQL =request.getParameter("RequestXSPARQLInput");
			System.out.println(requeststringXSPARQL);
			requeststringXMLRDF =request.getParameter("RequestXMLRDFInput");
			System.out.println(requeststringXMLRDF);
			case_id = 2;
		}
		
		//Case 1:
		// Parse input according to request method
		if (request.getParameter("RequestInput") != null) {
			inputXSPARQL = new BufferedReader(new StringReader(request.getParameter("RequestInput")));
			System.out.println("+++++++JSP++++++++");
			System.out.println(request.getParameter("RequestInput"));
			requeststringXSPARQL =request.getParameter("RequestInput");
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			case_id = 1;
		} else {
			inputXSPARQL = new BufferedReader(new StringReader(CharStreams.toString(request.getReader())));
			System.out.println("++++++Client++++++");
			System.out.println(CharStreams.toString(request.getReader()));
			requeststringXSPARQL = CharStreams.toString(request.getReader());
			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			case_id = 1;
		}
		
		//CASE 1: Convertert.jsp
		if(case_id == 1){
			//Convert
			XSPARQLEvaluator xe = new XSPARQLEvaluator();
			Writer o = new StringWriter();
			try {
				xe.evaluate(inputXSPARQL, o);
				result = o.toString();
			} catch (Exception e) {
				result ="Invalid Input!";
			}
			
			//Response
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
					+ requeststringXSPARQL
					+ "</textarea></div>"
					+ "</body>"
					+ "</html>";
			response.getWriter().println(html);
			inputXSPARQL.close();
		}
		
		//CASE 2: Convertert_2.jsp
		if(case_id == 2){
			
			//Convert
			
			//Store in TMP-FIle
//			File tmp = new File("C:/Users/phiL/Desktop/tmp.xml");
//			if (!tmp.exists()) {
//				tmp.createNewFile();
//			}
//			FileWriter fw = new FileWriter(tmp.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(requeststringXMLRDF);
//			bw.close();
			
			XSPARQLEvaluator xe = new XSPARQLEvaluator();
			Writer o = new StringWriter();
			try {
				xe.evaluate(inputXSPARQL, o);
				result = o.toString();
			} catch (Exception e) {
				result ="Invalid Input!";
			}
			
			//Response
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
					+ requeststringXSPARQL
					+ "</textarea></div>"
					+ "</body>"
					+ "</html>";
			response.getWriter().println(html);
			inputXSPARQL.close();
		}
		
		
		
		
		
	}

}
