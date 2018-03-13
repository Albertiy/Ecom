<%--
  Created by IntelliJ IDEA.
  User: Damon
  Date: 2018/3/13
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>My Store</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
    <script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
    <script src="js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>

    <div class="container-fluid">
        <!-- 引入header.jsp -->
        <jsp:include page="header.jsp" flush="true"></jsp:include>


        <!-- 引入footer.jsp -->
        <jsp:include page="footer.jsp" flush="true"></jsp:include>
    </div>

</body>
</html>
