package kit.sfb.cognitive.apps.cast.neu;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.sfb.cognitive.apps.helper.Helper;
import kit.sfb.cognitive.apps.xnat.Downloader;

import org.xml.sax.InputSource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.common.io.CharStreams;
import com.oreilly.servlet.MultipartResponse;
import com.oreilly.servlet.ServletUtils;
import com.sun.org.apache.xerces.internal.parsers.SAXParser;

public class SFBServletCastNeu extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Data input: file upload or online resource (any or XNAT)
		//Data output: URI for download result via GET 
		
		boolean multipartFileUpload = ServletFileUpload.isMultipartContent(request);
		
		// Request - Processing
		BufferedReader input = null;
		List<FileItem> formFileItems = null;

		if (request.getParameter("RequestInput") != null &!multipartFileUpload) {
			
			// No File-Upload -> online resource
			// URL-Encoded request : must use parameter: "RequestInput"
			// RDF in URL parameter encoded
			// z.B. cURL url-encoded or JSP
			
			input = new BufferedReader(new StringReader(request.getParameter("RequestInput")));
			System.out.println("+++++++URL-Encoded-Request++++++++");
			System.out.println(request.getParameter("RequestInput"));
			
		} else if (!multipartFileUpload){
			
			// No File-Upload -> online resource
			// RDF in body
			
			input = new BufferedReader(new StringReader(CharStreams.toString(request.getReader())));
			System.out.println("+++++++RDF-In-Body++++++++");
			System.out.println(CharStreams.toString(request.getReader()));
		
		} else if(multipartFileUpload){
			
			// File-Upload
			
			// set up multipart stuff
			int MEMORY_THRESHOLD = 1024 * 1024 * 25; 	// 3MB
			int MAX_FILE_SIZE = 1024 * 1024 * 40; 		// 40MB
			int MAX_REQUEST_SIZE = 1024 * 1024 * 50; 	// 50MB

			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(MAX_REQUEST_SIZE);
			
			try {
			
				System.out.println("+++++++File-Upload++++++++");
			
				// Get all parameter items
				List<FileItem> formItems = upload.parseRequest(request);
				formFileItems = formItems;

				for (FileItem item : formItems) {
					
					if (item.isFormField()) {
						// Process regular form fields).
						// Case: RDF urlencoded! - must use: "RequestInput" as parameter name
						
						String fieldname = item.getFieldName();
						String fieldvalue = item.getString();
						
						if (fieldname.equalsIgnoreCase("RequestInput")){
							
							System.out.println("+++++++RDF-URL-Encoded & File-Upload++++++++");
							input = new BufferedReader(new StringReader(fieldvalue));
						}
					} else {
						// Process form file field (input type="file").
						// Case: RDF uploaded via file
						
						String fieldname = item.getFieldName();

						if (fieldname.equalsIgnoreCase("RequestInput")) {
							
							InputStream filecontent = item.getInputStream();
							System.out.println("+++++++RDF-in-File & File-Upload++++++++");
							input = new BufferedReader(new InputStreamReader(filecontent));
						}

					}
				}
			} catch (Throwable t) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND, "Submission failure!");
			}
		}
		
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		// Get needed information from RDF-Request for CDL execution
		String inputImage = null;
		String outputImagePath = null;
		String requestURI = null;
		
		try {
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx RDF Information xxxxxxxxxxxxxxxxxxxxxxxxx");
			
			RequestDataCastNeu requestDataCast = importRequestDataCast(input);

			inputImage = requestDataCast.getInputImage();
			System.out.println("inputImage: " + inputImage);
			outputImagePath = requestDataCast.getOutputImagePath();
			System.out.println("outputImagePath: " + outputImagePath);
			requestURI = requestDataCast.getRequestURI();
			System.out.println("requestURI " + requestURI);
			
		} catch (Throwable t) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Corrupt input!");
		}
		
		// Get files for execution
		
			// InputImage
			UrlValidator urlValidator = new UrlValidator();
			
			if(urlValidator.isValid(inputImage)){
			
				if(inputImage.contains("xnat.sfb125.de")){
					// Get file From XNAT ...
					
					URI inputURI;
					try {
						inputURI = new URI(inputImage);
						File test = Downloader.downloadFile(inputURI);
						FileOutputStream outputStream = new FileOutputStream(test);
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else{
					// Get file from other online resource
					Helper.downloadFileFromOnlineResource(inputImage, "C:/Users/phiL/Desktop/tmp/methodTest.nrrd");	
				
				}
			}
//			}else if (multipartFileUpload){
//				String filePath = "C:/Users/phiL/Desktop/tmp/";
//				
//									// Process the uploaded file items
//									Iterator i = formFileItems.iterator();
//				
//									// List fileItems = upload.parseRequest(request);
//									// Iterator i = fileItems.iterator();
//				
//									while (i.hasNext()) {
//				
//										FileItem fi = (FileItem) i.next();
//										if (!fi.isFormField()) {
//				
//											if (fi.getFieldName().equalsIgnoreCase(inputImageParameterName)) {
//				
//												// Get the uploaded file parameters
//												String fieldName = fi.getFieldName();
//												System.out.println("1 : " + fieldName);
//												String fileName = fi.getName();
//												System.out.println("2 : " + fileName);
//												String contentType = fi.getContentType();
//												System.out.println("3 : " + contentType);
//												boolean isInMemory = fi.isInMemory();
//												System.out.println("4 : " + isInMemory);
//												long sizeInBytes = fi.getSize();
//												System.out.println("5 : " + sizeInBytes);
//												// Write the file
//												if (fileName.lastIndexOf("\\") >= 0) {
//													uploadedImage = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
//												} else {
//													uploadedImage = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
//												}
//												fi.write(uploadedImage);
//				
//												System.out.println("Uploaded Filename: " + fileName);
//												uploadedinputImage = filePath + fileName;
//												System.out.println(uploadedinputImage);
//				
//				
//			}}
//				
//				
//
//		
//		
//
//			
//			
//			
////
////			// Execution information
////			String uploadedinputImage = null;
////			// int random = (int)((Math.random()) * 999999999 + 1);
////			String outputImagePath = "C:/Users/phiL/Desktop/tmp/" + outputImagePathParameterName;
////
////			// Get file types for execution
////			List<String> inputtypes = new ArrayList<String>();
////			inputtypes.add(inputImageType);
////			inputtypes.add(outputImagePathType);
////
////			// set Files for CDL-Execution according to input style : XNAT OR
////			// FILE
////			File uploadedImage = null;
////			for (String s : inputtypes) {
////
////				// Case: File - Upload
////				if (s.equalsIgnoreCase("http://localhost:8080/Prototyp/Ontology/Lapis#FileUpload")) {
////
////					String filePath = "C:/Users/phiL/Desktop/tmp/";
////
////					// Process the uploaded file items
////					Iterator i = formFileItems.iterator();
////
////					// List fileItems = upload.parseRequest(request);
////					// Iterator i = fileItems.iterator();
////
////					while (i.hasNext()) {
////
////						FileItem fi = (FileItem) i.next();
////						if (!fi.isFormField()) {
////
////							if (fi.getFieldName().equalsIgnoreCase(inputImageParameterName)) {
////
////								// Get the uploaded file parameters
////								String fieldName = fi.getFieldName();
////								System.out.println("1 : " + fieldName);
////								String fileName = fi.getName();
////								System.out.println("2 : " + fileName);
////								String contentType = fi.getContentType();
////								System.out.println("3 : " + contentType);
////								boolean isInMemory = fi.isInMemory();
////								System.out.println("4 : " + isInMemory);
////								long sizeInBytes = fi.getSize();
////								System.out.println("5 : " + sizeInBytes);
////								// Write the file
////								if (fileName.lastIndexOf("\\") >= 0) {
////									uploadedImage = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
////								} else {
////									uploadedImage = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
////								}
////								fi.write(uploadedImage);
////
////								System.out.println("Uploaded Filename: " + fileName);
////								uploadedinputImage = filePath + fileName;
////								System.out.println(uploadedinputImage);
////
////							}
////						}
////
////					}
////				}
////			}
////
////			// New execution style with helper class
////			List<String> parameters = new ArrayList<String>();
////			parameters.add(uploadedinputImage);
////			parameters.add(outputImagePath);
////			String result = Helper.RunCommandLineTool("Cast", parameters);
////
////			// Response
////
////			// RDF-Result for Header
////			String rdf = "<rdf:RDF xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:cast=\"http://localhost:8080/Prototyp/Cast/Ontology#\">"
////					+ "<rdf:Description rdf:about=\""
////					+ requestDataCast.getRequestURI()
////					+ "\">"
////					+ "<rdf:type rdf:resource=\"http://localhost:8080/Prototyp/Ontology/Lapis#Request\"/>"
////					+ "<cast:hasInputImage>"
////					+ "<rdf:Description rdf:about=\""
////					+ requestDataCast.getInputImageURI()
////					+ "\">"
////					+ "<rdf:type rdf:resource=\"http://localhost:8080/Prototyp/Ontology/Lapis#InputParameter\"/>"
////					+ "<lapis:kindOfProvision rdf:resource=\""
////					+ requestDataCast.getInputImageType()
////					+ "\"/>"
////					+ "<lapis:hasParameterName>"
////					+ requestDataCast.getInputImageParameterName()
////					+ "</lapis:hasParameterName>"
////					+ "</rdf:Description>"
////					+ "</cast:hasInputImage>"
////					+ "<cast:hasOutputImagePath>"
////					+ "<rdf:Description rdf:about=\""
////					+ requestDataCast.getOutputImagePathURI()
////					+ "\">"
////					+ "<rdf:type rdf:resource=\"http://localhost:8080/Prototyp/Ontology/Lapis#InputParameter\"/>"
////					+ "<lapis:kindOfProvision rdf:resource=\""
////					+ requestDataCast.getOutputImagePathType()
////					+ "\"/>"
////					+ "<lapis:hasParameterName>"
////					+ requestDataCast.getOutputImagePathParameterName()
////					+ "</lapis:hasParameterName>"
////					+ "</rdf:Description>"
////					+ "</cast:hasOutputImagePath>"
////					+ "<lapis:hasResult>"
////					+ result
////					+ "</lapis:hasResult>"
////					+ "</rdf:Description>" + "</rdf:RDF>";
////
////			System.out.println(rdf);
////			// response.getWriter().print(rdf);
////
////			// File as response
////			File file = new File(outputImagePath);
////			int length = 0;
////			ServletOutputStream outStream = response.getOutputStream();
////			ServletContext context = getServletConfig().getServletContext();
////			String mimetype = context.getMimeType(outputImagePath);
////
////			// sets response content type
////			if (mimetype == null) {
////				mimetype = "application/octet-stream";
////			}
////			response.setContentType(mimetype);
////			response.setContentLength((int) file.length());
////			String fileName = (new File(outputImagePath)).getName();
////
////			// sets HTTP header
////			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
////			response.setHeader("RDF", rdf);
////
////			byte[] byteBuffer = new byte[4096];
////			DataInputStream in = new DataInputStream(new FileInputStream(file));
////
////			// reads the file's bytes and writes them to the response stream
////			while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
////				outStream.write(byteBuffer, 0, length);
////			}
////
////			in.close();
////			outStream.close();
////			
////			//Delete Tmp-Files
////			try{
////				file.delete();
////				uploadedImage.delete();
////				System.out.println("++++++++++++Files deleted++++++++++++++");
////			}catch(Exception e){		 
////	    		e.printStackTrace();
////	    	}
////			
////			
////
////			// Multipart-Response
////
////			// ServletOutputStream outStream = response.getOutputStream();
////			//
////			// MultipartResponse multi = new MultipartResponse(response);
////			//
////			//
////			// multi.startResponse("text/plain");
////			// outStream.println("On your mark");
////			// multi.endResponse();
////			//
////			// try { Thread.sleep(1000); } catch (InterruptedException e) { }
////			//
////			// multi.startResponse("text/plain");
////			// outStream.println("Get set");
////			// multi.endResponse();
////
////			// try { Thread.sleep(1000); } catch (InterruptedException e) { }
////			//
////			// multi.startResponse("image/gif");
////			// ServletUtils.returnFile("C:/Users/phiL/Desktop/tmp/atlasImage.nrrd",
////			// outStream);
////
////			// multi.finish();

//		} catch (Throwable t) {
//			response.sendError(HttpServletResponse.SC_NOT_FOUND, "corrupt input");
//		}

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
	// protected void doPost(HttpServletRequest request, HttpServletResponse
	// response) throws ServletException, IOException {
	//
	// BufferedReader input = null;
	//
	// // Parse input according to request method
	// if (request.getParameter("RequestInput") != null) {
	// input = new BufferedReader(new
	// StringReader(request.getParameter("RequestInput")));
	// System.out.println("+++++++JSP++++++++");
	// System.out.println(request.getParameter("RequestInput"));
	// }else{
	// input = new BufferedReader(new
	// StringReader(CharStreams.toString(request.getReader())));
	// System.out.println("++++++Client++++++");
	// System.out.println(CharStreams.toString(request.getReader()));
	// }
	//
	//
	// try {
	// RequestDataCast requestDataCast = importRequestDataCast(input);
	// String inputImage = requestDataCast.getInputImage();
	// String outputImagePath = requestDataCast.getOutputImagePath();
	//
	// // New execution style with helper class
	// List<String> parameters = new ArrayList<String>();
	// parameters.add(inputImage);
	// parameters.add(outputImagePath);
	// String result = Helper.RunCommandLineTool("Cast", parameters);
	//
	// // Response
	// response.setContentType("application/xml");
	//
	// String rdf =
	// "<rdf:RDF xmlns:lapis=\"http://localhost:8080/Prototyp/Ontology/Lapis#\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:cast=\"http://localhost:8080/Prototyp/Cast/Ontology#\">"
	// + "<rdf:Description rdf:about=\""
	// + requestDataCast.getRequestURI()
	// + "\">"
	// +
	// "<rdf:type rdf:resource=\"http://localhost:8080/Prototyp/Ontology/Lapis#Request\"/>"
	// + "<cast:hasInputImage>"
	// + requestDataCast.getInputImage()
	// + "</cast:hasInputImage>"
	// + "<cast:hasOutputImagePath>"
	// + requestDataCast.getOutputImagePath()
	// + "</cast:hasOutputImagePath>"
	// + "<lapis:hasResult>"
	// + result
	// + "</lapis:hasResult>" + "</rdf:Description></rdf:RDF>";
	//
	// System.out.println(rdf);
	// response.getWriter().print(rdf);
	//
	// } catch (Throwable t) {
	// response.sendError(HttpServletResponse.SC_NOT_FOUND, "corrupt input");
	// }
	// }

	private RequestDataCastNeu importRequestDataCast(BufferedReader reader) {
		RDImportHandlerCastNeu handler;

		try {
			handler = new RDImportHandlerCastNeu();
			SAXParser parser = new SAXParser();
			parser.setContentHandler(handler);
			parser.parse(new InputSource(reader));
			// new ByteArrayInputStream(data.getBytes())));
			if (handler.getError() || handler.getInputImage() == null
					|| handler.getOutputImagePath() == null)
				return null;
		} catch (Throwable t) {
			return null;
		}
		return new RequestDataCastNeu(handler.getInputImage(),
				handler.getOutputImagePath(), handler.getRequestURI());
	}

}