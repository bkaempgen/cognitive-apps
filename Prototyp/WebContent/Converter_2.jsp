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
font:normal 12px verdana, sans-serif;
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
	<div align ="center" id="box">
		<a href="#">Remark
			<span>
				You must use the string "<FONT COLOR="#FF0000">MySpecifiedData</FONT>" as data source in your XSPARQL query to refer to your posted XML/RDF!<br/><br/>
				e.g.:<br/>
				for * from &lt;<FONT COLOR="#FF0000">MySpecifiedData</FONT>&gt;<br/>
				for * in doc("<FONT COLOR="#FF0000">MySpecifiedData</FONT>")
			</span>
		</a>
	</div>
	<br/>
	<br/>
	<form action="SFBServletXSPARQL#i" method="POST">
		<div align="center">
			<table>
			<tr>
			<td align="center">XSPARQL-Request-Input:</td>
			<td align="center">XML/RDF-Request-Input:</td>
			</tr>
			<tr>
			<td>
			<textarea name="RequestXSPARQLInput" cols="80" rows="20"></textarea>
			</td>
			<td>
			<textarea name="RequestXMLRDFInput" cols="80" rows="20"></textarea>
			</td>
			</tr>
			</table>
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
	<table>
		<tr>
			<td>
				<textarea name="RequestInput" cols="80" rows="20">
declare namespace foaf = "http://xmlns.com/foaf/0.1/";
for $person in doc("MySpecifiedData")//person,
    $nameA in $person/name,
    $nameB in $person/knows
construct
{
[ foaf:name $nameA; a foaf:Person ]
foaf:knows
[ foaf:name $nameB; a foaf:Person ].
}
				</textarea>
			</td>
			<td>
				<textarea name="RequestInput" cols="80" rows="20">
<relations>
	<person id="1"> 
		<name>Philipp</name>
		<knows>Bob</knows> 
		<knows>Charles</knows> 
		<knows>Jan</knows> 
	</person> 
	<person id="2"> 
		<name>Jan</name>
		<knows>Philipp</knows> 
	</person> 
	<person id="3">
		<name>Nicole</name>
		<knows>Clara</knows>
		<knows>Philipp</knows>
		<knows>Bob</knows>
	</person>
</relations>
				</textarea>
			</td>
		</tr>
	</table>
	</div>
</body>
</html>