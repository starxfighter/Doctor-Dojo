<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Billing Dashboard</title>
</head>
<body>
	<div class="wrapper">
		<a href="/dashboard"><button>Dashboard</button></a>	
		<div class="profile">
			<table>
				<thead>
					<tr>
						<th>MRN</th>
						<th>Patient Name</th>
						<th>Visit Number</th>
						<th>Visit Date</th>
						<th>Charge</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bills}" var="b">
						<tr>
							<td><c:out value="${b.visit.patient.mpi}"/></td>
					</c:forEach>	
				
				</tbody>
			</table>
		
		</div>
	</div>
</body>
</html>