<!DOCTYPE HTML>
<%@ page import="home.task.domain.Category" %>
<%@ page import="home.task.domain.Product" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Добавление товара</title>
<style>
   <%@include file='style.css' %>
</style>
</head>
<body>
<h3><a href="/products">Список продуктов</a></h3>
<h3><a href="/categories">Список категорий</a></h3>
<%
    List<Category> categories = (List)request.getAttribute("categories");
    List<String> catNames = new ArrayList<>();
    for(Category cat:categories)catNames.add(cat.getName());
    Collections.sort(catNames);
%>
    <form method="post" name="1" action="add-product" enctype="multipart/form-data">
        <div class = "catBtn"><h1>Добавление товара </h1></div><br/>
        <label for="1">Название товара </label>
        <input required id="1" type="text" name="name" value=><br/>
        <label for="2">Описание </label>
        <textarea id="2" type="text" name="about" value=></textarea><br/>
        <label for="3">Производитель </label>
        <input id="3" type="text" name="manufacturer" value=><br/>
        <label for="4">Картинка </label>
        <input id="4" type="file" name="picture" value=><br/>
        <label for="5">Дата добавления  </label>
        <input id="5" type="date" name="createdDate" ><br/>
        <label for="6">Цена </label>
        <input id="6" type="number" name="price" value=><br/>
        <label>Категория</label>
        <td><select class = "select" name="selected">
                <option value=""></option>
            <% for(String cat:catNames){ %>
                <option ><%= cat%></option>
            <%}%>
                </select></td><br/>
                <button class="add-product" type="submit">Добавить товар</button>

    </form>
</div>



<script></script>

</body>
</html>