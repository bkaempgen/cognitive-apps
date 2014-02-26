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
	<h1 align="center">Prototyp</h1>
	<br />
	<div align="center">
		<table>
			<tr>
				<th>Ontologies:</th>
				<th>Pipeline:</th>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td align="center"><a href="http://141.52.218.34:8080/Prototyp/Ontology/CTK">CTK</a></td>
							<td align="center"><a href="http://141.52.218.34:8080/Prototyp/Ontology/Lapis">LAPIS</a></td>
						</tr> 
					</table>
				</td>
				<td>
					<table>
						<tr>
							<td align="center"><a href="http://141.52.218.34:8080/Prototyp/Mockup.jsp">Mockup</a></td>
						</tr> 
					</table>
				</td>
			</tr>
		</table>
	</div>
	<br />
	<br />
	<div align="center">
	<div><b>Linked Services / LAPIs:</b><br/></div>
	<table border="1">
		<tr bgcolor="#FF0000">
			<th colspan="3">Preprocessing</th>
		</tr>
		<tr>	
			<th>Name</th>
			<th>Transformation</th>
			<th>Try Request via</th>
		</tr>	
		<tr>
			<td><a href="SFBServletCast#i">Cast-Service:</a></td>
			<td/>
			<td align="center" valign="middle"><a href="http://141.52.218.34:8080/Prototyp/Cast.jsp">JSP</a></td>
		</tr>
		<tr>
			<td><a href="SFBServletMeanFree#i">MeanFree-Service:</a></td>
			<td/>
			<td align="center" valign="middle"><a href="http://141.52.218.34:8080/Prototyp/MeanFree.jsp">JSP</a></td>
		</tr>
		<tr>
			<td><a href="SFBServletStripTs#i">StripTs:</a></td>
			<td/>
			<td align="center" valign="middle"><a href="http://141.52.218.34:8080/Prototyp/StripTs.jsp">JSP</a></td>
		</tr>
		<tr bgcolor="#FF0000">
			<th colspan="3">Converter</th>
		</tr>
		<tr>	
			<th>Name</th>
			<th>Transformation</th>
			<th>Try Request via</th>
		</tr>	
		<tr>
			<td><a href="SFBServletXSPARQL#i">XSPARQL:</a></td>
			<td align="center" >XML&harr;RDF</td>
			<td align="center" valign="middle"><a href="http://141.52.218.34:8080/Prototyp/Converter.jsp">JSP</a></td>
		</tr>
		<tr bgcolor="#FF0000">
			<th colspan="3">CTK-Description</th>
		</tr>
		<tr >	
			<th>Name</th>
			<th>Transformation</th>
			<th>Try Request via</th>
		</tr>	
		<tr>
			<td><a href="SFBServletTranslator#i">CTK-Translator:</a></td>
			<td align="center" valign="middle">XML&rarr;RDF</td>
			<td align="center" valign="middle"><a href="http://141.52.218.34:8080/Prototyp/Translator.jsp">JSP</a></td>
		</tr>
		<tr>
			<td><a href="SFBServletDescriber#i">CTK-Describer:</a></td>
			<td align="center" valign="middle">RDF&rarr;SPARQL</td>
			<td align="center" valign="middle"><a href="http://141.52.218.34:8080/Prototyp/Describer.jsp">JSP</a></td>
		</tr>
		<tr bgcolor="#FF0000">
			<th colspan="3">Other</th>
		</tr>
		<tr>	
			<th>Name</th>
			<th>Transformation</th>
			<th>Try Request via</th>
		</tr>	
		<tr>
			<td><a href="CalcServlet">Calculator:</a></td>
			<td/>
			<td align="center" valign="middle"><a href="http://141.52.218.34:8080/Prototyp/Calculator.jsp">JSP</a></td>
		</tr>
	</table>
	</div>
	
	
	
	
	
	
	
</body>
</html>
