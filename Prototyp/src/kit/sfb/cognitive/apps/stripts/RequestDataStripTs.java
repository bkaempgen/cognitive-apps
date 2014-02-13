package kit.sfb.cognitive.apps.stripts;

public class RequestDataStripTs {

	private String inputBrainAtlasImage;
	private String inputBrainAtlasMask;
	private String inputImage;
	private String outputMaskPath;
	private String outputImagePath;
	private String uriRequest;

	public RequestDataStripTs(String inputBrainAtlasImage, String inputBrainAtlasMask, String inputImage, String outputMaskPath, String outputImagePath, String uriRequest) {
		this.inputBrainAtlasImage = inputBrainAtlasImage;
		this.inputBrainAtlasMask = inputBrainAtlasMask;
		this.inputImage = inputImage;
		this.outputMaskPath = outputMaskPath;
		this.outputImagePath = outputImagePath;
		this.uriRequest = uriRequest;

	}

	public String getInputBrainAtlasImage() {
		return inputBrainAtlasImage;
	}
	
	public String getInputBrainAtlasMask() {
		return inputBrainAtlasMask;
	}
		
	public String getInputImage() {
		return inputImage;
	}
	
	public String getOutputMaskPath() {
		return outputMaskPath;
	}
	
	public String getOutputImagePath() {
		return outputImagePath;
	}

	public String getRequestURI() {
		return uriRequest;
	}

	
	
	
}
