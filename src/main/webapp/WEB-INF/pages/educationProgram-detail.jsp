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

    <jsp:include page="../utils/form-select.jsp">
        <jsp:param name="field" value="major"/>
        <jsp:param name="items" value="${majors}"/>
        <jsp:param name="relationId" value="${educationProgram.major.id}"/>
        <jsp:param name="label" value="Ngành"/>
    </jsp:include>

    <jsp:include page="../utils/form-input.jsp">
        <jsp:param name="field" value="schoolYear"/>
        <jsp:param name="label" value="Năm học"/>
    </jsp:include>


    <%--####################### education program section #######################--%>

    <div class="container education-programs">
    <div class="row">
        <c:forEach var="semester" begin="1" end="12">
            <div class="col-4 border semester-${semester}">
                <div class="d-flex justify-content-center border-bottom p-2">Học kỳ ${semester}</div>
                <div class="ps-3 py-2 courses-content list-group list-group-numbered">
                    <c:forEach var="epc" items="${educationProgram.educationProgramCourses}">
                        <c:if test="${epc.semester == semester}">
                            <c:choose>
                                <c:when test="${epc.courseOutline != null}">
                                    <div class="list-group-item" data-id="${epc.course.id}">
                                        <a href="<c:url value='/course-outlines/${epc.courseOutline.id}'/>"
                                           class="text-primary">${epc.course.name}</a>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div href="<c:url value='/course-outlines/${epc.courseOutline.id}'/>"
                                         class="list-group-item"
                                         data-id="${epc.course.id}">${epc.course.name}</div>
                                </c:otherwise>
                            </c:choose>
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
    <button class="remove-course-outline btn btn-warning">Hủy gán đề cương</button>

    <%--    <input name="courses" value="2">--%>
    <div class="input-courses">
        <c:forEach var="epc" items="${educationProgram.educationProgramCourses}">
            <input name="courses" type="hidden" data-id="${epc.course.id}" value="${epc.semester}-${epc.course.id}">
        </c:forEach>
    </div>
    <%--####################### education program section #######################--%>

    <div class="form-floating mt-1">
        <button class="btn btn-info" type="submit">Lưu</button>
    </div>
</form:form>

<script type="text/javascript" src="<c:url value="/js/education-program-detail.js"/>"></script>
<style>
    div.selected {
        background-color: #c2c2c2;
    }

    .courses-content div:hover {
        background-color: #c2c2c2;
    }
</style>