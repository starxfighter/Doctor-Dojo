<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Edit Visit</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/editVisit.css" type="text/css" rel="stylesheet">
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
					<a class="nav-link" href="/visit/dashboard/${patient.id}">Visit Dashboard</a>
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
				<h6>Case Number: <c:out value="${currVisit.caseNumber}"/></h6>
				<br><br>
			</div>
			<div class="right col-sm-12 col-md-3 offset-md-5">
				<c:out value="${error}"/>
				<form:form action="/visit/edit/${currVisit.id}" method="post" modelAttribute="visitToEdit">
					<input type="hidden" name="_method" value="put">
				    <p>
				    	<label>Date:</label>
				    	<input class="form-control form-control-sm" type="date" name="visitDay"/> <!-- Format date -->
				    </p>
				    <p>
				        <form:label path="reason">Reason:</form:label>
				        <form:errors path="reason"/>
				        <form:input class="form-control form-control-sm" value="${currVisit.reason}" path="reason" minLength="1"/>
				    </p>
				    <p>
				        <form:label path="diagnosis">Diagnosis:</form:label>
				        <form:errors path="diagnosis"/>
				        <form:input class="form-control form-control-sm" value="${currVisit.diagnosis}" path="diagnosis"/>
				    </p>
				    <p>
				    	<form:label path="visitStatus">Visit Status:</form:label>
				    	<form:errors path="visitStatus"/>
				    	<form:select class="form-control form-control-sm" value="${currVisit.visitStatus}" path="visitStatus">
				        	<form:option value="In Progress"/>
				        	<form:option value="Complete"/>
				        </form:select>
				    </p>	    
				    <button class="btn-sm" type="submit">Submit</button>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>