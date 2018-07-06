<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Doctor Dojo Registration Page</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/registrationPage.css" type="text/css" rel="stylesheet">
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
					<a class="nav-link" href="/registration">Register</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="/login">Login</a>
				</li>
			</ul>
		</nav>
		<br><br>
		<div class="row">
			<div class="col-sm-4 offset-sm-6 col-med-4">
				<h1 class="title">Doctor Dojo</h1>
 				<h5>Where no virus is safe!</h5>
				<br>
				<p><form:errors path="user.*"/></p>
				<div id="form">
					<form:form method="POST" action="/registration" modelAttribute="user">
						<form:input class="form-control form-control-sm" path="username" placeholder="User Name"/>
						<form:input class="form-control form-control-sm" path="firstName" placeholder="First Name"/>
						<form:input class="form-control form-control-sm" path="lastName" placeholder="Last Name"/>
						<form:input class="form-control form-control-sm" path="empNumber" maxlength="5" placeholder="Employee Number"/>
		            	<form:password class="form-control form-control-sm" path="password" placeholder="Password"/>
		           	 	<form:password class="form-control form-control-sm" path="passwordConf" placeholder="Confirm Password"/>
						<select class="form-control form-control-sm" name="accesstype">
							<option value="Nurse">Nurse</option>
							<option value="FrontOffice">Front Office</option>
							<option value="Physician">Physician</option>
							<option value="Admin">Admin</option>
						</select>
						<input class="form-control form-control-sm" type="submit" value="register"/>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>