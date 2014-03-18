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
<style type="text/css">
<!--
#box {
position:relative; 
z-index:3;}

#box a {
color:#ffffff;
background:#FF0000;
font:bold 16px verdana, sans-serif;
text-decoration:none;
display:block;
padding:5px;
border:1px solid black;}

#box a:hover {
color:black;
background:#FF0000;
width:1600x;}

#box a span {display:none;}

#box a:hover span {
color:black;
background:#ffffff;
font:normal 16px verdana, sans-serif;
border:1px solid black;
display:block;
padding:10px;}
-->
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>StripTs</title>
</head>
<body>
	<div align="center">
	<form action="SFBServletStripTs#i" method="POST">
		<p><b>Request - Using Online Resource:</b></p>
		<div>
			<textarea name="RequestInput" cols="80" rows="4"></textarea>
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
    xmlns:lapis="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#"
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:stripts="http://141.52.218.34:8080/Prototyp/StripTs/Ontology#"> 
  
<rdf:Description rdf:about="http://www.example.com/Request_01_Example">
    <rdf:type rdf:resource="http://141.52.218.34:8080/Prototyp/Ontology/Lapis#Request"/>
    <lapis:salt>80844534</lapis:salt>
	<stripts:hasInputBrainAtlasImage>https://xnat.sfb125.de/data/projects/CAUC/resources/Example_Data/files/atlasImage.mha</stripts:hasInputBrainAtlasImage>
	<stripts:hasInputBrainAtlasMask>https://xnat.sfb125.de/data/projects/CAUC/resources/Example_Data/files/atlasMask.mha</stripts:hasInputBrainAtlasMask>
	<stripts:hasInputImage>https://xnat.sfb125.de/data/projects/CAUC/resources/Example_Data/files/T1.nrrd</stripts:hasInputImage>
</rdf:Description>
</rdf:RDF>
					</textarea>
	</div>
	<br/>
	<br/>
	<div align ="center" id="box"><a href="http://141.52.218.34:8080/Prototyp/StripTs_2.jsp">Want to do Request with File Upload?<span>Click here!</span></a></div>
</body>
</html>