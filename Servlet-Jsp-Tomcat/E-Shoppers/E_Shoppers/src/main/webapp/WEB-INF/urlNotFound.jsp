<%--
  Created by IntelliJ IDEA.
  User: peripatetic
  Date: 11/15/25
  Time: 3:31 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container d-flex justify-content-center align-items-center" style="min-height: 60vh;">
    <div class="text-center bg-warning-subtle shadow rounded p-5">
        <h1 class="display-4 mb-3">404</h1>
        <h4 class="mb-3">Page Not Found</h4>
        <p class="mb-4">
            Sorry, we could not find the page you were looking for. The URL you entered might be incorrect.
        </p>
        <a href="<c:url value='/'/>" class="btn btn-primary btn-lg">Go to Home</a>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
