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
<title>XSPARQL - RDF/XML Converter</title>
</head>
<body>
	<h1 align="center">Converter</h1>
	<form action="SFBServletXSPARQL#i" method="POST">
		<div align="center">Request:</div>
		<div align="center">
			<textarea name="RequestInput" cols="100" rows="20"></textarea>
			<br /> <input type="submit" value="Post Request" />
		</div>
	</form>
	<br />
	<br />
	<br />
	<div align="center">
	<input type="submit" value="Show Example"
		onclick="showDiv();this.style.visibility= 'hidden';" />
	</div>
	<div align="center" id="example" style="display: none;" class="answer_list">
	<div align="center">Example:</div>
		<textarea name="RequestInput" cols="100" rows="20">
declare namespace foaf = "http://xmlns.com/foaf/0.1/";
for $person in doc("http://xsparql.deri.org/data/relations.xml")//person,
    $nameA in $person/@name,
    $nameB in $person/knows
construct
{
[ foaf:name {data($nameA)}; a foaf:Person ]
foaf:knows
[ foaf:name $nameB; a foaf:Person ].
}
		
		</textarea>
	</div>
	<br />
	<br />
	<br />
	<div align ="center" id="box"><a href="http://141.52.218.34:8080/Prototyp/Converter_2.jsp">Want to POST XML/RDF also?<span>Click here!</span></a></div>
</body>
</html>