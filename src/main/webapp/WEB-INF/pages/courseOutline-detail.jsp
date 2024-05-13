<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:hidden path="id"/>
<jsp:include page="../utils/form-select.jsp">
    <jsp:param name="field" value="assignOutline.course"/>
    <jsp:param name="items" value="${courses}"/>
    <jsp:param name="relationId"
               value="${courseOutline.assignOutline != null ? courseOutline.assignOutline.course.id : ''}"/>
    <jsp:param name="label" value="Môn học"/>
</jsp:include>

<div class="form-floating mb-3 mt-3">
    <form:textarea class="form-control"
                   path="content" id="content"/>
    <label for="content">Nội dung</label>
</div>

<%--#######################################--%>
<c:set var="countCurrentAssessment" value="0"/>

<c:forEach var="assessment" items="${courseOutline.courseAssessments}">
    <c:set var="countCurrentAssessment" value="${countCurrentAssessment + 1}"/>
    <jsp:include page="../utils/course-assessment-item.jsp">
        <jsp:param name="type" value="${assessment.type}"/>
        <jsp:param name="method" value="${assessment.method}"/>
        <jsp:param name="time" value="${assessment.time}"/>
        <jsp:param name="clos" value="${assessment.clos}"/>
        <jsp:param name="weightPercent" value="${assessment.weightPercent}"/>
    </jsp:include>
</c:forEach>

<c:forEach begin="${countCurrentAssessment + 1}" end="4">
    <jsp:include page="../utils/course-assessment-item.jsp"/>
</c:forEach>
<%--#######################################--%>

<c:set var="schoolYear" value="${[0,0]}"/>

<c:if test="${schoolYears.size() == 1}">
    <c:set var="schoolYear" value="${[schoolYears.get(0),0]}"/>
</c:if>
<c:if test="${schoolYears.size() == 2}">
    <c:set var="schoolYear" value="${[schoolYears.get(0),schoolYears.get(1)]}"/>
</c:if>

<div class="form-floating mb-3 mt-3">
    <form:select class="form-control" path="status" id="status">
        <form:options items="${statuses}"/>
    </form:select>
    <label for="status">Trạng thái</label>
</div>

<select class="selectpicker schoolYears" name="schoolYears" data-live-search="true" multiple>
    <c:forEach var="i" begin="2010" end="2025">
        <c:set var="j" value="${2025-i+2010}"/>

        <c:choose>
            <c:when test="${j == schoolYear[0]}">
                <option value="${j}" selected>${j}</option>
            </c:when>
            <c:when test="${j == schoolYear[1]}">
                <option value="${j}" selected>${j}</option>
            </c:when>
            <c:otherwise>
                <option value="${j}">${j}</option>
            </c:otherwise>
        </c:choose>

    </c:forEach>
</select>