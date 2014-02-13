package kit.sfb.cognitive.apps.helper;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

	// Method for CDL-Execution
	@SuppressWarnings("deprecation")
	public static String RunCommandLineTool(String servicename,
			List<String> parameters) throws IOException {

		String mitkCommandLine = "F:/KIT/Master/Masterthesis - MITK/SFB-Files/MITK-2013.09.00-win64/MITK-2013.09.00-win64/bin/mitkBrainStrippingMiniApps.exe";
		String command = mitkCommandLine + " " + servicename;

		// Build command from parameters
		for (String p : parameters) {
			command = command + " " + p;
		}

		// Initiate Runtime
		Runtime runtime = Runtime.getRuntime();
		Process process = null;
		String result = null;
		String outputpath = null;

		try {
			process = runtime.exec(command);
			DataInputStream in = new DataInputStream(process.getInputStream());

			// READ AND PRINT THE OUTPUT
			String line = null;
			int outputcounter = 0;
			while ((line = in.readLine()) != null) {
				System.out.println(line);

				// Extract output path
				if (line.contains("core.io.imgWriter: Writing image:")) {
					Pattern myPattern = Pattern
							.compile(".*(core.io.imgWriter: Writing image: )(.*)");
					Matcher m = myPattern.matcher(line);
					if (m.matches()) {

						if (outputcounter == 0) {
							outputpath = m.group(2);
							result = "Success! Download the file(s) from: "
									+ outputpath;
							outputcounter++;
						} else {
							outputpath = m.group(2);
							result = result + " " + "," + " " + outputpath;
						}

					}
				}

			}

		} catch (Exception e) {
			System.out.println("Problem");
			result = "Failure! Please check Request!";
			return result;
		}

		return result;
	}

}
