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
	<title>Edit Medication</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/editMedication.css" type="text/css" rel="stylesheet">
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
					<a class="nav-link" href="/medication/index">Medication Dashboard</a>
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
			<div class="col-sm-12 col-md-3 offset-md-5">		
				<h3>Edit Medication</h3>
				<c:out value="${error}"/>
				<form:form action="/medication/edit/${currMedication.id}" method="post" modelAttribute="medicationToEdit">
					<input type="hidden" name="_method" value="put">						
					
						<form:errors path="drugName"/>
				        <form:input class="form-control form-control-sm" value="${currMedication.drugName}" path="drugName" placeholder="Drug Name"/>
				  		
				  		<form:errors path="drugForm"/>
				    	<form:select class="form-control form-control-sm" value="${currMedication.drugForm}" path="drugForm" placeholder="Drug Form">
				        	<form:option value="Oral"/>
				        	<form:option value="Ophthalmic"/>
				        	<form:option value="Inhalation"/>
				        	<form:option value="Parenteral"/>
				        	<form:option value="Topical"/>
				        	<form:option value="Suppository"/>
				        </form:select>
				  
				  		<form:errors path="frequency"/>
				    	<form:select class="form-control form-control-sm" value="${currMedication.frequency}" path="frequency" placeholder="Frequency">
				        	<form:option value="Daily"/>
				        	<form:option value="Twice Daily"/>
				        	<form:option value="Three Times Daily"/>
				        	<form:option value="Four Times Daily"/>
				        	<form:option value="Mornings"/>
				        	<form:option value="Evenings"/>
				        </form:select>
				 
				 		<form:errors path="duration"/>
				        <form:input class="form-control form-control-sm" value="${currMedication.duration}" path="duration" placeholder="Duration"/>
				   
				   		<form:errors path="dosage"/>
				        <form:input class="form-control form-control-sm" value="${currMedication.dosage}" path="dosage" placeholder="Dosage"/>
				
				    	<label>Start Date:</label>
				    	<input class="form-control form-control-sm" type="date" name="startDay"/> <!-- Format date -->
				
				    	<label>End Date:</label>
				    	<input class="form-control form-control-sm" type="date" name="endDay"/> <!-- Format date -->
				    	
				    <input class="form-control form-control-sm" type="submit" value="Submit"/>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>