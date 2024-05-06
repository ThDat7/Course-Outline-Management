<%--
    Document   : index
    Created on : Jul 7, 2023, 1:08:19 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<section class="container">
    <h1 class="text-center text-info mt-1">DANH SÁCH ${entityLabelName}</h1>
    <a href="<c:url value='${rootEndpoint}/create' />" class="btn btn-info">Thêm ${entityLabelName}</a>


    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <li class="page-item"><a class="page-link" href="${action}">Tất cả</a></li>
            <c:forEach begin="1" end="${counter}" var="i">
                <c:url value="${rootEndpoint}" var="pageUrl">
                    <c:param name="page" value="${i}"></c:param>
                </c:url>
                <li class="page-item"><a class="page-link" href="${pageUrl}">${i}</a></li>
            </c:forEach>
        </ul>
    </c:if>

    <table class="table table-hover">
        <thead>
        <tr>
            <c:forEach items="${labels}" var="k">
                <th>${k}</th>
            </c:forEach>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${records}" var="r">
            <tr>
                <c:forEach items="${r}" var="v">
                    <td>${v}</td>
                </c:forEach>
                <td>
                    <a href="<c:url value='${rootEndpoint}/${r.get(0)}'/>" class="btn btn-success">Cập nhật</a>
                    <a href="<c:url value='${rootEndpoint}/delete/${r.get(0)}'/>" class="btn btn-danger">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
