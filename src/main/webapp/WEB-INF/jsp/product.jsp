<!DOCTYPE HTML>
<%@ page import="home.task.domain.Category" %>
<%@ page import="home.task.domain.Product" %>
<%@ page import="java.util.*" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Редактирование продукта</title>
<style>
   <%@include file='style.css' %>
</style>
</head>
<body>
<%
    String categoryName = "";
    List<Category> categories = (List)request.getAttribute("categories");
    List<String> catNames = new ArrayList<>();
    for(Category cat:categories)catNames.add(cat.getName());
    Collections.sort(catNames);
    Product product = (Product)request.getAttribute("product");
    for(Category cat:categories){
                if(product.getCategory()!=null && cat.getId()==product.getCategory().getId())
                categoryName =cat.getName();
            }
%>
<img class = "sidebar" src="/img/<%= product.getPicture()%>" width="560" height="445"><br/>
    <form method="post" name="1" action="/add-product" enctype="multipart/form-data">
        <div class = "catBtn"><h1>Редактирование продукта </h1></div><br/>
        <input  type="text" name="id" value=<%= product.getId()%>  hidden><br/>
        <label >Название товара </label>
        <input  maxlength = "200" type="text" name="name" value="<%= product.getName()%>"><br/>
        <label >Описание </label>
        <textarea maxlength = "200" type="text" name="about" value=><%= product.getAbout()%></textarea><br/>
        <label >Производитель </label>
        <input  type="text" name="manufacturer" value="<%= product.getManufacturer()%>"><br/>
        <label >Картинка </label>
        <input  type="file" value="<%= product.getPicture()%>" name="picture" ><br/>

        <label for="5">Дата добавления  </label>
        <input id="5" type="date" name="createdDate" value=<%= product.getCreatedDate()%> ><br/>
        <label for="6">Цена </label>
        <input id="6" type="number" name="price" value=<%= product.getPrice()%>><br/>
        <label>Категория</label>
        <td><select style = "background-color:#336600 ;color:#ffffff" name="selected">
                <option name=<%= categoryName%>><%= categoryName%></option>
            <% for(String cat:catNames){ %>
                <option ><%= cat%></option>
            <%}%>
                </select></td><hr/>
                <button class="edit-but" type="submit">Сохранить</button>
                <hr/>
    </form>
</div>

<h3><a href="/products">Список продуктов</a></h3>
<h3><a href="/categories">Список категорий</a></h3>
<script></script>

</body>
</html>