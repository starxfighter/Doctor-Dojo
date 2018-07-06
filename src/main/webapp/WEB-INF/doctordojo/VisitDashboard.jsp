<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Visit Dashboard</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/VisitDashboard.css" type="text/css" rel="stylesheet">
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
				<a href="/visit/new/${pid}"><button class="btn-sm">Create Visit</button></a>
				<br><br>
			</div>
			<table class="col-sm-12 col-md-9 offset-md-1 table table-sm table-hover">
				<thead>
					<tr>
						<th>Case Number</th>
						<th>Visit Date</th>
						<th>Reason</th>
						<th>Diagnosis</th>
						<th>Status</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${patient.visits}" var="pv">
						<tr>
							<td><c:out value="${pv.caseNumber}"/></td>
							<td><fmt:formatDate value="${pv.visitDate}" pattern="MM/dd/yyyy"/></td>
							<td><c:out value="${pv.reason}"/></td>
							<td><c:out value="${pv.diagnosis}"/></td>
							<td><c:out value="${pv.visitStatus}"/></td>
							<td>
								<c:if test = "${pv.visitStatus.equals('In Progress')}">
									<a href="/visit/edit/${pv.id}"><button class="btn-sm">Update Visit</button></a>
									<a href="/encounter/index/${pv.id}"><button class="btn-sm">Update Encounter</button></a>
								</c:if>
								<c:if test = "${pv.visitStatus.equals('Complete')}">
									Visit Closed
									<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal">Historical View</button></a>
								</c:if>
							</td>
						</tr>
							<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
							  <div class="modal-dialog" role="document">
							    <div class="modal-content">
							      <div class="modal-header">
							        <h5 class="modal-title" id="exampleModalLabel">Patient History View</h5>
							        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
							          <span aria-hidden="true">×</span>
							        </button>
							      </div>
							      <div class="modal-body">
							      	<h4>Patient Information</h4>
							      		<p><c:out value="${patient.firstName}"/> <c:out value="${patient.lastName}"/></p>
							      		<p>Sex: <c:out value="${patient.sex}"/></p>
							      		<p>Date of Birth: <fmt:formatDate value="${patient.dob}" pattern="MM/dd/yyyy"/></p>
							      		<p>MRN: <c:out value="${patient.mpi}"/></p>
							      	<hr>	
							      	<h4>Visit Information</h4>
							      		<p>Case Num: <c:out value="${pv.caseNumber}"/></p>
							      		<p>Status: <c:out value="${pv.visitStatus}"/></p>
							      		<p>Reason: <c:out value="${pv.reason}"/></p>
							      		<p>Diagnosis <c:out value="${pv.diagnosis}"/></p>
							      		<p>Date: <fmt:formatDate value="${pv.visitDate}" pattern="MM/dd/yyyy"/></p>
						      		<hr>
						      		<h4>Patient Vitals</h4>
						      			<h6>BP</h6>
						      			<p><c:out value="${pv.systolic}"/> / <c:out value="${pv.diastolic}"/></p>
						      			<p>Pulse Rate: <c:out value="${pv.pulserate}"/></p>
						      			<p>Respiration: <c:out value="${pv.respiration}"/></p>
						      			<p>Temperature: <c:out value="${pv.temperature}"/></p>
						      			<p>Height: <c:out value="${pv.heightFeet}"/>' <c:out value="${pv.heightInches}"/>"</p>
						      			<p>Weight: <c:out value="${pv.weight}"/></p>
						      		<hr>	
									<h4>Note Information</h4>
										<c:forEach items="${pv.notes}" var="vxn">
							      			<p>Subjective: <c:out value="${vxn.subjective}"/></p>
							      			<p>Objective: <c:out value="${vxn.objective}"/></p>
							      			<p>Assessment: <c:out value="${vxn.assessment}"/></p>
							      			<p>Plan: <c:out value="${vxn.plan}"/></p>
							      			<p>Status: <c:out value="${vxn.noteStatus}"/></p>
										</c:forEach>
							      	<hr>					      		      	
							      	<h4>Billing Information</h4>
							      		<p>Billing Charge: $<c:out value="${pv.billing.charge}"/></p>
							      		<p>Billing Status: <c:out value="${pv.billing.billingStatus}"/></p>
							      		<p>Billing Date: <fmt:formatDate value="${pv.billing.billingDate}" pattern="MM/dd/yyyy"/></p>
							      	
						
							      </div>
							      <div class="modal-footer">
							        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
							      </div>
							    </div>
							  </div>
							</div>	
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>