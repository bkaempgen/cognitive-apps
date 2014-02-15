package kit.sfb.cognitive.apps.xnat;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import org.apache.commons.codec.binary.Base64;
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

	//HACK wegen SSL
	//Source: http://stackoverflow.com/questions/2642777/trusting-all-certificates-using-httpclient-over-https
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

	//Download File from Xnat
	public static File downloadFile(URI uri) throws IOException {

		DefaultHttpClient client = getNewHttpClient();
		HttpGet get = new HttpGet(uri);
		get.setHeader("Content-Type", "application/xml");

		// Credentials
		byte[] encodedBytes = Base64.encodeBase64(Files.readAllBytes(Paths.get("C:/Users/phiL/Desktop/xnat_auth.txt")));
		String encoding = new String(encodedBytes);

		try {

			System.out.println("request " + get.getRequestLine());
			get.setHeader("Authorization", "Basic " + encoding);
			HttpResponse response = client.execute(get);

			System.out.println(response.getStatusLine());

			BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			StringBuilder total = new StringBuilder();
			String line = null;
			while ((line = r.readLine()) != null) {
				total.append(line + '\n');
			}
			System.out.println(total.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new File("test");
	}

}
