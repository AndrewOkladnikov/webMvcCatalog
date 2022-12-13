<!DOCTYPE HTML>
<%@ page import="home.task.domain.Category" %>
<%@ page import="home.task.domain.Product" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Редактирование категории</title>
<style>
   <%@include file='style.css' %>
</style>
</head>
<body>
<%
    Category category = (Category)request.getAttribute("category");
%>
    <form method="post" action="/category/<%= category.getId()%>" >
        <div class = "catDiv"><h1>Редактирование категории </h1></div><br/>
        <label for="1">Название категории </label>
        <td ><input id="1" type="text" maxlength = "200" name="name" value="<%= category.getName()%>"></td><br/>
        <label for="2">Описание </label>
        <td ><textarea maxlength = "200" id="2" type="text" name="about" value=><%= category.getAbout()%></textarea></td><br/>
                <button class="edit-but" type="submit">Сохранить</button>
                <hr/>
    </form>
</div>
<a href="/categories">Список категорий</a><br/>
<a href="/products">Список продуктов</a>
<script></script>

</body>
</html>