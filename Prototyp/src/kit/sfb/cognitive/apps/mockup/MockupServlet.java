package kit.sfb.cognitive.apps.mockup;

import java.io.IOException;
import java.io.PrintWriter;

import kit.sfb.cognitive.apps.pipeline.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MockupServlet
 */
public class MockupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MockupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Pipeline.startPipeline();
		response.setContentType("text/html");
		
		PrintWriter writer = response.getWriter();
		writer.println("<html>");
    	writer.println("<head><title>Mockup</title></head>");
    	writer.println("<body>");
    	writer.println("	<h1 align=\"center\">Mockup</h1>");
    	writer.println("<div align=\"center\"><b>Input:</b><br/></div>");
    	writer.println("<table align=\"center\">");
    	writer.println("<tr>");
    	writer.println("<td align=\"center\">T1.nrrd</td>");
    	writer.println("<td align=\"center\">atlasImage.mha</td>");
    	writer.println("<td align=\"center\">atlasMask.mha</td>");
    	writer.println("</tr>");
    	writer.println("<tr>");
    	writer.println("<td>");
    	writer.println("<div><img src=\"Mockup/T1_input.PNG\" width=\"150\" height=\"130\"/></div>");
    	writer.println("</td>");
    	writer.println("<td>");
    	writer.println("<div><img src=\"Mockup/atlasImage_input.PNG\" width=\"150\" height=\"130\"/></div>");
    	writer.println("</td>");
    	writer.println("<td>");
    	writer.println("<div><img src=\"Mockup/atlasMask_input.PNG\" width=\"150\" height=\"130\"/></div>");
    	writer.println("</td></tr></table>");
    	writer.println("<br/><br/><br/><br/>");
    	writer.println("<div align=\"center\"><b>Output:</b><br/><div align=\"center\">meanfree_t1_normalized_image.nrrd</div><img src=\"Mockup/output.PNG\" width=\"150\" height=\"130\"/></div></div>");
    	writer.println("</body>");
    	writer.println("</html>");
    		
    	writer.close();			

		
		
	}

}
