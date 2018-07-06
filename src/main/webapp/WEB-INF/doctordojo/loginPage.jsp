<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Doctor Dojo Login</title>
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
	<link href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet">
	<link href="/css/loginPage.css" type="text/css" rel="stylesheet">
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
			<div class="col-sm-4 offset-sm-6">
			<h1 class="title">Doctor Dojo</h1>
			<h5>Where no virus is safe!</h5>
			<br>
			<c:if test="${logoutMessage != null}">
	        	<c:out value="${logoutMessage}"></c:out>
	    	</c:if>
				<c:if test="${errorMessage != null}">
		        	<c:out value="${errorMessage}"></c:out>
		    	</c:if>
		    	<div id="form">
					<form method="post" action="/login">
						<input class="form-control form-control-sm" type="text" id="username" name="username" placeholder="User Name" required />
			           	<input class="form-control form-control-sm" type="password" id="password" name="password" placeholder="Password"/>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			        	<input class="form-control form-control-sm" type="submit" value="Login"/>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>