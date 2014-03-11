<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mockup</title>
</head>
<script type="text/javascript">
function showDiv() {
	   document.getElementById('requests').style.display = "block";
	}
</script>
<style>
textarea {
    resize: none;
}
</style>
<body>
	<h1 align="center">Mockup</h1>
	<div align="center"><b>Hinweis:</b><br/>
	Pipeline läuft momentan ohne XNAT-Anbindung, d.h. Input-Files liegen auf Filesystem des Web Servers und Zwischen- bzw. Endergebnisse werden dort gepspeichert.<br/><br/>
	<div align="center"><b>Input:</b><br/>
	 </div>
	<table align="center">
		<tr>
			<td align="center">T1.nrrd</td>
			<td align="center">atlasImage.mha</td>
			<td align="center">atlasMask.mha</td>
		</tr> 
		<tr>
			<td>
				<div>
					<img src="Mockup/T1_input.PNG" width="150" height="130"/>
				</div>
			</td>
			<td>
				<div>
					<img src="Mockup/atlasImage_input.PNG" width="150" height="130"/>
				</div>
			</td>
			<td>
				<div>
					<img src="Mockup/atlasMask_input.PNG" width="150" height="130"/>
				</div>
			</td>
		</tr> 
	</table> 
	 <br/>
	 <br/>
	 <br/>
	<form action="MockupServlet" method="POST">
		<div align="center">
			<input type="submit" value="Start"
				onclick="document.getElementById('loader').style.display = 'block';showDiv();this.style.visibility= 'hidden';">
			<img id="loader" src="Mockup/loader.gif" style="display: none;" />
		</div>
	</form>
	<br/>
	<br/>
	<div id="requests" style="display: none;" class="answer_list">
	<!-- Kommentar: Request 1 -->	
	<div id="request1">
		<table align ="center">
			<tr>
				<td><b>Request 1 - Post to <a href="http://141.52.218.34:8080/Prototyp/SFBServletCast#i">http://141.52.218.34:8080/Prototyp/SFBServletCast#i</a>:</b> Cast atlasImage.mha to atlasImage.nrrd</td>
				<td><b>Response 1:</b></td>
			</tr>
			<tr>
				<td>
					<textarea name="RequestInput" cols="115" rows="12">
<rdf:RDF 
xmlns:lapis="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#" 
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
xmlns:cast="http://141.52.218.34:8080/Prototyp/Cast/Ontology#">
<rdf:Description rdf:about="http://www.example.com/Request_01_Example">
<rdf:type rdf:resource="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request"/>
<cast:hasInputImage>https://xnat.sfb125.de/data/projects/TP/resources/2/files/atlasImage.mha</cast:hasInputImage>
<cast:hasOutputImagePath>XNAT?project_id=TP&name=atlasImage.nrrd</cast:hasOutputImagePath>
</rdf:Description>
</rdf:RDF>
					</textarea>
				</td>
				<td>
					<textarea name="RequestInput" cols="115" rows="12">
<rdf:RDF 
xmlns:lapis="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#" 
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
xmlns:cast="http://141.52.218.34:8080/Prototyp/Cast/Ontology#">
<rdf:Description rdf:about="http://www.example.com/Request_01_Example">
<rdf:type rdf:resource="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request"/>
<cast:hasInputImage>https://xnat.sfb125.de/data/projects/TP/resources/2/files/atlasImage.mha</cast:hasInputImage>
<cast:hasOutputImagePath>XNAT?project_id=TP&name=atlasImage.nrrd</cast:hasOutputImagePath>
<lapis:hasResult>https://xnat.sfb125.de/data/projects/TP/resources/2/files/atlasImage.nrrd</lapis:hasResult>
</rdf:Description>
</rdf:RDF>
					</textarea>
				</td>
		</table>
	</div>
