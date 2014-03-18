package kit.sfb.cognitive.apps.xnat;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {

	public static void main(String[] args) throws URISyntaxException, IOException {
		// TODO Auto-generated method stub
		
		
		String downloadLink = moveFileToDownloadFolder("C:/Users/phiL/Desktop/b_mask.nrrd", "Cast");
		System.out.println(downloadLink);
		
		
	}
	
	
	public static String moveFileToDownloadFolder(String filePath, String webFolder) throws IOException {
		File test = new File(filePath);
		
		
		
		
		
		String path = "C:/Users/phiL/Desktop/tmp/" + test.getName();
		System.out.println("path: " + path);
		
		Path moveSourcePath = Paths.get(filePath);
		Path moveTargetPath = Paths.get(path);
		Files.move( moveSourcePath, moveTargetPath );
		
		
		return ("http://141.52.218.34:8080/downloads/" + webFolder + "/");
			
	
	}

}
