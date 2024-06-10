<%--
    Document   : index
    Created on : Jul 7, 2023, 1:08:19 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<section class="container">
    <h1 class="text-center text-info mt-1">DANH SÁCH YÊU CẦU TÀI KHOẢN</h1>


    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <li class="page-item"><a class="page-link" href="${action}">Tất cả</a></li>
            <c:forEach begin="1" end="${counter}" var="i">
                <c:url value="/" var="pageUrl">
                    <c:param name="page" value="${i}"></c:param>
                </c:url>
                <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
            </c:forEach>
        </ul>
    </c:if>

    <table class="table table-hover">
        <thead>
        <tr>
            <th>Id</th>
            <th>Tên</th>
            <th>Chức vụ</th>
            <th>Email</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="u">
            <tr>
                <td>${u.id}</td>
                <td>${u.lastName} ${u.firstName}</td>
                <td>${u.role.toString()}</td>
                <td>${u.email}</td>
                <td>
                    <c:set var="role" value="${u.role.name().toLowerCase()}"/>
                    <a href="<c:url value='/users/pending/${role}/${u.id}'/>" class="btn btn-success">Chi tiết</a>
                    <a href="<c:url value='/users/pending/${role}/delete/${u.id}'/>" class="btn btn-danger">Xóa yêu cầu</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
