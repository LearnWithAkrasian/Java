<%@include file="includes/header.jsp"%>
<%@include file="includes/navigation.jsp"%>

<div class="container">
    <br/>

    <h2 class="h2">REST API</h2>
    <hr class="mb-4">

    <form class="form-horizontal" role="form"
          action="<c:url value="/api-rest"/>"
          method="post">
        <div class="form-group">
            <label for="startTime">Starting Time ( Ex- yyyy-MM-dd HH:mm:ss )</label>
            <input type="text" class="form-control"
                   id="startTime"
                   name="startTime"
                   value="${restApiDTO.startTime}"/>

            <c:if test="${errors.startTime != null}">
                <small class="text-danger"> ${errors.startTime} </small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="endingTime">Ending Time ( Ex- yyyy-MM-dd HH:mm:ss )</label>
            <input type="text" class="form-control"
                   id="endingTime"
                   name="endingTime"
                   value="${restApiDTO.endingTime}"/>

            <c:if test="${errors.endingTime != null}">
                <small class="text-danger"> ${errors.endingTime} </small>
            </c:if>
        </div>
        <div class="form-group">
            <label for="userId">User ID</label>
            <input type="number" class="form-control"
                   id="userId"
                   name="userId"
                   value="${restApiDTO.userId}"/>

            <c:if test="${errors.userId != null}">
                <small class="text-danger"> ${errors.userId} </small>
            </c:if>
        </div>
        <hr class="mb-4">
        <div class="form-group">
            <button class="btn btn-primary btn-lg"
                    type="submit">
                Submit
            </button>
        </div>
    </form>
</div>
<%@include file="includes/footer.jsp"%>