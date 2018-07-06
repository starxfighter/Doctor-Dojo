<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Create New Patient</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/NewPat.css" type="text/css" rel="stylesheet">
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
				<li class="nav-item">
					<a class="nav-link" href="/logout">Logout</a>
				</li>
			</ul>
		</nav>
		<div class="row">
			<div class="col-sm-6 offset-sm-4 col-med-3 offset-med-5 offset-lg-7 col-lg-3">
			<br><br>
				<h3>New Patient</h3>
				<p><form:errors path="patient.*"/></p>
				<p><c:out value="${error}"/></p>
				<form:form action="/patient/new" method="POST" modelAttribute="patient">
					<form:input class="form-control form-control-sm" path="firstName" placeholder="First Name"/>
					<form:input class="form-control form-control-sm" path="lastName" placeholder="Last Name"/>
					<form:select class="form-control form-control-sm" path="sex" placeholder="Sex">
						<form:option value="Male">Male</form:option>
						<form:option value="Female">Female</form:option>
					</form:select>
					<form:input class="form-control form-control-sm" type="email" path="email" placeholder="email@gmail.com"/>
					<form:input class="form-control form-control-sm" path="streetAddress" placeholder="Street Address"/>
					<form:input class="form-control form-control-sm" path="city" placeholder="City"/>
					<form:input class="form-control form-control-sm" maxlength="2" path="state" placeholder="NY"/>
					<form:input class="form-control form-control-sm" path="zip" minlength="5" maxlength="9" placeholder="91504"/> 
					<form:input class="form-control form-control-sm" path="phone" type="tel"
	         						placeholder="123-456-7890"
	          						pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}"/>
	          		<label>Date of Birth</label>
					<input class="form-control form-control-sm" type="date" name="fDob"/>
					<input class="form-control form-control-sm" type="submit" value="Create">	
				</form:form>
			</div>
		</div>
	</div>	
</body>
</html>