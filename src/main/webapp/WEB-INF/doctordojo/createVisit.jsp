<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Create Visit</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/createVisit.css" type="text/css" rel="stylesheet">
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
					<a class="nav-link" href="/logout">Logout</a>
				</li>
			</ul>
		</nav>
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
				<br><br>
			</div>
			<div class="col-sm-12 col-md-3 offset-md-5 lower">	
				<h4>Create a Visit</h4>
				<c:out value="${error}"/>
				<form:form action="/visit/new/${patientId}" method="post" modelAttribute="visitToCreate">
				    	<input class="form-control form-control-sm" type="date" name="visitDay"/>
				    	<form:errors path="reason"/>
				        <form:input class="form-control form-control-sm" path="reason" placeholder="Reason for Visit"/>
				        <form:input class="form-control form-control-sm" path="diagnosis" placeholder="Diagnosis"/>
				        <form:errors path="visitStatus"/>
				    	<form:select class="form-control form-control-sm" path="visitStatus" placeholder="Visit Status">
				        	<form:option value="In Progress"/>
				        	<form:option value="Complete"/>
				        </form:select>
				        <br>
				    <button class="btn-sm" type="submit">Create Visit</button>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>