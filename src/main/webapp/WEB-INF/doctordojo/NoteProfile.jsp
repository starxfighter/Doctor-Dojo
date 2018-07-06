<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Patient Note Profile</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/NoteProfile.css" type="text/css" rel="stylesheet">
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<nav class="navbar navbar-expand-md">
			<a class="navbar-brand" href="/dashboard">
    			<img src="http://softwaregroup-int.com/wp-content/uploads/2016/03/pharmacy-management-icon-300x300.png" width="70" height="70" alt="Nope.">
 			</a>
			<ul class="navbar-nav ml-auto">
				<li class="nav-item">
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
				<br>
			</div>
			<div class="col-sm-12 col-md-8 offset-md-2 above">
				<table class="table table-sm table-hover">
					<thead>
						<tr>
							<th>Type</th>
							<th>Create Date</th>
							<th>Update Date</th>
							<th>Status</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${visit.notes}" var="vn">
							<tr>
								<td>S.O.A.P</td>
								<td><fmt:formatDate value="${vn.createdAt}" pattern="MM/dd/yyyy"/></td>
								<td><fmt:formatDate value="${vn.updatedAt}" pattern="MM/dd/yyyy"/></td>
								<td><c:out value="${vn.noteStatus}"/></td>
								<td><a href="/note/edit/${vn.id}"><button>Update</button></a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="row">
					<div class="col-sm-12 col-md-2 offset-md-10">
						<a href="/note/new"><button class="btn-sm">Add Note</button></a>
					</div>
				</div>
			</div>
		</div>
	</div>	
</body>
</html>