<%--
    Document   : products
    Created on : Jul 21, 2023, 1:18:29 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="text-center text-info mt-1">Chi tiết người dùng</h1>

<c:url value="/users" var="action"/>
<form:form method="post" action="${action}" modelAttribute="user" enctype="multipart/form-data">
    <form:errors path="*" element="div"/>
    <form:hidden path="id"/>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control"
                    path="lastName" id="lastName" placeholder="Họ..."/>
        <label for="lastName">Họ</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control"
                    path="firstName" id="firstName" placeholder="Tên..."/>
        <label for="firstName">Tên</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control"
                    path="email" id="email" placeholder="Email..."/>
        <label for="email">Email</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control"
                    path="username" id="username" placeholder="Tên đăng nhập..."/>
        <label for="username">Tên đăng nhập</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="password" class="form-control"
                    path="password" id="password" placeholder="Mật khẩu..."/>
        <label for="password">Mật khẩu</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:select class="form-control" path="role" id="role">
            <form:options items="${roles}"/>
        </form:select>
        <label for="role">Chức vụ</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:select class="form-control" path="status" id="status">
            <form:options items="${statuses}"/>
        </form:select>
        <label for="status">Trạng thái</label>
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="file" class="form-control"
                    path="file" id="file"/>
        <label for="file">Avatar</label>
    </div>

    <div class="form-floating mt-1">
        <button class="btn btn-info" type="submit">Thêm người dùng</button>
    </div>
</form:form>