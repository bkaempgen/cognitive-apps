package kit.sfb.cognitive.apps.xnat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class Downloader {

	private OutputStream outputstream;

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

	// Download File from Xnat
	public static void downloadFile(URI uri, String pathOnDisc) throws IOException {

		DefaultHttpClient client = getNewHttpClient();
		HttpGet get = new HttpGet(uri);
		get.setHeader("Content-Type", "application/xml");

		// Credentials
		byte[] encodedBytes = Base64.encodeBase64(Files.readAllBytes(Paths.get("C:/Users/phiL/Desktop/xnat_auth.txt")));
		String encoding = new String(encodedBytes);

		InputStream inputstream = null;
		OutputStream outputstream = null;
		try {

			System.out.println("request " + get.getRequestLine());
			get.setHeader("Authorization", "Basic " + encoding);
			HttpResponse response = client.execute(get);

			System.out.println("xxxxxxxxxxxxxxxx Status xxxxxxxxxxxxxxxx");
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

		} catch (IOException e) {
			e.printStackTrace();
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
}
