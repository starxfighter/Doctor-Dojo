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
	<title>Edit Allergy</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/EditAllergy.css" type="text/css" rel="stylesheet">
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
					<a class="nav-link" href="/allergy/index">Allergy Dashboard</a>
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
			<div class="col-sm-12 col-md-4 offset-md-4">
				<h5><c:out value="${error}"/></h5>
				<form:form action="/allergy/edit/${allergy.id}" method="POST" modelAttribute="allergy">
					<input type="hidden" name="_method" value="put">
					<p>
						<form:input class="form-control form-control-sm" path="allergen"/>
						<form:errors path="allergen"/>
					</p>
					<p>
						<form:select class="form-control form-control-sm" path="category">
							<form:option value="Eduardo">Eduardo</form:option>
							<form:option value="Drug">Drug</form:option>
							<form:option value="Food">Food</form:option>
							<form:option value="Insect">Insect</form:option>
							<form:option value="Latex">Latex</form:option>
							<form:option value="Mold">Mold</form:option>
							<form:option value="Pet">Pet</form:option>
							<form:option value="Pollen">Pollen</form:option>
						</form:select>
					</p>
					<p>
						<form:select class="form-control form-control-sm" path="severity">
							<form:option value="Mild">Mild</form:option>
							<form:option value="Annoyance">Annoyance</form:option>
							<form:option value="Distracting">Distracting</form:option>
							<form:option value="Severe">Severe</form:option>
						</form:select>
					</p>
					<p>
						<form:select class="form-control form-control-sm" path="reaction">
							<form:option value="Rash">Rash</form:option>
							<form:option value="Hives">Hives</form:option>
							<form:option value="Anaphylaxis">Anaphylaxis</form:option>
							<form:option value="Discomfort">Discomfort</form:option>
							<form:option value="Death">Death</form:option>
						</form:select>
					</p>
					<p>
						<form:select class="form-control form-control-sm" path="allergyStatus">
							<form:option value="Active">Active</form:option>
							<form:option value="Historical">Historical</form:option>
						</form:select>
					</p>
					<p>
						<input class="form-control form-control-sm" type="date" name="rDate"/>
					</p>
					<form:hidden path="patient"/>
					<button class="btn-sm" type="submit">Update Allergy</button>
				</form:form>
			</div>
		</div>
	</div>		
</body>
</html>