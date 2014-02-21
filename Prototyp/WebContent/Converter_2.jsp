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
<title>XSPARQL - RDF/XML Converter</title>
</head>
<body>
	<h1 align="center">Converter</h1>
	<form action="SFBServletXSPARQL#i" method="POST">
		<div align="center">
			<table>
			<tr>
			<td align="center">XSPARQL-Request-Input:</td>
			<td align="center">XML/RDF-Request-Input:</td>
			</tr>
			<tr>
			<td>
			<textarea name="RequestXSPARQLInput" cols="100" rows="20"></textarea>
			</td>
			<td>
			<textarea name="RequestXMLRDFInput" cols="100" rows="20"></textarea>
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
		<textarea name="RequestInput" cols="100" rows="20">
declare namespace foaf = "http://xmlns.com/foaf/0.1/";
for $person in doc("http://xsparql.deri.org/data/relations.xml")//person,
    $nameA in $person/@name,
    $nameB in $person/knows
construct
{
[ foaf:name $nameA; a foaf:Person ]
foaf:knows
[ foaf:name $nameB; a foaf:Person ].
}
		
		</textarea>
	</div>
</body>
</html>