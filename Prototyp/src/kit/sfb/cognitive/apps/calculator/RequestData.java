package kit.sfb.cognitive.apps.calculator;

public class RequestData {

	private int firstAddend;
	private int secondAddend;
	private String uriRequest;

	public RequestData(int firstAddend, int secondAddend, String uriRequest) {
		this.firstAddend = firstAddend;
		this.secondAddend = secondAddend;
		this.uriRequest = uriRequest;

	}

	public int getFirstAddend() {
		return firstAddend;
	}

	public int getSecondAddend() {
		return secondAddend;
	}

	public String getRequestURI() {
		return uriRequest;
	}

}
