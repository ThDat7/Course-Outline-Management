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
    
    <%--####################### education program section #######################--%>

    <div class="container education-programs">
    <div class="row">
        <c:forEach var="semester" begin="1" end="12">
            <div class="col-4 border semester-${semester}">
                <div class="d-flex justify-content-center border-bottom p-2">Học kỳ ${semester}</div>
                <div class="ps-3 py-2 courses-content list-group list-group-numbered">
                    <c:forEach var="ep" items="${major.educationPrograms}">
                        <c:if test="${ep.semester == semester}">
                            <div class="list-group-item" data-id="${ep.course.id}">${ep.course.name}</div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
    </div>


    <select class="selectpicker courseId" data-live-search="true" multiple>
        <c:forEach var="courseId" items="${allCourses.keySet()}">
            <option value="${courseId}" data-name="${allCourses.get(courseId)}">${allCourses.get(courseId)}</option>
        </c:forEach>
    </select>

    <select class="selectpicker semester" data-live-search="true">
        <c:forEach var="i" begin="1" end="12">
            <option value="${i}">${i}</option>
        </c:forEach>
    </select>

    <button class="add-education-program btn btn-success">Thêm</button>
    <button class="delete-education-program btn btn-danger">Xóa</button>

    <%--    <input name="courses" value="2">--%>
    <div class="input-courses">
        <c:forEach var="ep" items="${major.educationPrograms}">
            <input name="courses" type="hidden" data-id="${ep.course.id}" value="${ep.semester}-${ep.course.id}">
        </c:forEach>
    </div>
    <%--####################### education program section #######################--%>

    <div class="form-floating mt-1">
        <button class="btn btn-info" type="submit">Lưu</button>
    </div>
</form:form>
<script type="text/javascript" src="<c:url value="/js/major-detail.js"/>"></script>
<style>
    div.selected {
        background-color: #454545;
    }

    .courses-content div:hover {
        background-color: #454545;
    }
</style>