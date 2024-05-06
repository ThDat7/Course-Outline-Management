<%--
    Document   : products
    Created on : Jul 21, 2023, 1:18:29 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<h1 class="text-center text-info mt-1">Chi tiết ${entityLabelName}</h1>
<c:url value="${rootEndpoint}" var="action"/>
<form:form method="post" action="${action}" modelAttribute="${entityName}">
    <form:errors path="*" element="div"/>
    <tiles:insertAttribute name="form-field-content"/>

    <div class="form-floating mt-1">
        <button class="btn btn-info" type="submit">Lưu</button>
    </div>
</form:form>