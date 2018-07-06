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
	<title>Encounter Details</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/EncounterView.css" type="text/css" rel="stylesheet">
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
				<h6>Case Number: <c:out value="${visit.caseNumber}"/></h6>
				<br>
			</div>
			<div class="right col-sm-12 col-md-4 offset-md-3">
				<h5><c:out value="${error}"/></h5>
				<h5><form:errors path="visit.*"/></h5>
				<form:form action="/encounter/edit/${visit.id}" method="POST" modelAttribute="visit">
					<div class="row">
						<div class="col-sm-12 col-md-6">
							<input type="hidden" name="_method" value="put">
							<form:label path="diastolic">Diastolic</form:label>
							<form:input class="form-control form-control-sm" type="number" path="diastolic" min="60"/>
							<form:label path="systolic">Systolic</form:label>
							<form:input class="form-control form-control-sm" type="number" path="systolic" min="90"/>
							<form:label path="pulserate">Pulse Rate: </form:label>
							<form:input class="form-control form-control-sm" type="number" min="30" path="pulserate"/>
							<form:label path="respiration">Respiration: </form:label>
							<form:input class="form-control form-control-sm" type="number" min="8" path="respiration"/>
							<form:label path="temperature">Temperature: </form:label>
							<form:input class="form-control form-control-sm" type="number" min="70" step=".1" path="temperature"/>
						</div>
						<div class="col-sm-12 col-md-6">
							<form:label path="heightFeet"> Feet</form:label>
							<form:input class="form-control form-control-sm" type="number" min='1' path="heightFeet"/>
							<form:label path="heightInches"> Inches</form:label>
							<form:input class="form-control form-control-sm" type="number" min='1' path="heightInches"/>
							<form:label path="weight">Weight: </form:label>
							<form:input class="form-control form-control-sm" type="number" min="1" path="weight"/>
							<form:label path="diagnosis">Diagnosis:</form:label>
							<form:input class="form-control form-control-sm" path="diagnosis"/>
							<form:hidden path="id"/>
							<form:hidden path="caseNumber"/>
							<form:hidden path="visitStatus"/>
							<form:hidden path="reason"/>
							<form:hidden path="patient"/>
							<form:hidden path="notes"/>
							<form:hidden path="billing"/>
							<br>
							<button class="btn-sm" type="submit">Update</button>
						</div>
					</div>
				</form:form>
			</div>		
		</div>
		<br><br>
		<div class="row">
			<div class="col-sm-12 col-md-6 offset-md-6">
				<a href="/note/index"><button class="btn-sm">Add/View Notes</button></a>
				<a href="/medication/index"><button class="btn-sm">Add/View Medications</button></a>
				<a href="/allergy/index"><button class="btn-sm">Add/View Allergies</button></a>
			</div>
		</div>
	</div>
</body>
</html>