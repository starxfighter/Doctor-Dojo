<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Patient Allergy Profile</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/AllergyProfile.css" type="text/css" rel="stylesheet">
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-expand-md">
			<a class="navbar-brand" href="/dashboard">
    			<img src="http://softwaregroup-int.com/wp-content/uploads/2016/03/pharmacy-management-icon-300x300.png" width="70" height="70" alt="Nope.">
 			</a>
			<ul class="navbar-nav ml-auto">
				<li>
					<a class="nav-link" href="/dashboard">Dashboard</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/encounter/index">Encounter Dashboard</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/logout">Logout</a>
				</li>
			</ul>
		</nav>
		<br>
		<div class="row">
			<div class="col-sm-12 col-md-2 background">
				<p class="bigger"><c:out value="${patient.lastName}"/>, <c:out value="${patient.firstName}"/></p>
				<hr>
				<h6>Date Of Birth: <fmt:formatDate value="${patient.dob}" pattern="MM/dd/yyyy"/></h6>
				<h6><c:out value="${patient.sex}"/></h6>
				<h6>
					<c:choose>
						<c:when test = "${nkaCheck.equals('true')}">
							No Known Allergies
						</c:when>
						<c:when test = "${patient.allergies.size() > 0}">
							Allergies Present
						</c:when>
						<c:otherwise>
							No Allergies Recorded
						</c:otherwise>
					</c:choose>
				</h6>
				<hr>
				<h6>MRN: <c:out value="${patient.mpi}"/></h6>
				<h6>Case Number: <c:out value="${visit.caseNumber}"/></h6>
				<br><br>
			</div>
				<div class="col-sm-12 col-md-10">
					<table class="table table-sm table-hover">
						<thead>
							<tr>
								<th>Allergen</th>
								<th>Category</th>
								<th>Severity</th>
								<th>Reaction Type</th>
								<th>Status</th>
								<th>On-Set Date</th>
								<th>Resolve Date</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${patient.allergies}" var="a">
								<tr>
									<td><c:out value="${a.allergen}"/></td>
									<td><c:out value="${a.category}"/></td>
									<td><c:out value="${a.severity}"/></td>
									<td><c:out value="${a.reaction}"/></td>
									<td><c:out value="${a.allergyStatus}"/></td>
									<td><fmt:formatDate value="${a.onsetDate}" pattern="MM/dd/yyyy"/></td>
									<td><fmt:formatDate value="${a.resolveDate}" pattern="MM/dd/yyyy"/></td>
									<td><a href="/allergy/edit/${a.id}"><button class="btn-sm">Update</button></a></td>
								</tr>		
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
			<nav class="navbar navbar-expand-md">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item">
						<div class="nav-link">
							<form action="/allergy/nka" method="GET">
								<button class="btn-sm" type="submit">No Known Allergies</button>
							</form>
						</div>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/allergy/new"><button class="btn-sm">Add Allergy</button></a>	
					</li>
				</ul>
			</nav>
		</div>	
</body>
</html>