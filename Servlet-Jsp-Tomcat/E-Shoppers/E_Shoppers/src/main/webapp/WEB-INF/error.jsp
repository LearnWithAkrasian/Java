<%--
  Created by IntelliJ IDEA.
  User: peripatetic
  Date: 11/16/25
  Time: 1:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container d-flex justify-content-center align-items-center" style="min-height: 60vh;">
    <div class="text-center bg-warning-subtle shadow rounded p-5">
        <h4 class="mb-3">Could not process your request</h4>
        <p class="mb-4">
            Sorry, we could not process your request. Please try again later.
        </p>
        <a href="<c:url value='/'/>" class="btn btn-primary btn-lg">Go to Home</a>
    </div>
</div>
<%@include file="includes/footer.jsp"%>
