<%--
    Document   : products
    Created on : Jul 21, 2023, 1:18:29 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<h1 class="text-center text-info mt-1">Chi tiết người dùng</h1>

<c:choose>
    <c:when test="${isPending}">
        <c:url value="/users/pending" var="action"/>
    </c:when>
    <c:otherwise>
        <c:url value="/users" var="action"/>
    </c:otherwise>
</c:choose>

<form:form method="post" action="${action}" modelAttribute="user" enctype="multipart/form-data">
    <form:errors path="*" element="div"/>
    <form:hidden path="id"/>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control"
                    path="lastName" id="lastName" placeholder="Họ..."/>
        <label for="lastName">Họ</label>
        <form:errors path="lastName" element="div" cssClass="text-danger" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control"
                    path="firstName" id="firstName" placeholder="Tên..."/>
        <label for="firstName">Tên</label>
        <form:errors path="firstName" element="div" cssClass="text-danger" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control"
                    path="email" id="email" placeholder="Email..."/>
        <label for="email">Email</label>
        <form:errors path="email" element="div" cssClass="text-danger" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="text" class="form-control"
                    path="username" id="username" placeholder="Tên đăng nhập..."/>
        <label for="username">Tên đăng nhập</label>
        <form:errors path="username" element="div" cssClass="text-danger" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:input type="password" class="form-control"
                    path="password" id="password" placeholder="Mật khẩu..."/>
        <label for="password">Mật khẩu</label>
        <form:errors path="password" element="div" cssClass="text-danger" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:select class="form-control" path="role" id="role">
            <form:options items="${roles}"/>
        </form:select>
        <label for="role">Chức vụ</label>
        <form:errors path="role" element="div" cssClass="text-danger" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <form:select class="form-control" path="status" id="status">
            <c:forEach items="${statuses}" var="s">
                <c:choose>
                    <c:when test="${user.status == s}">
                        <option value="${s}" selected>${s.toString()}</option>
                    </c:when>
                    <c:otherwise>
                        <option value="${s}">${s.toString()}</option>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </form:select>
        <label for="status">Trạng thái</label>
        <form:errors path="status" element="div" cssClass="text-danger" />
    </div>
    <div class="form-floating mb-3 mt-3">
        <input type="file" class="form-control"
                    name="avatar" id="avatar"/>
        <label for="avatar">Avatar</label>

        <form:hidden path="image" id="image"/>
    </div>
    <img class="preview-avatar" src="#" style="max-height: 300px"/>

    <div class="form-floating mt-1">
        <button class="btn btn-info" type="submit">Lưu</button>
    </div>
</form:form>
<script>
$(document).ready(function () {
    const oldImg = $('#image').val()
    if (oldImg) {
        $('.preview-avatar').attr('src', oldImg)
    }

    $('#avatar').change(function () {
            let file = this.files[0];
            let reader = new FileReader();
            reader.onload = function (e) {
                $('.preview-avatar').attr('src', e.target.result);
            };
            reader.readAsDataURL(file);
        });
    });
</script>