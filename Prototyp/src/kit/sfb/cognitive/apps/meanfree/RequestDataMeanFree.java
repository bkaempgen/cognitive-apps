package kit.sfb.cognitive.apps.meanfree;

public class RequestDataMeanFree {

	private String inputImage;
	private String inputMaskImage;
	private String salt;
	private String uriRequest;

	public RequestDataMeanFree(String inputImage, String inputMaskImage, String salt, String uriRequest) {
		this.inputImage = inputImage;
		this.inputMaskImage = inputMaskImage;
		this.salt = salt;
		this.uriRequest = uriRequest;

	}

	public String getInputImage() {
		return inputImage;
	}

	public String getInputMaskImage() {
		return inputMaskImage;
	}

	public String getSalt() {
		return salt;
	}

	public String getRequestURI() {
		return uriRequest;
	}

}
