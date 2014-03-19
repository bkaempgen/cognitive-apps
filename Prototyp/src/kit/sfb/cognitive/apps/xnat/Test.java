package kit.sfb.cognitive.apps.xnat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kit.sfb.cognitive.apps.helper.Helper;

public class Test {

	public static void main(String[] args) throws URISyntaxException, IOException {
		
//		File file = new File("C:/Users/phiL/Desktop/tmp/test.txt");
//		long diff = new Date().getTime() - file.lastModified();
//		System.out.println(file.lastModified());
//		System.out.println(new Date().getTime());
//		System.out.println(diff);
//		int min_threshold = 4;
//		
//		//* 1000 = s * 60 = min * 60 = h 
//
//		if (diff > min_threshold * 60 * 1000) {
//		    file.delete();
//		}
		
		
		String folder = Helper.getProperties("tomcat_downloads");
		
		String castFolder 		= folder + "Cast/";
		String striptsFolder 	= folder + "StripTs/";
		String meanfreeFolder 	= folder + "MeanFree/";
		
		List<String> folders = new ArrayList<String>();
		folders.add(castFolder);
		folders.add(striptsFolder);
		folders.add(meanfreeFolder);
		
		for (String s : folders){
			System.out.println(s);
		}
		
	}
}