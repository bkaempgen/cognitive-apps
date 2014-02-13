package kit.edu;

public class RequestDataMeanFree {

	private String inputImage;
	private String inputMaskImage;
	private String outputImagePath;
	private String uriRequest;

	public RequestDataMeanFree(String inputImage, String inputMaskImage,
			String outputImagePath, String uriRequest) {
		this.inputImage = inputImage;
		this.inputMaskImage = inputMaskImage;
		this.outputImagePath = outputImagePath;
		this.uriRequest = uriRequest;

	}

	public String getInputImage() {
		return inputImage;
	}

	public String getInputMaskImage() {
		return inputMaskImage;
	}

	public String getOutputImagePath() {
		return outputImagePath;
	}

	public String getRequestURI() {
		return uriRequest;
	}

}
