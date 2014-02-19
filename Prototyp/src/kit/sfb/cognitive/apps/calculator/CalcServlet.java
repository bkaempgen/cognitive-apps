package kit.sfb.cognitive.apps.calculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;

/**
 * Servlet implementation class CalcServlet
 */
public class CalcServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CalcServlet() {
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
		response.sendRedirect("/Prototyp/Calc/index.html");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println(request.getParameter("RequestInput"));
		BufferedReader input = new BufferedReader(new StringReader(
				request.getParameter("RequestInput")));

		try {
			RequestData requestData = importRequestData(input);
			int firstAddend = requestData.getFirstAddend();
			int secondAddend = requestData.getSecondAddend();

			firstAddend = requestData.getFirstAddend();
			secondAddend = requestData.getSecondAddend();

			// Test-Output
			System.out.println("Erster Summand = " + firstAddend);
			System.out.println("Zweiter Summand = " + secondAddend);
			System.out.println("URI request" + requestData.getRequestURI());

			// Calculation
			int result = firstAddend + secondAddend;

			// Response
			response.setContentType("text/xml");

			String rdf = "<rdf:RDF xmlns:test=\"http://141.52.218.34:8080/Prototyp/CalcServlet#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">"
					+ "<rdf:Description rdf:about=\""
					+ requestData.getRequestURI()
					+ "\">"
					+ "<rdf:type rdf:resource=\"&amp;test;Request\"/>"
					+ "<test:hasFirstAddend>"
					+ requestData.getFirstAddend()
					+ "</test:hasFirstAddend>"
					+ "<test:hasSecondAddend>"
					+ requestData.getSecondAddend()
					+ "</test:hasSecondAddend>"
					+ "<test:hasResult>"
					+ result
					+ "</test:hasResult>"
					+ "</rdf:Description></rdf:RDF>";

			System.out.println(rdf);
			response.getWriter().print(rdf);

		} catch (Throwable t) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "corrupt input");
		}

	}

	private RequestData importRequestData(BufferedReader reader) {
		RDImportHandler handler;

		try {
			handler = new RDImportHandler();
			System.out.println("print fail 1");
			SAXParser parser = new SAXParser();
			parser.setContentHandler(handler);
			System.out.println("print fail 12");
			parser.parse(new InputSource(reader));// new
													// ByteArrayInputStream(data.getBytes())));
			System.out.println("print fail 13");
			if (handler.getError() || handler.getFirstAddend() == -1
					|| handler.getSecondAddend() == -1)
				return null;
		} catch (Throwable t) {
			return null;
		}
		return new RequestData(handler.getFirstAddend(),
				handler.getSecondAddend(), handler.getRequestURI());
	}

}
