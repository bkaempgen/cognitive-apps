package kit.sfb.cognitive.apps.cast.neu;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RDImportHandlerCastNeu extends DefaultHandler {
        private final String NAMESPACE_URI = "http://localhost:8080/Prototyp/Cast/Ontology#"; 
        private boolean error=false;
        private boolean hasInputImage = false;
        private boolean hasOutputImagePath = false;


        
        private String inputImage = null;
        private String outputImagePath = null;

        
        private String uriRequest ="";
        
        
        public void startElement(String namespaceURI, String localName, String qName, Attributes atts) {
                if (localName.equalsIgnoreCase("hasInputImage") && namespaceURI.equals(NAMESPACE_URI)) hasInputImage = true;
                if (localName.equalsIgnoreCase("hasOutputImagePath") && namespaceURI.equals(NAMESPACE_URI)) hasOutputImagePath = true;

                
                if(localName.equalsIgnoreCase("description") && !hasInputImage && !hasOutputImagePath){
                        //attribute req uri
                        uriRequest = atts.getValue("rdf:about");
                }

        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
                if (localName.equalsIgnoreCase("hasInputImage")) hasInputImage = false;
                if (localName.equalsIgnoreCase("hasOutputImagePath")) hasOutputImagePath = false;
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
                if(hasInputImage)
                        inputImage = new String(ch,start,length);

                if(hasOutputImagePath)
                        outputImagePath = new String(ch,start,length);
                
        }

        public String getInputImage(){return inputImage;}
        public String getOutputImagePath(){return outputImagePath;}
        public boolean getError(){
                return error;
        }
        public String getRequestURI(){return uriRequest;}
}