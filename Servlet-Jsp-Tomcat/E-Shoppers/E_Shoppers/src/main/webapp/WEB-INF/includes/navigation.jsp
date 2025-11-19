<%--
  Created by IntelliJ IDEA.
  User: peripatetic
  Date: 11/15/25
  Time: 3:26 AM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib prefix="sec" uri="http://com.imran/functions" %>
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
                <c:choose>
                    <c:when test="${sec:isAuthenticated(pageContext.request)}">
                        <a class="nav-link" href="#" onclick="logout()">
                            Logout
                            [${sec:getCurrentUser(pageContext.request).firstName}]
                        </a>
                        <script>
                            function logout() {
                                document.getElementById("logoutForm").submit();
                            }
                        </script>
                        <form style="visibility: hidden" id="logoutForm"
                              action="<c:url value="/logout"/>" method="post">
                        </form>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link" href="<c:url value="/login"/>">
                            Login
                        </a>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>
