<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="home.task.domain.Category" %>
<%@ page import="home.task.domain.Product" %>
<%@ page import="home.task.repository.CategoryRepository" %>
<%@ page import="java.util.*" %>
<%@ page import="java.util.Collections" %>
<html>
<head>
    <title>Список товаров</title>
    <style>
       <%@include file='style.css'%>
    </style>
</head>

<body>
<%
    CategoryRepository categoryRepository = (CategoryRepository)request.getAttribute("categoryRepository");
    List<Category> categories = (List)categoryRepository.findAll();
    List<Product> products = (List)request.getAttribute("products");
    List<String> catNames = new ArrayList<>();
    for(Category cat:categories)catNames.add(cat.getName());
    Collections.sort(catNames);
%>

<h3><a href="/add">Добавить новый продукт</a></h3>
<h3><a href="/categories">Список категорий</a></h3>
<hr/>
<div class = "catBtn"><h3>Список продуктов</h3></div>

<form method="get" action="/products">
    <label>Категории товаров</label>
    <td><select style = "background-color:#336600 ;color:#ffffff" name="selected">
    <option value=>Все товары</option>
<% for(String cat:catNames){ %>
    <option ><%= cat%></option>
<%}%>
    </select></td>
    <input style = "background-color:#6666ff ;color:#ffffff" type="submit" value="Найти" ></button>
</form>
<hr/>

    <table width="100%">
    <%
        for(Product prod:products){
        String categoryName = null;
        if(!(prod.getCategory() == null)){
            categoryName = ((Category)categoryRepository.findById(prod.getCategory().getId()).get()).getName();
        }
    %>
        <tr class = "tableColumn" >
            <td width="5%" hidden><%= prod.getId()%></td>
            <td title ="Нажмите для редактирования" width="8%" ><a href="/products/<%= prod.getId()%>/edit"><%= prod.getName()%></a></td>
            <td  width="15%"><%= prod.getAbout()%></td>
            <td width="15%"><%= prod.getManufacturer()%></td>
            <td width="5%"><%= prod.getPrice()%> </td>
            <td width="10%"><%= prod.getCreatedDate()%></td>
            <%
                if(prod.getCategory()!=null){
            %>  <td width="10%"><a href="/categories/<%= prod.getCategory().getId()%>/edit"><%= categoryName%></a></td>
            <%
                }else{
            %> <td width="10%"><%= categoryName%></td><%;}%>
            <td width="10%"><img src="/img/<%= prod.getPicture()%>" width="60" height="45"></td>
            <form method="post" action="/delete-product">
                <td width="5%"><button class = "del-but" type="submit" >Удалить</button></td>
                <input id="24" type="text" name="value" value = "<%=prod.getId()%>" hidden>
            </form>
        </tr>

<%}%>
</table>
<script>
    <%@include file="main.js"%>
</script>

</body>
</html>