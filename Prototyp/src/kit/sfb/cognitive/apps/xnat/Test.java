package kit.sfb.cognitive.apps.xnat;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.commons.validator.routines.UrlValidator;

import kit.sfb.cognitive.apps.pipeline.Pipeline;

public class Test {

	public static void main(String[] args) throws URISyntaxException, IOException {
		// TODO Auto-generated method stub

		//System.out.println(Uploader.uploadToProject("TP", "C:/Users/phiL/Desktop/test_upload3.xml"));
		// System.out.println(Uploader.uploadToSubject("TP", "sfb125_S00002",
		// "C:/Users/phiL/Desktop/test_upload3.xml"));
		// System.out.println(Uploader.uploadToExperiment("sfb125_E00004",
		// "C:/Users/phiL/Desktop/test_upload3.xml"));
		// URI uri = new
		// URI("https://xnat.sfb125.de/data/projects/TP/resources/2/files/test_upload2.xml");
		// Downloader.downloadFile(uri, "C:/Users/phiL/Desktop/tmp/test2.xml");
		
//		Pipeline.startPipeline();
		
		String uri = "http://141.52.218.34:8080/downloads/StripTs/26555008_result_image.nrrd";
		UrlValidator urlValidator = new UrlValidator();
		System.out.println(urlValidator.isValid(uri));
		
		
	}

}
