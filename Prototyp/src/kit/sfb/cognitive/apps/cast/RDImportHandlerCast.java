package kit.sfb.cognitive.apps.cast;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RDImportHandlerCast extends DefaultHandler {
	private final String NAMESPACE_URI = "http://localhost:8080/Prototyp/Cast/Ontology#";
	private final String NAMESPACE_URI_LAPIS = "http://localhost:8080/Prototyp/Ontology/Lapis#";
	private boolean error = false;

	private boolean hasInputImage = false;
	private boolean hasOutputImagePath = false;

	private String uriRequest = "";
	private String uriInputImage = "";
	private String uriOutputImagePath = "";

	private boolean hasInputImageParameterName = false;
	private boolean hasInputImageXNATIdentifier = false;

	private boolean hasOutputImagePathParameterName = false;
	private boolean hasOutputImagePathXNATIdentifier = false;

	private String inputImageType = null;
	private String outputImagePathType = null;

	private String inputImageParameterName = null;
	private String inputImageXNATIdentifier = null;

	private String outputImagePathParameterName = null;
	private String outputImagePathXNATIdentifier = null;

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {

		// hasInputImage
		if (localName.equalsIgnoreCase("hasInputImage") && namespaceURI.equals(NAMESPACE_URI))
			hasInputImage = true;

		if (localName.equalsIgnoreCase("description") && hasInputImage) {
			uriInputImage = atts.getValue("rdf:about");
		}

		if (localName.equalsIgnoreCase("kindOfProvision") && namespaceURI.equals(NAMESPACE_URI_LAPIS) && hasInputImage) {
			inputImageType = atts.getValue("rdf:resource");
		}

		if (localName.equalsIgnoreCase("hasParameterName") && namespaceURI.equals(NAMESPACE_URI_LAPIS) && hasInputImage) {
			hasInputImageParameterName = true;
		}

		if (localName.equalsIgnoreCase("xnatIdentifier") && namespaceURI.equals(NAMESPACE_URI_LAPIS) && hasInputImage) {
			hasInputImageXNATIdentifier = true;
		}

		// hasOutputImagePath
		if (localName.equalsIgnoreCase("hasOutputImagePath") && namespaceURI.equals(NAMESPACE_URI))
			hasOutputImagePath = true;

		if (localName.equalsIgnoreCase("description") && hasOutputImagePath) {
			uriOutputImagePath = atts.getValue("rdf:about");
		}

		if (localName.equalsIgnoreCase("kindOfProvision") && namespaceURI.equals(NAMESPACE_URI_LAPIS) && hasOutputImagePath) {
			outputImagePathType = atts.getValue("rdf:resource");
		}

		if (localName.equalsIgnoreCase("hasParameterName") && namespaceURI.equals(NAMESPACE_URI_LAPIS) && hasOutputImagePath) {
			hasOutputImagePathParameterName = true;
		}

		if (localName.equalsIgnoreCase("xnatIdentifier") && namespaceURI.equals(NAMESPACE_URI_LAPIS) && hasOutputImagePath) {
			hasOutputImagePathXNATIdentifier = true;
		}

		// Request-URI
		if (localName.equalsIgnoreCase("description") && !hasInputImage && !hasOutputImagePath) {
			// attribute req uri
			uriRequest = atts.getValue("rdf:about");
		}

	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equalsIgnoreCase("hasInputImage"))
			hasInputImage = false;
		if (localName.equalsIgnoreCase("hasParameterName") && hasInputImage)
			hasInputImageParameterName = false;
		if (localName.equalsIgnoreCase("xnatIdentifier") && hasInputImage)
			hasInputImageXNATIdentifier = false;

		if (localName.equalsIgnoreCase("hasOutputImagePath"))
			hasOutputImagePath = false;
		if (localName.equalsIgnoreCase("hasParameterName") && hasOutputImagePath)
			hasOutputImagePathParameterName = false;
		if (localName.equalsIgnoreCase("xnatIdentifier") && hasOutputImagePath)
			hasOutputImagePathXNATIdentifier = false;

	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		if (hasInputImageParameterName) {
			inputImageParameterName = new String(ch, start, length);
		}

		if (hasInputImageXNATIdentifier) {
			inputImageXNATIdentifier = new String(ch, start, length);
		}

		if (hasOutputImagePathParameterName) {
			outputImagePathParameterName = new String(ch, start, length);
		}

		if (hasOutputImagePathXNATIdentifier) {
			outputImagePathXNATIdentifier = new String(ch, start, length);
		}

	}

	public boolean getError() {
		return error;
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
