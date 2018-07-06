<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Doctor Dojo Dashboard</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/Dashboard.css" type="text/css" rel="stylesheet">
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
					<a class="nav-link" href="#">Schedule Appointment</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/logout">Logout</a>
				</li>
			</ul>
		</nav>
		<div class="row">
			<div class="d-none d-sm-block col-md-12">
				<h3>Welcome, <c:out value="${currentUser.firstName}"/>!</h3>
				<p class="line d-none d-sm-block col-md-4 offset-md-4"></p>
				<p>Search for a patient to begin.</p>
			</div>
			<form class="col-sm-12 col-md-7 offset-md-6" action="/patient/search2" method="GET">
				<input name="search"/>
				<button id="newPatient" class="btn-sm" type="submit">Search for Patient</button>
			</form>
		</div>
		<br><br>
		<div class="row">
			<div class="col">
				<h3>Patients</h3>
				<table class="table table-sm table-hover">
					<thead>
					<tr>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Sex</th>
						<th>Date of Birth</th>
						<th>Street Address</th>
						<th>City</th>
						<th>State</th>
						<th>Zip</th>
						<th>Actions</th>
					</tr>
					</thead>
					<tbody>
						<c:forEach items="${patient}" var="p">
							<tr>
								<td><c:out value="${p.firstName}"/></td>
								<td><c:out value="${p.lastName}"/></td>
								<td><c:out value="${p.sex}"/></td>
								<td><fmt:formatDate value="${p.dob}" pattern="MM/dd/yyyy"/></td>
								<td><c:out value="${p.streetAddress}"/></td>
								<td><c:out value="${p.city}"/></td>
								<td><c:out value="${p.state}"/></td>
								<td><c:out value="${p.zip}"/></td>
								<td>
									<a href="/visit/dashboard/${p.id}"><button class="btn-sm">Check-In</button></a>
									<a href="/patient/update/${p.id}"><button class="btn-sm">Update</button></a>
								</td>				
						</c:forEach>
					</tbody>	
				</table>
			</div>
		</div>
		<br><br>
		<div class="row">
			<div class="col-sm-12 col-md-7 offset-md-6">
				 <a class="col-sm-12 col-md-2 offset-md-4" href="/patient/new"><button class="btn-sm">New Patient</button></a>
			</div>
		</div>
		<br><br>
		<div class="row">
			<div class="d-none d-sm-block col-sm-12 col-md-12">
				<h3>Visits to be Checked-Out</h3>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<table class="table table-sm table-hover">
					<thead>
						<tr>
							<th>MRN</th>
							<th>Patient Name</th>
							<th>Visit Number</th>
							<th>Visit Date</th>
							<th>Charge</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${bills}" var="b">
							<tr>
								<td><c:out value="${b.visit.patient.mpi}"/></td>
								<td><c:out value="${b.visit.patient.lastName}"/> , <c:out value="${b.visit.patient.firstName}"/></td>
								<td><c:out value="${b.visit.caseNumber}"/></td>
								<td><fmt:formatDate value="${b.visit.visitDate}" pattern="MM/dd/yyyy"/></td>
								<td>
									<fmt:setLocale value = "en_US"/>
	         						<fmt:formatNumber value = "${b.charge}" type = "currency"/>
								</td>
								<td>
									<form action="/billing/chargecomplete/${b.id}" method="POST">
									  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
									  <script
									    src="https://checkout.stripe.com/checkout.js" class="stripe-button"
									    data-key="pk_test_QJ0ku4SiZLDHHdcvdJKHPZx6"
									    data-amount="${b.charge * 100}"
									    data-name="Doctor Dojo"
									    data-description="Charge for services rendered"
									    data-image="https://upload.wikimedia.org/wikipedia/commons/thumb/5/5b/Star_of_life2.svg/600px-Star_of_life2.svg.png"
									    data-locale="auto"
									    data-zip-code="true">
									  </script>
									</form>
								</td>
							</tr>
						</c:forEach>	
					</tbody>
				</table>
			</div>
		</div>
   	</div>
</body>
</html>