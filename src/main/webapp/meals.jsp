<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<form action="meals">
    Calories per day <input type="text" name="calories"/>
    <input type="submit" value="Отправить">
</form>
<br>
<table style=" border-style: solid; border-width:1px; width: 600px; border-collapse: collapse;">
    <thead>
    <tr style="background-color: gray;">
        <td style="width: 150px;">date</td>
        <td style="width: 80px;">description</td>
        <td style="width: 80px;">calories</td>
        <td style="width: 80px;">exceed</td>
        <td></td>
        <td style="width: 80px;">action</td>
        <td style="width: 80px;">action</td>
    </tr>
    </thead>
    <c:forEach var="meal" items="${meals}">
        <c:if test="${meal.exceed}">
            <tr style="color: red">
                <td><javatime:format value="${meal.dateTime}" pattern="dd-MM-yyyy  HH:mm"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><c:out value="${meal.exceed}"/></td>
                <td></td>
                <form action="meals">
                <td><input type="submit" name="act" value="delete"/></td>
                <td><input type="submit" name="act" value="update"/></td>
                </form>
            </tr>
        </c:if>
        <c:if test="${not meal.exceed}">
            <tr style="color: green">
                <td><javatime:format value="${meal.dateTime}" pattern="dd-MM-yyyy  HH:mm"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><c:out value="${meal.exceed}"/></td>
                <td></td>
                <form action="meals">
                    <td><input type="submit" name="act" value="delete"/></td>
                    <td><input type="submit" name="act" value="update"/></td>
                </form>
            </tr>
        </c:if>

    </c:forEach>
</table>

</body>
</html>