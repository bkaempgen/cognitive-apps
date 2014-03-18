package kit.sfb.cognitive.apps.helper;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import kit.sfb.cognitive.apps.xnat.MySSLSocketFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.google.common.io.CharStreams;

public class Helper {

	static List<FileItem> formItemsStore;

	// Method for CDL-Execution
	@SuppressWarnings("deprecation")
	public static String RunCommandLineTool(String servicename, List<String> parameters) throws IOException {

		String mitkCommandLine = "sh /data/SFB/MITK/MITK-MBI-2013.12.99-linux64/mitkBrainStrippingMiniApps.sh";
		String command = mitkCommandLine + " " + servicename;

		// Build command from parameters
		for (String p : parameters) {
			command = command + " " + p;
		}
		System.out.println(command);
		// Initiate Runtime
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		String result = null;
		String outputpath = null;

		try {
			process = runtime.exec(command);
			DataInputStream in = new DataInputStream(process.getInputStream());

			// READ AND PRINT THE OUTPUT
			String line = null;
			int outputcounter = 0;
			while ((line = in.readLine()) != null) {
				System.out.println(line);

				// Extract output path
				if (line.contains("Writing image:")) {
					Pattern myPattern = Pattern.compile(".*(Writing image: )(.*)");
					Matcher m = myPattern.matcher(line);
					if (m.matches()) {

						if (outputcounter == 0) {
							outputpath = m.group(2);
							// result = "Success! Download the file(s) from: " +
							// outputpath;
							outputcounter++;
						} else {
							outputpath = m.group(2);
							// result = result + " " + "," + " " + outputpath;
						}

					}
				}
				if (line.contains("ERROR")) {
					result = "Failure! Please check quality of request data!";
					return result;
				}
				
				
				

			}
			result = "Success!";

		} catch (Exception e) {
			System.out.println("Problem");
			result = "Failure! Please check quality of request data!";
			return result;
		}
		return result;
	}

	public static void deleteFileFromDisc(String filePath) {

		File tmp = new File(filePath);
		tmp.delete();
		System.out.println("++++++++++++Input-File deleted++++++++++++++");
	}

	public static String moveFileToDownloadFolder(String filePath, String webFolder) throws IOException {
		
		File input = new File(filePath);
		
		String webpath = "/usr/local/tomcat/webapps/downloads/" + webFolder + "/" + input.getName();
		
		Path moveSourcePath = Paths.get(filePath);
		Path moveTargetPath = Paths.get(webpath);
		Files.move( moveSourcePath, moveTargetPath );

		return ("http://141.52.218.34:8080/downloads/" + webFolder + "/" + input.getName());

	}

