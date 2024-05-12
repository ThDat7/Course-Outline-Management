<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<form:hidden path="id"/>

<jsp:include page="../utils/form-select.jsp">
    <jsp:param name="field" value="teacher"/>
    <jsp:param name="items" value="${teachers}"/>
    <jsp:param name="relationId" value="${assignOutline.teacher.id}"/>
    <jsp:param name="label" value="Giáo viên phụ trách"/>
</jsp:include>

<jsp:include page="../utils/form-select.jsp">
    <jsp:param name="field" value="course"/>
    <jsp:param name="items" value="${courses}"/>
    <jsp:param name="relationId" value="${assignOutline.course.id}"/>
    <jsp:param name="label" value="Môn học"/>
</jsp:include>

<div class="form-floating mb-3 mt-3">
    <input type="input" class="date form-control" id="assignDate" name="assignDate"
           value="<fmt:formatDate value="${assignOutline.assignDate}" pattern="dd/MM/yyyy"/>"/>
    <label for="assignDate">Ngày phân công</label>
</div>

<%--<div class="form-floating mb-3 mt-3">--%>
<%--    <form:input path="deadlineDate" class="form-control" type="date" id="deadlineDate"/>--%>
<%--    <label for="deadlineDate">Hạn chót</label>--%>
<%--</div>--%>
<div class="form-floating mb-3 mt-3">
    <input type="input" class="date form-control" id="deadlineDate" name="deadlineDate"
           value="<fmt:formatDate value="${assignOutline.deadlineDate}" pattern="dd/MM/yyyy"/>"/>

    <label for="deadlineDate">Hạn chót</label>
</div>

<div class="form-floating mb-3 mt-3">
    <form:select class="form-control" path="status" id="status">
        <form:options items="${statuses}"/>
    </form:select>
    <label for="status">Trạng thái</label>
</div>

<script>
    $(document).ready(function () {
        $('.date').datepicker({format: 'dd/mm/yyyy'});
    });
</script>