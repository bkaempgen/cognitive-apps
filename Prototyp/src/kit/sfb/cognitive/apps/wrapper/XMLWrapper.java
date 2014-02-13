package kit.sfb.cognitive.apps.wrapper;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.javatuples.Triplet;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class XMLWrapper{
	
	String dir, name, ns, blank, dc, foaf, xsd;
	List<List<String>> results = new ArrayList<List<String>>();
	List<String> paraTypes, baseTypes, subParas, subsubParas, paraNames;
	PrintWriter writer;
	@SuppressWarnings("rawtypes")
	List<Triplet> dictionaryMapper = new ArrayList<Triplet>();
	
	String category = "category";
	String title = "title";
	String version = "version";
	String license = "license";
	String contributor = "contributor";
	String label = "label";
	String description = "description";
	String maximum = "maximum";
	String minimum = "minimum";
	String type = "type";
	String longflag = "longflag";
	String channel = "channel";
	String def = "default";
	String element = "element";
	String documentation_url = "documentation-url";
	String image = "image";
	String fl = "float";
	String integer = "integer";
	String file = "file";
	String integer_enumeration = "integer-enumeration";
	String bool = "boolean";
	String constraints = "constraints";
	
	String globalType = "";
	
	public XMLWrapper(String dir, String name) throws FileNotFoundException, UnsupportedEncodingException {
		this.dir = dir + "spec.xml";
		this.name = name;
		ns = "spec:";
		blank = " ";
		dc = "dc:";
		foaf = "foaf:";
		xsd = "xsd:";

		paraTypes = new ArrayList<String>();
		paraTypes.add(image);
		paraTypes.add(fl);
		paraTypes.add(integer);
		paraTypes.add(file);
		paraTypes.add(integer_enumeration);
		paraTypes.add(bool);
		
		baseTypes = new ArrayList<String>();
		baseTypes.add(category);
		baseTypes.add(version);
		baseTypes.add(title);
		baseTypes.add(description);
		baseTypes.add(documentation_url);
		baseTypes.add(license);
		baseTypes.add(contributor);
		
		subParas = new ArrayList<String>();
		subParas.add("name");
		subParas.add(description);
		subParas.add(label);
		subParas.add(def);
		subParas.add(longflag);
		subParas.add(channel);
		subParas.add(constraints);
		subParas.add(element);
		
		subsubParas = new ArrayList<String>();
		subsubParas.add(minimum);
		subsubParas.add(maximum);
		
		paraNames = new ArrayList<String>();
		
		mapXMLtoRDF();
		
		writer = new PrintWriter(dir + "spec_instance.ttl", "UTF-8");
		writer.println("@prefix spec:     <http://surgipedia.sfb125.de/spec#>.");
		writer.println("@prefix dc:       <http://purl.org/dc/elements/1.1/");
		writer.println("@prefix foaf:     <http://xmlns.com/foaf/0.1/>.");
		writer.println(name + " a spec:Algorithm.");		
	}
	
	public void mapXMLtoRDF() {
		Triplet<String,String,String> categoryTriplet = Triplet.with(category, ns + category, "String"); 
		Triplet<String,String,String> titleTriplet = Triplet.with(title, dc + title, "String");
		Triplet<String,String,String> versionTriplet = Triplet.with(version, ns + version, "String");
		Triplet<String,String,String> contributorTriplet = Triplet.with(contributor, ns + contributor, "String");
		Triplet<String,String,String> licenseTriplet = Triplet.with(license, ns + license, "String"); 
		Triplet<String,String,String> descriptionTriplet = Triplet.with(description, dc + description, "String");
		Triplet<String,String,String> labelTriplet = Triplet.with(label, ns + label, "String"); 
		Triplet<String,String,String> inputChannelTriplet = Triplet.with(channel, ns + channel, "String"); 
		Triplet<String,String,String> longFlagTriplet = Triplet.with(longflag, ns + longflag, "String"); 
		Triplet<String,String,String> typeTriplet = Triplet.with(type, xsd + type, "special"); 
		Triplet<String,String,String> elementTriplet = Triplet.with(element, ns + element, "xxx");
		Triplet<String,String,String> minimumTriplet = Triplet.with(minimum, ns + minimum, "xxx");
		Triplet<String,String,String> maximumTriplet = Triplet.with(maximum, ns + maximum, "xxx");
		Triplet<String,String,String> defTriplet = Triplet.with(def, ns + def, "xxx"); 
		Triplet<String,String,String> homePageTriplet = Triplet.with(documentation_url, foaf + "homepage", "String");
        
        // special meint, dass man irgendwie festlegen muss, dass der Typ hier entweder xsd:integer, xsd:float, ... ist.
		// xxx steht für kein String, aber der Typ selbst ist verschieden für die Parameter. Die Variable globalType wurde
		// als Hack eingeführt, um den jeweiligen Typ zu speichern. Falls file, dann müsste man vielleicht einen String
		// für den Pfad übergeben.
		
		dictionaryMapper.add(categoryTriplet);
		dictionaryMapper.add(titleTriplet);
		dictionaryMapper.add(versionTriplet);
		dictionaryMapper.add(licenseTriplet);
		dictionaryMapper.add(contributorTriplet);
		dictionaryMapper.add(descriptionTriplet);
		dictionaryMapper.add(labelTriplet);
		dictionaryMapper.add(inputChannelTriplet);
		dictionaryMapper.add(longFlagTriplet);
		dictionaryMapper.add(typeTriplet);
		dictionaryMapper.add(defTriplet);
		dictionaryMapper.add(elementTriplet);
		dictionaryMapper.add(minimumTriplet);
		dictionaryMapper.add(maximumTriplet);
		dictionaryMapper.add(homePageTriplet);
	}
	
	public void writeTTL() {
		for(List<String> r:results) {
			if(r.get(0).equals(name)) 
				writeLineNew(name, r.get(1), r.get(2));
			else 
				writeLineNew(paraNames.get(Integer.parseInt(r.get(0))), r.get(1), r.get(2));
		}
		writer.close();
	}
	
	@SuppressWarnings("rawtypes")
	public void writeLineNew(String rdfSubject, String xmlTag, String xmlValue) {
		for(Triplet triplet:dictionaryMapper) {
			if(xmlTag.equals(triplet.getValue0())) {
				writer.println(getTTLNew(rdfSubject, triplet.getValue1().toString(), xmlValue, triplet.getValue2().toString()));
				break;
			}
			else if(paraTypes.contains(xmlTag)) {
				writer.println(getTTLNew(rdfSubject, xsd + type, xmlTag, triplet.getValue2().toString()));
				globalType = xmlTag;
				break;
			}
		}
	}

	public String getTTLNew(String subject, String mode, String object, String type) {
		if(type.equals("String")) 
			return subject +blank +mode +blank +"\"" +object +"\"" +".";
		else
			return subject +blank +mode +blank +object +".";
	}
	
	public NodeList getContent(Element el, String xmlTag, String vis, String subjectForRDF) {
		NodeList list = el.getElementsByTagName(xmlTag);
		if(list.getLength() > 0) {
	        for(int i = 0; i < list.getLength(); i++) {
	        	Element elel = (Element)list.item(i);
		        NodeList text = elel.getChildNodes();
		        if(text.getLength() > 0) {
		        	String xmlValue = ((Node)text.item(0)).getNodeValue().trim(); 
			        if(xmlTag.equals("name")) 
			        	paraNames.add(xmlValue);
			        List<String> r = new ArrayList<String>();
			        r.add(subjectForRDF); 
			        r.add(xmlTag);
			        r.add(xmlValue);
			        results.add(r);
		        }
		        else {}
		        if(!xmlTag.equals("element"))
		        		break;
	        }
		}
        return list;
	}
		    
    public void readXML() {
    	try {
           DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
           DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
           Document doc = docBuilder.parse (dir);

            doc.getDocumentElement ().normalize ();
            NodeList bases = doc.getElementsByTagName("executable");

            for(int s=0; s<bases.getLength() ; s++){
                Node fBase = bases.item(s);
                if(fBase.getNodeType() == Node.ELEMENT_NODE){
                    Element fbaseElement = (Element)fBase;                
                    for(String base:baseTypes) {
	                    getContent(fbaseElement, base, "#### ", name);
                    }                                    
                    NodeList parameterList = getContent(fbaseElement, "parameters", "####", name);
                    
                    for(int t=0; t<parameterList.getLength() ; t++){
                        Node firstParaNode = parameterList.item(t);
                        if(firstParaNode.getNodeType() == Node.ELEMENT_NODE){
                            Element paraElement = (Element)firstParaNode;                         
                            for(String para:paraTypes) {
	                            NodeList paraTypeList = getContent(paraElement, para, "##", String.valueOf(t));
	                            
	                            for(int q=0; q<paraTypeList.getLength() ; q++){                            	
	                                Node subParaNode = paraTypeList.item(q);
	                                if(subParaNode.getNodeType() == Node.ELEMENT_NODE){
	                                    Element subParaEl = (Element)subParaNode;  
	                                    for(String subPara:subParas) {
	                                    	NodeList subparaTypeList = getContent(subParaEl, subPara, "-", String.valueOf(t));
	                                    	
	                                    	 for(int p=0; p<subparaTypeList.getLength() ; p++){                            	
	         	                                Node subsubParaNode = subparaTypeList.item(p);
	         	                                if(subParaNode.getNodeType() == Node.ELEMENT_NODE){
	         	                                    Element subsubParaEl = (Element)subsubParaNode;  
	         	                                    for(String subsubPara:subsubParas) {
	         	                                    	getContent(subsubParaEl, subsubPara, "!", String.valueOf(t));
	         	                                    }
	         		                            }
	         	                            }
	                                    }
		                            }
	                            }
                            }                         
                        }
                    }
                }
            }
        }catch (SAXParseException err) {
        System.out.println ("** Parsing error" + ", line " 
             + err.getLineNumber () + ", uri " + err.getSystemId ());
        System.out.println(" " + err.getMessage ());

        }catch (SAXException e) {
        Exception x = e.getException ();
        ((x == null) ? e : x).printStackTrace ();

        }catch (Throwable t) {
        t.printStackTrace ();
        }
    }
}