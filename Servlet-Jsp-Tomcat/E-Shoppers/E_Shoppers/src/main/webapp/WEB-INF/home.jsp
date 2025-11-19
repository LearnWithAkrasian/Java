<%--
  Created by IntelliJ IDEA.
  User: peripatetic
  Date: 10/23/25
  Time: 5:00 AM
  To change this template use File | Settings | File Templates.
--%>

<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container">
    <div class="bg-light-subtle shadow p-4 rounded d-flex">
        <img src="<c:url value="/image/Pasted%20image.png"/>" style="height: 200px; margin-right: 20px;" alt=""/>
        <c:if test="${sec:isAuthenticated(pageContext.request)}">
            <h1 class="mb-0" style="line-height: 1; display: flex;align-items: center;">
            Hello <c:out value="${sec:getCurrentUser(pageContext.request).firstName}"/>,
            </h1>
        </c:if>
        <h1 class="mb-0" style="line-height: 1; display: flex; align-items: center;"> Welcome to e-Shoppers!</h1>
    </div>
    <div class="row g-4 mt-5 mb-5">
        <c:forEach var="product" items="${products}">
            <div class="col-sm-4">
                <div class="card h-100 mb-4">
                    <div class="card-body">
                        <h5 class="card-title">
                            <c:out value="${product.name}"/>
                        </h5>
                        <p class="card-text">
                            <c:out value="${product.description}"/>
                        </p>
                        <p class="card-text">
                            <c:out value="${product.price}"/>
                        </p>
                        <a href="#" class="card-link btn btn-outline-info">
                            Add to Cart
                        </a>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@include file="includes/footer.jsp"%>
