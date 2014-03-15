package kit.sfb.cognitive.apps.stripts.neu;

public class RequestDataStripTsNeu {

	private String inputBrainAtlasImage;
	private String inputBrainAtlasMask;
	private String inputImage;
	private String salt;
	private String uriRequest;

	public RequestDataStripTsNeu(String inputBrainAtlasImage, String inputBrainAtlasMask, String inputImage, String salt, String uriRequest) {
		this.inputBrainAtlasImage = inputBrainAtlasImage;
		this.inputBrainAtlasMask = inputBrainAtlasMask;
		this.inputImage = inputImage;
		this.salt = salt;
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

	public String getSalt() {
		return salt;
	}

	public String getRequestURI() {
		return uriRequest;
	}

}
