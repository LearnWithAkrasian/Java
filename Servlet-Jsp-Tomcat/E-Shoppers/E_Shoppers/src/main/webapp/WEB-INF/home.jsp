<%--
  Created by IntelliJ IDEA.
  User: peripatetic
  Date: 10/23/25
  Time: 5:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.imran.dto.ProductDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>All Products</title>
</head>
<body>

    <% List<ProductDto> products = (List<ProductDto>) request.getAttribute("products"); %>

    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
        </tr>
        </thead>

        <% for (ProductDto product : products) {%>
        <tr>
            <td>
                <%= product.getName() %>
            </td>
            <td>
                <%= product.getPrice() %>
            </td>
            <td>
                <%= product.getDescription() %>
            </td>
        </tr>
        <%}%>
    </table>
</body>
</html>
