<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Edit A Note</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/EditNote.css" type="text/css" rel="stylesheet">
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
					<a class="nav-link" href="/">Dashboard</a>
				</li>
				<li>
					<a class="nav-link" href="/note/index">Notes Dashboard</a>
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
			<div class="col-sm-12 col-md-4 offset-md-4 above">
				<h5><c:out value="${error}"/></h5>
				<form:form action="/note/edit/${note.id}" method="POST" modelAttribute="note">
					<input type="hidden" name="_method" value="put">
					<h4>
						<form:select class="form-control form-control-sm" path="noteStatus">
							<form:option value="Active">Active</form:option>
							<form:option value="Historical">Historical</form:option>
							<form:option value="In Error">In Error</form:option>
						</form:select>
					</h4>		
					<h4>
						<form:errors path="subjective"/>
						<form:textarea class="form-control form-control-sm" path="subjective" rows="4" cols="20"/>
					</h4>
					<h4>
						<form:errors path="objective"/>
						<form:textarea class="form-control form-control-sm" path="objective" rows="4" cols="20"/>
					</h4>
					<h4>
						<form:errors path="assessment"/>
						<form:textarea class="form-control form-control-sm" path="assessment" rows="4" cols="20"/>
					</h4>
					<h4>
						<form:errors path="plan"/>
						<form:textarea class="form-control form-control-sm" path="plan" rows="4" cols="20"/>
					</h4>
					<form:hidden path="visit"/>
					<input type="submit" value="Update Note">
				</form:form>
			</div>
		</div>
	</div>	
</body>
</html>