	public static boolean saltIsValid(String webFolder, String salt) {

		String path = "/usr/local/tomcat/webapps/downloads/" + webFolder + "/";

		File folder = new File(path);

		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.getName().contains(salt)) {
				return false;
			}
		}

		return true;
	}

	// HACK wegen SSL
	// Source:
	// http://stackoverflow.com/questions/2642777/trusting-all-certificates-using-httpclient-over-https
	private static DefaultHttpClient getNewHttpClient() {

		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

	private static String getFileName(URI uri) {

		String uriAsString = uri.toASCIIString();

		String baseName = FilenameUtils.getBaseName(uriAsString);
		String extension = FilenameUtils.getExtension(uriAsString);

		return (baseName + "." + extension);
	}

	// Download File from Xnat
	private static String downloadFileFromXNAT(URI uri, String pathOnDisc) throws IOException {

		DefaultHttpClient client = getNewHttpClient();
		HttpGet get = new HttpGet(uri);
		get.setHeader("Content-Type", "application/xml");

		pathOnDisc = pathOnDisc + getFileName(uri);

		// Credentials
		byte[] encodedBytes = Base64.encodeBase64(Files.readAllBytes(Paths.get("/data/SFB/xnat_auth.txt")));
		String encoding = new String(encodedBytes);

		InputStream inputstream = null;
		OutputStream outputstream = null;
		try {

			System.out.println("request " + get.getRequestLine());
			get.setHeader("Authorization", "Basic " + encoding);
			HttpResponse response = client.execute(get);

			System.out.println("xxxxxxxxxxxxxxxx File-Download-Status xxxxxxxxxxxxxxxx");
			System.out.println(response.getStatusLine());

			File file = new File(pathOnDisc);

			inputstream = response.getEntity().getContent();
			outputstream = new FileOutputStream(file);

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputstream.read(bytes)) != -1) {
				outputstream.write(bytes, 0, read);
			}

			System.out.println("Done!");
			return pathOnDisc;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (inputstream != null) {
				try {
					inputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputstream != null) {
				try {
					// outputStream.flush();
					outputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
	}

	private static String downloadFileFromFreeOnlineResource(URI uri, String pathOnDisc) throws ClientProtocolException, IOException {

		HttpClientBuilder clientbuilder = HttpClientBuilder.create();
		HttpClient httpclient = clientbuilder.build();

		HttpGet get = new HttpGet(uri);
		HttpResponse getResponse = httpclient.execute(get);

		System.out.println("xxxxxxxxxxxxxxxx File-Download-Status xxxxxxxxxxxxxxxx");
		System.out.println(getResponse.getStatusLine());

		HttpEntity entity = getResponse.getEntity();

		pathOnDisc = pathOnDisc + getFileName(uri);

		if (entity != null) {
			InputStream inputStream = entity.getContent();

			FileOutputStream outputStream = new FileOutputStream(new File(pathOnDisc));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.close();
			inputStream.close();
		}
		System.out.println("Done!");
		return pathOnDisc;

	}

	private static ServletFileUpload getServletFileUpload() {
		// set up multipart stuff
		int MEMORY_THRESHOLD = 1024 * 1024 * 25; // 3MB
		int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
		int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);

		return upload;
	}

	public static BufferedReader getRDFInformation(HttpServletRequest request) throws IOException {
		boolean multipartFileUpload = ServletFileUpload.isMultipartContent(request);

		// Request - Processing
		BufferedReader input = null;

		if (request.getParameter("RequestInput") != null & !multipartFileUpload) {

			// No File-Upload
			// URL-Encoded request : must use parameter: "RequestInput"
			// RDF in URL parameter encoded
			// z.B. cURL url-encoded or JSP

			input = new BufferedReader(new StringReader(request.getParameter("RequestInput")));
			System.out.println("+++++++RDF-URL-Encoded-Request++++++++");
			System.out.println(request.getParameter("RequestInput"));
			return input;

		} else if (!multipartFileUpload) {

			// No File-Upload
			// RDF in body

			input = new BufferedReader(new StringReader(CharStreams.toString(request.getReader())));
			System.out.println("+++++++RDF-File-Upload-In-Body++++++++");
			return input;

		} else if (multipartFileUpload) {

			// File-Upload

			ServletFileUpload upload = getServletFileUpload();

			try {

				System.out.println("+++++++File-Upload++++++++");

				// Get all parameter items
				List<FileItem> formItems = upload.parseRequest(request);
				formItemsStore = formItems;

				for (FileItem item : formItems) {

					if (item.isFormField()) {
						// Process regular form fields).
						// Case: Files uploaded + RDF urlencoded! - must use:
						// "RequestInput" as parameter name

						String fieldname = item.getFieldName();
						String fieldvalue = item.getString();

						if (fieldname.equalsIgnoreCase("RequestInput")) {

							System.out.println("+++++++RDF-URL-Encoded & File Uploaded++++++++");
							input = new BufferedReader(new StringReader(fieldvalue));
							return input;
						}
					} else {
						// Process form file field (input type="file").
						// Case: Files uploaded + RDF uploaded via file

						String fieldname = item.getFieldName();

						if (fieldname.equalsIgnoreCase("RequestInput")) {

							InputStream filecontent = item.getInputStream();
							System.out.println("+++++++RDF-in-File & File-Upload++++++++");
							input = new BufferedReader(new InputStreamReader(filecontent));
							return input;
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		return input;
	}

	private static String getFileFromOnlineRessource(URI uri, String pathOnDisc) {

		String uriAsString = uri.toASCIIString();
		String pathOnlocalDisc;

		try {

			if (uriAsString.contains("xnat.sfb125.de")) {
				// Get file From XNAT ...

				pathOnlocalDisc = downloadFileFromXNAT(uri, pathOnDisc);

			} else {
				// Get file from other online resource

				pathOnlocalDisc = downloadFileFromFreeOnlineResource(uri, pathOnDisc);

			}
			return pathOnlocalDisc;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static String getFileFromPost(String URLParameter, String pathOnDisc, HttpServletRequest request) throws Exception {

		ServletFileUpload upload = getServletFileUpload();

		// Process the uploaded file items
		List fileItems = formItemsStore;
		Iterator i = fileItems.iterator();
		File uploadedFile;

		while (i.hasNext()) {

			FileItem fi = (FileItem) i.next();
			if (!fi.isFormField()) {

				if (fi.getFieldName().equalsIgnoreCase(URLParameter)) {

					// Get the uploaded file parameters
					String fieldName = fi.getFieldName();
					// System.out.println("1 : " + fieldName);
					String fileName = fi.getName();
					// System.out.println("2 : " + fileName);
					String contentType = fi.getContentType();
					// System.out.println("3 : " + contentType);
					boolean isInMemory = fi.isInMemory();
					// System.out.println("4 : " + isInMemory);
					long sizeInBytes = fi.getSize();
					// System.out.println("5 : " + sizeInBytes);

					// Write the file
					pathOnDisc = pathOnDisc + fileName;
					uploadedFile = new File(pathOnDisc);
					fi.write(uploadedFile);

					return pathOnDisc;

				}
			}
		}

		throw new Exception();

	}

	public static String getFileFromPostRequest(String inputvalue, String pathOnDisc, HttpServletRequest request) throws Exception {

		UrlValidator urlValidator = new UrlValidator();
		String pathOnLocalDisc = null;

		if (urlValidator.isValid(inputvalue)) {
			// Get File From Online Resource and store in "pathOnDisc"
			URI inputuri = new URI(inputvalue);
			pathOnLocalDisc = getFileFromOnlineRessource(inputuri, pathOnDisc);
		} else {
			// Get File From Post File Upload and store in "pathOnDisc"
			pathOnLocalDisc = getFileFromPost(inputvalue, pathOnDisc, request);
		}
		return pathOnLocalDisc;
	}

}
