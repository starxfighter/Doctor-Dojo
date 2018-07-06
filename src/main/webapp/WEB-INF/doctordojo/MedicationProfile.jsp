<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Patient Medication Profile</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/MedicationProfile.css" type="text/css" rel="stylesheet">
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
				<li>
					<a class="nav-link" href="/encounter/index/${visit.id}">Encounter Dashboard</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/logout">Logout</a>
				</li>
			</ul>
		</nav>
		<br><br>
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
			<div class="col">
				<table class="table table-sm table-hover">
					<thead>
						<tr>
							<th>Drug Name</th>
							<th>Dosage</th>
							<th>Frequency</th>
							<th>Duration</th>
							<th>Drug Form</th>
							<th>Start Date</th>
							<th>End Date</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${patient.medications}" var="m">
							<tr>
								<td><c:out value="${m.drugName}"/></td>
								<td><c:out value="${m.dosage}"/></td>
								<td><c:out value="${m.frequency}"/></td>
								<td><c:out value="${m.duration}"/></td>
								<td><c:out value="${m.drugForm}"/></td>
								<td><fmt:formatDate value="${m.startDate}" pattern="MM/dd/yyyy"/></td>
								<td><fmt:formatDate value="${m.endDate}" pattern="MM/dd/yyyy"/></td>
								<td><a href="/medication/edit/${m.id}"><button class="btn-sm">Update</button></a></td>
							</tr>		
						</c:forEach>
					</tbody>
				</table>
				<div class="row">
					<div class="col-sm-12 col-md-2 offset-md-9">
						<a href="/medication/new/${patient.id}"><button class="btn-sm">Add Medication</button></a>
					</div>
				</div>
			</div>
		</div>
	</div>		
</body>
</html>