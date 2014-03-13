package kit.sfb.cognitive.apps.cast;

public class RequestDataCast {

	private String uriRequest;

	private String uriInputImage;
	private String uriOutputImagePath;

	private String inputImageType;
	private String outputImagePathType;

	private String inputImageParameterName;
	private String inputImageXNATIdentifier;

	private String outputImagePathParameterName;
	private String outputImagePathXNATIdentifier;

	public RequestDataCast(String uriRequest, String inputImageType, String inputImageParameterName, String inputImageXNATIdentifier,
			String outputImagePathType, String outputImagePathParameterName, String outputImagePathXNATIdentifier, String uriInputImage,
			String uriOutputImagePath) {

		this.uriRequest = uriRequest;

		this.inputImageType = inputImageType;
		this.inputImageParameterName = inputImageParameterName;
		this.inputImageXNATIdentifier = inputImageXNATIdentifier;

		this.outputImagePathType = outputImagePathType;
		this.outputImagePathParameterName = outputImagePathParameterName;
		this.outputImagePathXNATIdentifier = outputImagePathXNATIdentifier;

		this.uriInputImage = uriInputImage;
		this.uriOutputImagePath = uriOutputImagePath;

	}

	public String getRequestURI() {
		return uriRequest;
	}

	public String getInputImageType() {
		return inputImageType;
	}

	public String getInputImageParameterName() {
		return inputImageParameterName;
	}

	public String getInputImageXNATIdentifier() {
		return inputImageXNATIdentifier;
	}

	public String getOutputImagePathType() {
		return outputImagePathType;
	}

	public String getOutputImagePathParameterName() {
		return outputImagePathParameterName;
	}

	public String getOutputImagePathXNATIdentifier() {
		return outputImagePathXNATIdentifier;
	}

	public String getInputImageURI() {
		return uriInputImage;
	}

	public String getOutputImagePathURI() {
		return uriOutputImagePath;
	}
}