<!-- Kommentar: Request 2 -->	
	<div id="request2">
		<table align ="center">
			<tr>
				<td><b>Request 2 - Post to <a href="http://141.52.218.34:8080/Prototyp/SFBServletCast#i">http://141.52.218.34:8080/Prototyp/SFBServletCast#i</a>:</b> Cast atlasMask.mha to atlasMask.mha.nrrd</td>
				<td><b>Response 2:</b></td>
			</tr>
			<tr>
				<td>
					<textarea name="RequestInput" cols="115" rows="12">
<rdf:RDF 
xmlns:lapis="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#" 
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
xmlns:cast="http://141.52.218.34:8080/Prototyp/Cast/Ontology#">
<rdf:Description rdf:about="http://www.example.com/Request_02_Example">
<rdf:type rdf:resource="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request"/>
<cast:hasInputImage>https://xnat.sfb125.de/data/projects/TP/resources/2/files/atlasMask.mha</cast:hasInputImage>
<cast:hasOutputImagePath>XNAT?project_id=TP&name=atlasMask.nrrd</cast:hasOutputImagePath>
</rdf:Description>
</rdf:RDF>
					</textarea>
				</td>
				<td>
					<textarea name="RequestInput" cols="115" rows="12">
<rdf:RDF 
xmlns:lapis="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#" 
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
xmlns:cast="http://141.52.218.34:8080/Prototyp/Cast/Ontology#">
<rdf:Description rdf:about="http://www.example.com/Request_02_Example">
<rdf:type rdf:resource="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request"/>
<cast:hasInputImage>https://xnat.sfb125.de/data/projects/TP/resources/2/files/atlasMask.mha</cast:hasInputImage>
<cast:hasOutputImagePath>XNAT?project_id=TP&name=atlasMask.nrrd</cast:hasOutputImagePath>
<lapis:hasResult>https://xnat.sfb125.de/data/projects/TP/resources/2/files/atlasMask.nrrd</lapis:hasResult>
</rdf:Description>
</rdf:RDF>
					</textarea>
				</td>
		</table>
	</div>
<!-- Kommentar: Request 3 -->	
	<div id="request3">
		<table align ="center">
			<tr>
				<td><b>Request 3 - Post to <a href="http://141.52.218.34:8080/Prototyp/SFBServletStripTs#i">http://141.52.218.34:8080/Prototyp/SFBServletStripTs#i</a>:</b> Brain segmentation of T1.nrrd using atlasImage.nrrd, atlasMask.nrrd</td>
				<td><b>Response 3:</b></td>
			</tr>
			<tr>
				<td>
					<textarea name="RequestInput" cols="115" rows="22">
<rdf:RDF 
xmlns:lapis="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#" 
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
xmlns:stripts="http://141.52.218.34:8080/Prototyp/StripTs/Ontology#">
<rdf:Description rdf:about="http://www.example.com/Request_03_Example">
<rdf:type rdf:resource="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request"/>
<stripts:hasInputBrainAtlasImage>
	https://xnat.sfb125.de/data/projects/TP/resources/2/files/atlasImage.nrrd
</stripts:hasInputBrainAtlasImage>
<stripts:hasInputBrainAtlasMask>
	https://xnat.sfb125.de/data/projects/TP/resources/2/files/atlasMask.nrrd
</stripts:hasInputBrainAtlasMask>
<stripts:hasInputImage>
	https://xnat.sfb125.de/data/projects/TP/resources/2/files/T1.nrrd
</stripts:hasInputImage>
<stripts:hasOutputMaskPath>XNAT?project_id=TP&name=stripped_t1_mask.nrrd</stripts:hasOutputMaskPath>
<stripts:hasOutputImagePath>XNAT?project_id=TP&name=stripped_t1_image.nrrd</stripts:hasOutputImagePath>
</rdf:Description>
</rdf:RDF>
					</textarea>
				</td>
				<td>
					<textarea name="RequestInput" cols="115" rows="22">
<rdf:RDF 
xmlns:lapis="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#" 
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
xmlns:stripts="http://141.52.218.34:8080/Prototyp/StripTs/Ontology#">
<rdf:Description rdf:about="http://www.example.com/Request_03_Example">
<rdf:type rdf:resource="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request"/>
<stripts:hasInputBrainAtlasImage>
	https://xnat.sfb125.de/data/projects/TP/resources/2/files/atlasImage.nrrd
