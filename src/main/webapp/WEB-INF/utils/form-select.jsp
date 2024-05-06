<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<c:set var="map" value='${param.items.replace("{", "").replace("}", "")}'/>
<div class="form-floating mb-3 mt-3">
    <form:select class="selectpicker form-select" data-live-search="true" id="${param.field}" name="${param.field}"
                 path="${param.field}">
        <c:forEach items='${map.split(", ")}' var="c">
            <c:set var="option" value='${c.split("=")}'/>

            <c:choose>
                <c:when test="${option[0] == param.relationId}">
                    <option value="${option[0]}" selected>${option[1]}</option>
                </c:when>
                <c:otherwise>
                    <option value="${option[0]}">${option[1]}</option>
                </c:otherwise>
            </c:choose>

        </c:forEach>
    </form:select>

    <label for="${param.field}" class="form-label">${param.label}</label>
</div>