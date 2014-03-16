<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
	function showDiv() {
		document.getElementById('example').style.display = "block";
	}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>StripTs</title>
</head>
<body>
	<div align="center">
	<form action="SFBServletStripTs#i" method="POST" enctype="multipart/form-data">
		<p><b>Request - Using File Upload:</b></p>
		<div>
			<textarea name="RequestInput" cols="80" rows="4"></textarea>
			<br/>
			<table>
				<tr>
					<td align="left"><b><u>file1:</u></b></td>
					<td align="left"><b><u>file2:</u></b></td>
					<td align="left"><b><u>file3:</u></b></td>
				</tr>
				<tr>
					<td><input type="file" name="file1" size="50" /></td>
					<td><input type="file" name="file2" size="50" /></td>
					<td><input type="file" name="file3" size="50" /></td>
				</tr>
			</table>
			<br/>
			<br/>
			<br/>
			<input type="submit" value="Post Request"/>
		</div>
	</form>
	</div>
	<br/>
	<br/>
	<div align="center">
		<input type="submit" value="Show Example?" onclick="showDiv();this.style.visibility= 'hidden';" />
	</div>
	<div align="center" id="example" style="display: none;" class="answer_list">
	<div align="center">Example:</div>
	<textarea name="Example1" cols="160" rows="20">
<rdf:RDF
    xmlns:lapis="http://localhost:8080/Prototyp/Ontology/Lapis#"
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:stripts="http://localhost:8080/Prototyp/StripTs/Ontology#"> 
  
<rdf:Description rdf:about="http://www.example.com/Request_01_Example">
    <rdf:type rdf:resource="http://localhost:8080/Prototyp/Ontology/Lapis#Request"/>
    <lapis:salt>11793854</lapis:salt>
	<stripts:hasInputBrainAtlasImage>file1</stripts:hasInputBrainAtlasImage>
	<stripts:hasInputBrainAtlasMask>file2</stripts:hasInputBrainAtlasMask>
	<stripts:hasInputImage>file3</stripts:hasInputImage>
</rdf:Description>
</rdf:RDF>
					</textarea>
	</div>
	<br/>
	<br/>
</body>
</html>