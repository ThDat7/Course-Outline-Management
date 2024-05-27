<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:hidden path="id"/>
<jsp:include page="../utils/form-select.jsp">
    <jsp:param name="field" value="course"/>
    <jsp:param name="items" value="${courses}"/>
    <jsp:param name="relationId"
               value="${courseOutline.course.id}"/>
    <jsp:param name="label" value="Môn học"/>
</jsp:include>

<jsp:include page="../utils/form-select.jsp">
    <jsp:param name="field" value="teacher"/>
    <jsp:param name="items" value="${teachers}"/>
    <jsp:param name="relationId"
               value="${courseOutline.teacher.id}"/>
    <jsp:param name="label" value="Giáo viên"/>
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

<div class="form-floating mb-3 mt-3">
    <form:select class="form-control" path="status" id="status">
        <form:options items="${statuses}"/>
    </form:select>
    <label for="status">Trạng thái</label>
</div>