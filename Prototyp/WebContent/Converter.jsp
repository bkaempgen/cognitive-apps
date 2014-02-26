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
	<input type="submit" value="Show Examples"
		onclick="showDiv();this.style.visibility= 'hidden';" />
	</div>
	<div align="center" id="example" style="display: none;" class="answer_list">
		<table align="center">
			<tr>
				<td align="center">Example 1:</td>
				<td align="center">Example 2:</td>
			</tr>
			<tr>
				<td>
					<textarea name="Example1" cols="100" rows="22">
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
				</td>
				<td>
					<textarea name="Example2" cols="100" rows="22">
declare namespace foaf = "http://xmlns.com/foaf/0.1/";
let $baseURI := "http://www.example.org/"
return

	for $person in doc("http://xsparql.deri.org/data/relations.xml")//person
	let $name := $person/@name
	let $uriName := xsparql:createURI(fn:concat($baseURI, $name))
	construct
	{
		$uriName a foaf:Person;
		foaf:name {data($name)}.
		
		{	for $friend in $person/knows
			let $uriFriend := xsparql:createURI(fn:concat($baseURI, $friend))
			construct 
			{ 
			$uriName foaf:knows $uriFriend.
			}
		} 
	}
					</textarea>
				</td>
			</tr>
		</table>
	</div>
	<br />
	<br />
	<br />
	<div align ="center" id="box"><a href="http://localhost:8080/Prototyp/Converter_2.jsp">Want to POST XML/RDF also?<span>Click here!</span></a></div>
</body>
</html>