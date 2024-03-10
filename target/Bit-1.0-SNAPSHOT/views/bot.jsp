<%@ page import="java.util.List" %>
<%@ page import="bitmexbot.model.Bot" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Yulia
  Date: 15.02.2024
  Time: 14:50
  <%>
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bot</title>
</head>
<body>
<h1>Your bots</h1>

<form method="post" >
 <table   border="1" cellpadding="5" cellspacing="0" style="width: 50%; margin: 0 auto;">
     <thead>
        <tr>
            <th style="background-color: #f2f2f2;">step</th>
            <th style="background-color: #f2f2f2;">level</th>
            <th style="background-color: #f2f2f2;">coefficient</th>
            <th style="background-color: #f2f2f2;">work</th>
        </tr>
     </thead>
    <tbody>
     <c:forEach var ="bot" items="${requestScope.bots}" >
         <tr>
             <td>${bot.step}</td>
             <td>${bot.level}</td>
             <td>${bot.coefficient}</td>
             <td>${bot.workingIndicator}</td>

             <td><button type="submit" name="start" value=${bot.id}> Start</button></td>
             <td><button type="submit" name="stop" value=${bot.id}>Stop</button></td>

         </tr>
     </c:forEach>
    </tbody>
 </table>
</form>
<h1>Or you can create a new bot</h1>
<form method="post" >
    <label>Step:
        <input type="number" name="step"><br />
    </label>
    <label>Level:
        <input type="number" name="level"><br />
    </label>
    <label>Coefficient:
        <input type="number" name="coefficient"><br />
    </label>
    <button type="submit" name="create">Create</button>
</form>
</body>
</html>
