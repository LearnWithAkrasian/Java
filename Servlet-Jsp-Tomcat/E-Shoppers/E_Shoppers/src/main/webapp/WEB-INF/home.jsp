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
    <div class="col-6 mb-4">
        <c:if test="${cart != null && cart.cartItems.size() > 0}">
            <div class="card-header">
                <h4>Your Cart</h4>
            </div>
            <div class="card-body>">
                <p>
                    Total Item:
                    <span class='badge bg-primary rounded-pill ms-2'>
                        <c:out value="${cart.totalItem}"/>
                    </span>
                </p>
                <p>
                    Total Price:
                    <span class='badge bg-primary rounded-pill ms-2'>
                        <c:out value="${cart.totalPrice}"/>
                    </span>
                </p>
                <p><a class="btn btn-outline-info" href="<c:url value="/checkout"/>">Checkout</a></p>
            </div>
        </c:if>
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
                        <a href="#" class="card-link btn btn-outline-info" onclick="addToCart(${product.id})">
                            Add to Cart
                        </a>

                        <form style="visibility: hidden"
                              id="addToCart_${product.id}"
                              method="post"
                              action="<c:url value="/add-to-cart?productId=${product.id}"/>">
                        </form>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
<div class="row">
    <c:if test="${message != null}">
        <div class="alert alert-success">
            <c:out value="${message}" escapeXml="false"/>
        </div>
    </c:if>
</div>

<%@include file="includes/footer.jsp"%>

<script>
    function addToCart(productId) {
        let form = document.getElementById("addToCart_" + productId);
        form.submit();
    }
</script>
