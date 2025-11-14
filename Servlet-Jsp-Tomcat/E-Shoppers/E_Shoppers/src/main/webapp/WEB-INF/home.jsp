<%--
  Created by IntelliJ IDEA.
  User: peripatetic
  Date: 10/23/25
  Time: 5:00 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webjars/bootstrap/5.3.8/css/bootstrap.css">
    <title>All Products</title>
</head>
<body style="padding-top: 70px;">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value="/"/>">
            e-Shoppers
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarResponsive"
                aria-controls="navbarResponsive"
                aria-expanded="false"
                aria-label="Toggle navigation">

            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarResponsive">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="<c:url value="/"/>">
                        Home
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">
                        About Us
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <div class="bg-light-subtle shadow p-4 rounded d-flex">
        <img src="<c:url value="/image/Pasted%20image.png"/>" style="height: 200px; margin-right: 20px;" alt=""/>
        <h1 class="mb-0" style="line-height: 1; display: flex; align-items: center;">Welcome to e-Shoppers!</h1>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
        </tr>
        </thead>

        <c:forEach var="product" items="${products}">
            <tr>
                <td><c:out value="${product.name}"/></td>

                <td><c:out value="${product.description}"/></td>

                <td><c:out value="${product.price}"/></td>
            </tr>
        </c:forEach>
    </table>
</div>

<footer class="footer mt-auto py-3 fixed-bottom">
    <div class="container">
        <span class="text-muted">
            Copyright &copy; eShoppers.com 2025
        </span>
    </div>
</footer>

</body>
</html>
