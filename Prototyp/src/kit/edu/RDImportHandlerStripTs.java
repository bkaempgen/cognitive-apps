package kit.edu;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RDImportHandlerStripTs extends DefaultHandler {
	private final String NAMESPACE_URI = "http://localhost:8080/Prototyp/SFBServletStripTs#"; 
	private boolean error=false;
	private boolean hasInputBrainAtlasImage = false;
	private boolean hasInputBrainAtlasMask = false;
	private boolean hasInputImage = false;
	private boolean hasOutputMaskPath = false;
	private boolean hasOutputImagePath = false;
	


	private String inputBrainAtlasImage = null;
	private String inputBrainAtlasMask = null;
	private String inputImage = null;
	private String outputMaskPath = null;
	private String outputImagePath = null;

	
	private String uriRequest ="";
	
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
		if (localName.equalsIgnoreCase("hasInputBrainAtlasImage") && namespaceURI.equals(NAMESPACE_URI)) hasInputBrainAtlasImage = true;
		if (localName.equalsIgnoreCase("hasInputBrainAtlasMask") && namespaceURI.equals(NAMESPACE_URI)) hasInputBrainAtlasMask = true;
		if (localName.equalsIgnoreCase("hasInputImage") && namespaceURI.equals(NAMESPACE_URI)) hasInputImage = true;
		if (localName.equalsIgnoreCase("hasOutputMaskPath") && namespaceURI.equals(NAMESPACE_URI)) hasOutputMaskPath = true;
		if (localName.equalsIgnoreCase("hasOutputImagePath") && namespaceURI.equals(NAMESPACE_URI)) hasOutputImagePath = true;

		
		if(localName.equalsIgnoreCase("description") && !hasInputBrainAtlasImage && !hasInputBrainAtlasMask && !hasInputImage && !hasOutputMaskPath && !hasOutputImagePath){
			//attribute req uri
			uriRequest = atts.getValue("rdf:about");
		}

	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equalsIgnoreCase("hasInputBrainAtlasImage")) hasInputBrainAtlasImage = false;
		if (localName.equalsIgnoreCase("hasInputBrainAtlasMask")) hasInputBrainAtlasMask = false;
		if (localName.equalsIgnoreCase("hasInputImage")) hasInputImage = false;
		if (localName.equalsIgnoreCase("hasOutputMaskPath")) hasOutputMaskPath = false;
		if (localName.equalsIgnoreCase("hasOutputImagePath")) hasOutputImagePath = false;
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		if(hasInputBrainAtlasImage)
			inputBrainAtlasImage = new String(ch,start,length);
		
		if(hasInputBrainAtlasMask)
			inputBrainAtlasMask = new String(ch,start,length);
				
		if(hasInputImage)
			inputImage = new String(ch,start,length);
		
		if(hasOutputMaskPath)
			outputMaskPath = new String(ch,start,length);

		if(hasOutputImagePath)
			outputImagePath = new String(ch,start,length);
		
	}

	public String getInputBrainAtlasImage(){return inputBrainAtlasImage;}
	public String getInputBrainAtlasMask(){return inputBrainAtlasMask;}
	public String getInputImage(){return inputImage;}
	public String getOutputMaskPath(){return outputMaskPath;}
	public String getOutputImagePath(){return outputImagePath;}
	public boolean getError(){
		return error;
	}
	public String getRequestURI(){return uriRequest;}
}

