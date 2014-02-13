package kit.edu;

public class RequestDataCast {

	private String inputImage;
	private String outputImagePath;
	private String uriRequest;

	public RequestDataCast(String inputImage, String outputImagePath, String uriRequest) {
		this.inputImage = inputImage;
		this.outputImagePath = outputImagePath;
		this.uriRequest = uriRequest;

	}

	public String getInputImage() {
		return inputImage;
	}

	public String getOutputImagePath() {
		return outputImagePath;
	}

	public String getRequestURI() {
		return uriRequest;
	}

	
	
	
}
