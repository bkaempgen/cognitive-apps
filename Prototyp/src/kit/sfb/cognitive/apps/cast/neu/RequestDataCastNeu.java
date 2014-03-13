package kit.sfb.cognitive.apps.cast.neu;

public class RequestDataCastNeu {

    private String inputImage;
    private String outputImagePath;
    private String uriRequest;

    public RequestDataCastNeu(String inputImage, String outputImagePath, String uriRequest) {
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