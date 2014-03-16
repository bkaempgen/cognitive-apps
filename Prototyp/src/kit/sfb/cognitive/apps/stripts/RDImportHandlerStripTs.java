package kit.sfb.cognitive.apps.stripts;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RDImportHandlerStripTs extends DefaultHandler {
	private final String NAMESPACE_URI = "http://localhost:8080/Prototyp/StripTs/Ontology#"; 
	private final String NAMESPACE_URI_LAPIS = "http://localhost:8080/Prototyp/Ontology/Lapis#";
	private boolean error=false;
	private boolean hasInputBrainAtlasImage = false;
	private boolean hasInputBrainAtlasMask = false;
	private boolean hasInputImage = false;
	private boolean hasSalt = false;
	


	private String inputBrainAtlasImage = null;
	private String inputBrainAtlasMask = null;
	private String inputImage = null;
	private String salt = null;

	
	private String uriRequest ="";
	
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
		if (localName.equalsIgnoreCase("hasInputBrainAtlasImage") && namespaceURI.equals(NAMESPACE_URI)) hasInputBrainAtlasImage = true;
		if (localName.equalsIgnoreCase("hasInputBrainAtlasMask") && namespaceURI.equals(NAMESPACE_URI)) hasInputBrainAtlasMask = true;
		if (localName.equalsIgnoreCase("hasInputImage") && namespaceURI.equals(NAMESPACE_URI)) hasInputImage = true;
		if (localName.equalsIgnoreCase("salt") && namespaceURI.equals(NAMESPACE_URI_LAPIS)) hasSalt = true;

		
		if(localName.equalsIgnoreCase("description") && !hasInputBrainAtlasImage && !hasInputBrainAtlasMask && !hasInputImage && !hasSalt){
			//attribute req uri
			uriRequest = atts.getValue("rdf:about");
		}

	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equalsIgnoreCase("hasInputBrainAtlasImage")) hasInputBrainAtlasImage = false;
		if (localName.equalsIgnoreCase("hasInputBrainAtlasMask")) hasInputBrainAtlasMask = false;
		if (localName.equalsIgnoreCase("hasInputImage")) hasInputImage = false;
		if (localName.equalsIgnoreCase("salt")) hasSalt = false;
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		if(hasInputBrainAtlasImage)
			inputBrainAtlasImage = new String(ch,start,length);
		
		if(hasInputBrainAtlasMask)
			inputBrainAtlasMask = new String(ch,start,length);
				
		if(hasInputImage)
			inputImage = new String(ch,start,length);
		
		if(hasSalt)
			salt = new String(ch,start,length);

		
	}

	public String getInputBrainAtlasImage(){return inputBrainAtlasImage;}
	public String getInputBrainAtlasMask(){return inputBrainAtlasMask;}
	public String getInputImage(){return inputImage;}
	public String getSalt(){return salt;}
	public boolean getError(){
		return error;
	}
	public String getRequestURI(){return uriRequest;}
}

