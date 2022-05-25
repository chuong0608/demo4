<%--
  Created by IntelliJ IDEA.
  User: chuon
  Date: 5/19/2022
  Time: 10:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <style>
        body{
            font-family: 'Helvetica Neue', Helvetica, Arial;
            font-size: 14px;
            line-height: 20px;
            font-weight: 400;
            color: #3b3b3b;
            -webkit-font-smoothing: antialiased;
            font-smoothing: antialiased;
        }
        table{
            border: solid  1px black;
        }
        td,th{
            border-top: 1px solid #ECF0F1;
            padding: 10px;
        }
        td{
            border-left: 1px solid #ECF0F1;
            border-right: 1px solid #ECF0F1;
        }
        th{
            background-color: #4ECDC4;
        }
        tr:nth-of-type(even) td{
            background-color: #4ECDC4;
        }
    </style>
</head>
<body>
<h1>danh sách</h1>
<form action="customers">
            <input type="text" name="key">
            <button>Search</button>
<table>
    <tr>
        <td>
            <a href="/customers?action=create">Tạo mới sản phẩm</a>
        </td>
    </tr>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>price</th>
        <th>select</th>
    </tr>
    <c:forEach items="${dsKH}" var="cus">
        <tr>
            <td>${cus.id}
            </td>
            <td>${cus.name}
            </td>
            <td>
                    ${cus.age}
            </td>
            <td>
                <a href="/customers?action=view&id=${cus.id}">Xem</a>,
                <a href="/customers?action=edit&id=${cus.id}">Sửa</a>,
                <a href="/customers?action=delete&id=${cus.id}">Xóa</a>
            </td>

        </tr>

    </c:forEach>
</table>
</form>

</body>
</html>
