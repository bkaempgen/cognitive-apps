<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Localhost</title>
</head>
<body>
	Current local time:
	<%=new java.util.Date()%>
	<br />
	<br />
	<br />
	<h3>Ontologies:</h3>
	<ul>
  		<li><a href="http://localhost:8080/Prototyp/Ontology/CTK">CTK</a></li>
  		<li><a href="http://localhost:8080/Prototyp/Ontology/Lapis">LAPIS</a></li>
	</ul>
	
	<br />
	<h3>Linked Services / LAPIs:</h3>
	<table border="2">
		<tr>
			<th>Available Services - Preprocessing</th>
			<th>Transformation</th>
			<th>Try Request via</th>
		</tr>
		<tr>
			<td><a href="SFBServletCast#i">Cast-Service:</a></td>
			<td/>
			<td align="center" valign="middle"><a href="http://localhost:8080/Prototyp/Cast.jsp">JSP</a></td>
		</tr>
		<tr>
			<td><a href="SFBServletMeanFree#i">MeanFree-Service:</a></td>
			<td/>
			<td align="center" valign="middle"><a href="http://localhost:8080/Prototyp/MeanFree.jsp">JSP</a></td>
		</tr>
		<tr>
			<td><a href="SFBServletStripTs#i">StripTs:</a></td>
			<td/>
			<td align="center" valign="middle"><a href="http://localhost:8080/Prototyp/StripTs.jsp">JSP</a></td>
		</tr>
		<tr>
			<th>Available Services - Description</th>
			<th>Transformation</th>
			<th>Try Request via</th>
		</tr>
		<tr>
			<td><a href="SFBServletTranslator#i">Translator:</a></td>
			<td align="center" valign="middle">XML&rarr;RDF</td>
			<td align="center" valign="middle"><a href="http://localhost:8080/Prototyp/Translator.jsp">JSP</a></td>
		</tr>
		<tr>
			<td><a href="SFBServletDescriber#i">Describer:</a></td>
			<td align="center" valign="middle">RDF&rarr;SPARQL</td>
			<td align="center" valign="middle"><a href="http://localhost:8080/Prototyp/Describer.jsp">JSP</a></td>
		</tr>
		<tr>
			<th>Available Services - Other</th>
			<th>Transformation</th>
			<th>Try Request via</th>
		</tr>
		<tr>
			<td><a href="CalcServlet">Calculator:</a></td>
			<td/>
			<td align="center" valign="middle"><a href="http://localhost:8080/Prototyp/Calculator.jsp">JSP</a></td>
		</tr>
	</table>
</body>
</html>