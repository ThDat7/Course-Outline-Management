<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div class="form-floating mb-3 mt-3">
    <form:input type="text" class="form-control"
                path="${param.field}" id="${param.field}"/>
    <label for="${param.field}">${param.label}</label>
    <form:errors path="${param.field}" cssClass="text-danger"/>
</div>