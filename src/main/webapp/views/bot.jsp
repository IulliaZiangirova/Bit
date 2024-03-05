<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Yulia
  Date: 15.02.2024
  Time: 14:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bot</title>
</head>
<body>
<h1>Your bots</h1>
 <table>
     <tr>
         <th>step</th>
         <th>level</th>
         <th>work</th>
     </tr>
     <jsp:useBean id="bots" scope="request" type="java.util.List"/>
     <c:forEach items="${bots}" var ="bot">
         <tr>
             <td>${bot.step}</td>
             <td>${bot.level}</td>
             <td>${bot.level}</td>
         </tr>
     </c:forEach>
 </table>

<h1>Or you can create a new ot</h1>
<form method="post">
    <label>Step:
        <input type="text" name="step"><br />
    </label>

    <label>Level:
        <input type="text" name="level"><br />
    </label>

    <label>Coefficient:
        <input type="password" name="coefficient"><br />
    </label>
    <button type="submit">Submit</button>
</form>
</body>
</html>
