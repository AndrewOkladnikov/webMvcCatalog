<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="home.task.domain.Category" %>
<%@ page import="home.task.domain.Product" %>
<%@ page import="home.task.repository.CategoryRepository" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Collections" %>
<html>
<head>
    <title>Список категорий</title>
    <style>
       <%@include file='style.css'%>
    </style>
</head>
<body>
<%
    List<Category> categories = (List)request.getAttribute("categories");
%>
<h3><a href="/products">Список продуктов</a></h3>
<h3><a href="/add">Добавить новый продукт</a></h3>
<hr/>
    <div class = "catBtn"><h3>Список категорий</h3></div>

    <table width="100%">
    <%
        for(Category cat:categories){
    %>
        <tr class = "tableColumn1" >
            <td width="5%" hidden><%= cat.getId()%></td>
            <td title ="Нажмите для редактирования" width="18%" ><a href="/categories/<%= cat.getId()%>/edit"><%= cat.getName()%></a></td>
            <td  width="35%"><%= cat.getAbout()%></td>
            <form method="post" action="/delete-category">
                <td width="5%"><button class = "del-but" type="submit" >Удалить</button></td>
                <input id="24" type="text" name="value" value = "<%=cat.getId()%>" hidden>
            </form>
        </tr>
<%}%>
</table>
<hr/>
<div><h3>Добавить новую категорию</h3></div>
<div>
    <form method="post" action="add-category">
        <input required type="text" name="categoryName" placeholder="Новая категория"/><br/>
        <textarea maxlength = "200" type="text" name="about" placeholder="Описание"></textarea><br/>
        <button type="submit">Добавить</button>
    </form>
</div>

<script>
    <%@include file="main.js"%>
</script>

</body>
</html>