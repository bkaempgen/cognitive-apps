package wrapper;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class XMLWrapperTest {

	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub

		XMLWrapper wrapper = new XMLWrapper("//psf/Home/Dropbox/InwiMaster PG/Masterthesis/Themen/Cognition-guided Surgery/Files/", "tst");
		
		wrapper.readXML();
		wrapper.writeTTL();
		
	}

}
