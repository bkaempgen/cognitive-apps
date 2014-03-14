package kit.sfb.cognitive.apps.cast.neu;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RDImportHandlerCastNeu extends DefaultHandler {
	private final String NAMESPACE_URI = "http://localhost:8080/Prototyp/Cast/Ontology#";
	private final String NAMESPACE_URI_LAPIS = "http://localhost:8080/Prototyp/Ontology/Lapis#";
	private boolean error = false;
	private boolean hasInputImage = false;
	private boolean hasSalt = false;

	private String inputImage = null;
	private String salt = null;

	private String uriRequest = "";

	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
		if (localName.equalsIgnoreCase("hasInputImage") && namespaceURI.equals(NAMESPACE_URI))
			hasInputImage = true;
		if (localName.equalsIgnoreCase("salt") && namespaceURI.equals(NAMESPACE_URI_LAPIS))
			hasSalt = true;

		if (localName.equalsIgnoreCase("description") && !hasInputImage && !hasSalt) {
			// attribute req uri
			uriRequest = atts.getValue("rdf:about");
		}

	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equalsIgnoreCase("hasInputImage"))
			hasInputImage = false;
		if (localName.equalsIgnoreCase("salt"))
			hasSalt = false;
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		if (hasInputImage)
			inputImage = new String(ch, start, length);

		if (hasSalt)
			salt = new String(ch, start, length);

	}

	public String getInputImage() {
		return inputImage;
	}

	public String getSalt() {
		return salt;
	}

	public boolean getError() {
		return error;
	}

	public String getRequestURI() {
		return uriRequest;
	}
}