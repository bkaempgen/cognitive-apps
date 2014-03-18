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
<title>MeanFree</title>
</head>
<body>
	<div align="center">
	<form action="SFBServletMeanFree#i" method="POST" enctype="multipart/form-data">
		<p><b>Request - Using File Upload:</b></p>
		<div>
			<textarea name="RequestInput" cols="80" rows="4"></textarea>
			<br />
			<table>
				<tr>
					<td align="left"><b><u>file1:</u></b></td>
					<td align="left"><b><u>file2:</u></b></td>
				</tr>
				<tr>
					<td><input type="file" name="file1" size="50" /></td>
					<td><input type="file" name="file2" size="50" /></td>
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
	<textarea name="Example1" cols="140" rows="15">
<rdf:RDF
    xmlns:lapis="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#"
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:meanfree="http://141.52.218.34:8080/Prototyp/MeanFree/Ontology#"> 
  
<rdf:Description rdf:about="http://www.example.com/Request_01_Example">
    <rdf:type rdf:resource="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request"/>
    <lapis:salt>53078053</lapis:salt>
	<meanfree:hasInputImage>file1</meanfree:hasInputImage>
	<meanfree:hasInputMaskImage>none</meanfree:hasInputMaskImage>
</rdf:Description>
</rdf:RDF>
					</textarea>
	</div>
	<br/>
	<br/>
</body>
</html>