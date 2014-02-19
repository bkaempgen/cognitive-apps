package kit.sfb.cognitive.apps.calculator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RDImportHandler extends DefaultHandler {
	private final String NAMESPACE_URI = "http://141.52.218.34:8080/Prototyp/CalcServlet#"; 
	private boolean error=false;
	private boolean hasFirstAddend = false;
	private boolean hasSecondAddend = false;


	
	private int firstAddend = -1;
	private int secondAddend = -1;

	
	private String uriRequest ="";
	
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
		if (localName.equalsIgnoreCase("hasFirstAddend") && namespaceURI.equals(NAMESPACE_URI)) hasFirstAddend = true;
		if (localName.equalsIgnoreCase("hasSecondAddend") && namespaceURI.equals(NAMESPACE_URI)) hasSecondAddend = true;

		
		if(localName.equalsIgnoreCase("description") && !hasSecondAddend && !hasFirstAddend){
			//attribute req uri
			uriRequest = atts.getValue("rdf:about");
		}

	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.equalsIgnoreCase("hasFirstAddend")) hasFirstAddend = false;
		if (localName.equalsIgnoreCase("hasSecondAddend")) hasSecondAddend = false;
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		if(hasFirstAddend)
			firstAddend = Integer.parseInt(new String(ch,start,length));

		if(hasSecondAddend)
			secondAddend = Integer.parseInt(new String(ch,start,length));
		
	}

	public int getFirstAddend(){return firstAddend;}
	public int getSecondAddend(){return secondAddend;}
	public boolean getError(){
		return error;
	}
	public String getRequestURI(){return uriRequest;}
}

