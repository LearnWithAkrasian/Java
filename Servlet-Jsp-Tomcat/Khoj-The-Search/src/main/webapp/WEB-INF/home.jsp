<%@taglib prefix="sec" uri="http://imran.com/functions" %>
<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container">
    <div class="jumbotron">
        <c:if test="${sec:isAuthenticated(pageContext.request)}">
            <h1> Hello <c:out value="${sec:getCurrentUser(pageContext.request).username}"/>, Welcome to our Astana </h1>
        </c:if>
    </div>

    <form class="form-horizontal" role="form"
          method="post"
          action="<c:url value="/home"/>">
        <div class="form-group">
            <label for="inputValues">
                Input values ( N.B Use only Non-negative Integer numbers )
            </label>
            <input type="text"
                   class="form-control"
                   id="inputValues"
                   value="${numberListDTO.inputValues}"
                   name="inputValues"
                   placeholder="The input only contains digits, spaces and commas."/>

            <c:if test="${errors.inputValues != null}">
                <small class="text-danger">${errors.inputValues}</small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="searchValue">
                Search Values ( N.B Use only Non-negative Integer numbers )
            </label>
            <input type="text"
                   class="form-control"
                   id="searchValue"
                   value="${numberListDTO.searchValue}"
                   name="searchValue"
                   placeholder="you can search multiple numbers. use only digits, commas and spaces"/>
            <c:if test="${errors.searchValue != null}">
                <small class="text-danger">${errors.searchValue}</small>
            </c:if>
        </div>
        <c:choose>
            <c:when test="${sec:isAuthenticated(pageContext.request)}">
                <input type="submit" value="Khoj">
            </c:when>
            <c:otherwise>
                 <span>
                    You are not logged In :). Please log in first ->
                    <a class="btn-link" href="<c:url value="/login"/>">
                        Log in
                    </a>
                </span>
            </c:otherwise>
        </c:choose>
    </form>
</div>

<%@include file="includes/footer.jsp"%>
