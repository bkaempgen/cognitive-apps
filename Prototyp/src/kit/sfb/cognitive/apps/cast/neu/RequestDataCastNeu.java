package kit.sfb.cognitive.apps.cast.neu;

public class RequestDataCastNeu {

	private String inputImage;
	private String salt;
	private String uriRequest;

	public RequestDataCastNeu(String inputImage, String salt, String uriRequest) {
		this.inputImage = inputImage;
		this.salt = salt;
		this.uriRequest = uriRequest;

	}

	public String getInputImage() {
		return inputImage;
	}

	public String getSalt() {
		return salt;
	}

	public String getRequestURI() {
		return uriRequest;
	}

}