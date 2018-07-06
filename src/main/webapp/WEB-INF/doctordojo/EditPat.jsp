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
	<title>Edit Patient</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/EditPat.css" type="text/css" rel="stylesheet">
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="wrapper">
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
		<div class="row">
			<div class="col-xs-12 col-sm-4 col-md-3 offset-sm-6 lower">
			<h3>Edit <c:out value="${patient.firstName}"/> <c:out value="${patient.lastName}"/></h3>
				<h5><form:errors path="patient.*"/></h5>
				<h5><c:out value="${error}"/></h5>
				<form:form action="/patient/edit/${patient.id}" method="POST" modelAttribute="patient">
					<input type="hidden" name="_method" value="put">
						<form:errors path="firstName"/>
						<form:input class="form-control form-control-sm" path="firstName"/>
						<form:errors path="lastName"/>
						<form:input class="form-control form-control-sm" path="lastName"/>
						
						<form:select class="form-control form-control-sm" path="sex">
							<form:option value="Male">Male</form:option>
							<form:option value="Female">Female</form:option>
						</form:select>
						
						<input class="form-control form-control-sm" type="date" name="eDob" value="${eDob}"/>
						
						<form:errors path="email"/>
						<form:input class="form-control form-control-sm" type="email" path="email"/>
						<form:errors path="streetAddress"/>
						<form:input class="form-control form-control-sm" path="streetAddress"/>
						<form:errors path="City"/>
						<form:input class="form-control form-control-sm" path="city"/>
						<form:errors path="state"/>
						<form:input class="form-control form-control-sm" path="state"/>
						<form:errors path="zip"/>
						<form:input class="form-control form-control-sm" path="zip" minlength="5"/> 
						<form:input class="form-control form-control-sm" path="phone" type="tel" id="phone"
							placeholder="123-456-7890"
							pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"/>
					<form:hidden path="id"/>
					<form:hidden path="mpi"/>
					<form:hidden path="medications"/>
					<form:hidden path="allergies"/>
					<form:hidden path="visits"/>
					<br>
					<button class="btn-sm">Update</button>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>