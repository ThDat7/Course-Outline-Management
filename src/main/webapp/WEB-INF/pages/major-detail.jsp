<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1 class="text-center text-info mt-1">Chi tiết ${entityLabelName}</h1>
<c:url value="${rootEndpoint}" var="action"/>
<form:form method="post" action="${action}" modelAttribute="${entityName}">
    <form:errors path="*" element="div"/>
    <form:hidden path="id"/>

    <jsp:include page="../utils/form-input.jsp">
        <jsp:param name="field" value="name"/>
        <jsp:param name="label" value="Tên ngành"/>
    </jsp:include>

    <jsp:include page="../utils/form-input.jsp">
        <jsp:param name="field" value="alias"/>
        <jsp:param name="label" value="Tên viết tắt"/>
    </jsp:include>

    <jsp:include page="../utils/form-select.jsp">
        <jsp:param name="field" value="faculty"/>
        <jsp:param name="items" value="${faculties}"/>
        <jsp:param name="relationId" value="${major.faculty.id}"/>
        <jsp:param name="label" value="Khoa"/>
    </jsp:include>

    <div class="form-floating mt-1">
        <button class="btn btn-info" type="submit">Lưu</button>
    </div>
</form:form>