</stripts:hasInputBrainAtlasImage>
<stripts:hasInputBrainAtlasMask>
	https://xnat.sfb125.de/data/projects/TP/resources/2/files/atlasMask.nrrd
</stripts:hasInputBrainAtlasMask>
<stripts:hasInputImage>
	https://xnat.sfb125.de/data/projects/TP/resources/2/files/T1.nrrd
</stripts:hasInputImage>
<stripts:hasOutputMaskPath>XNAT?project_id=TP&name=stripped_t1_mask.nrrd</stripts:hasOutputMaskPath>
<stripts:hasOutputImagePath>XNAT?project_id=TP&name=stripped_t1_image.nrrd</stripts:hasOutputImagePath>
<lapis:hasResult>https://xnat.sfb125.de/data/projects/TP/resources/2/files/stripped_t1_mask.nrrd</lapis:hasResult>
<lapis:hasResult>https://xnat.sfb125.de/data/projects/TP/resources/2/files/stripped_t1_image.nrrd</lapis:hasResult>
</rdf:Description>
</rdf:RDF>
					</textarea>
				</td>
		</table>
	</div>
<!-- Kommentar: Request 4 -->	
	<div id="request4">
		<table align ="center">
			<tr>
				<td><b>Request 4 - Post to <a href="http://141.52.218.34:8080/Prototyp/SFBServletMeanFree#i">http://141.52.218.34:8080/Prototyp/SFBServletMeanFree#i</a>:</b> Normalization of tissue.</td>
				<td><b>Response 4:</b></td>
			</tr>
			<tr>
				<td>
					<textarea name="RequestInput" cols="115" rows="19">
<rdf:RDF 
xmlns:lapis="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#" 
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
xmlns:meanfree="http://141.52.218.34:8080/Prototyp/MeanFree/Ontology#">
<rdf:Description rdf:about="http://www.example.com/Request_04_Example">
<rdf:type rdf:resource="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request"/>
<meanfree:hasInputImage>
	https://xnat.sfb125.de/data/projects/TP/resources/2/files/stripped_t1_image.nrrd
</meanfree:hasInputImage>
<meanfree:hasInputMaskImage>
	https://xnat.sfb125.de/data/projects/TP/resources/2/files/stripped_t1_mask.nrrd
</meanfree:hasInputMaskImage>
<meanfree:hasOutputImagePath>XNAT?project_id=TP&name=meanfree_t1_normalized_image.nrrd</meanfree:hasOutputImagePath>
</rdf:Description>
</rdf:RDF>
					</textarea>
				</td>
				<td>
					<textarea name="RequestInput" cols="115" rows="19">
<rdf:RDF 
xmlns:lapis="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#" 
xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#" 
xmlns:meanfree="http://141.52.218.34:8080/Prototyp/MeanFree/Ontology#">
<rdf:Description rdf:about="http://www.example.com/Request_04_Example">
<rdf:type rdf:resource="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request"/>
<meanfree:hasInputImage>
	https://xnat.sfb125.de/data/projects/TP/resources/2/files/stripped_t1_image.nrrd
</meanfree:hasInputImage>
<meanfree:hasInputMaskImage>
	https://xnat.sfb125.de/data/projects/TP/resources/2/files/stripped_t1_mask.nrrd
</meanfree:hasInputMaskImage>
<meanfree:hasOutputImagePath>XNAT?project_id=TP&name=meanfree_t1_normalized_image.nrrd</meanfree:hasOutputImagePath>
<lapis:hasResult>https://xnat.sfb125.de/data/projects/TP/resources/2/files/meanfree_t1_normalized_image.nrrd</lapis:hasResult>
</rdf:Description>
</rdf:RDF>
					</textarea>
				</td>
		</table>
	</div>
	</div>


</body>
</html>