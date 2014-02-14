package kit.sfb.cognitive.apps.meanfree;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RDImportHandlerMeanFree extends DefaultHandler {
	private final String NAMESPACE_URI = "http://localhost:8080/Prototyp/MeanFree/Ontology#";
	private boolean error = false;
	private boolean hasInputImage = false;
	private boolean hasInputMaskImage = false;
	private boolean hasOutputImagePath = false;

	private String inputImage = null;
	private String inputMaskImage = null;
	private String outputImagePath = null;

	private String uriRequest = "";

	public void startElement(String namespaceURI, String localName,
			String qName, Attributes atts) {
		if (localName.equalsIgnoreCase("hasInputImage")
				&& namespaceURI.equals(NAMESPACE_URI))
			hasInputImage = true;
		if (localName.equalsIgnoreCase("hasInputMaskImage")
				&& namespaceURI.equals(NAMESPACE_URI))
			hasInputMaskImage = true;
		if (localName.equalsIgnoreCase("hasOutputImagePath")
				&& namespaceURI.equals(NAMESPACE_URI))
			hasOutputImagePath = true;

		if (localName.equalsIgnoreCase("description") && !hasInputImage
				&& !hasInputMaskImage && !hasOutputImagePath) {
			// attribute req uri
			uriRequest = atts.getValue("rdf:about");
		}

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equalsIgnoreCase("hasInputImage"))
			hasInputImage = false;
		if (localName.equalsIgnoreCase("hasInputMaskImage"))
			hasInputMaskImage = false;
		if (localName.equalsIgnoreCase("hasOutputImagePath"))
			hasOutputImagePath = false;
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if (hasInputImage)
			inputImage = new String(ch, start, length);

		if (hasInputMaskImage)
			inputMaskImage = new String(ch, start, length);

		if (hasOutputImagePath)
			outputImagePath = new String(ch, start, length);

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

	public boolean getError() {
		return error;
	}

	public String getRequestURI() {
		return uriRequest;
	}
}
