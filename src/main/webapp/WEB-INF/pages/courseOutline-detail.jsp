<%@ page import="com.dat.pojo.CourseOutline" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="<c:url value="/js/course-outline-detail.js"/>"></script>

<div class="err-msg text-danger">
</div>
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
<table class="table table-bordered mt-3" id="assessmentTable">
    <thead>
    <tr>
        <th>Thành phần đánh giá</th>
        <th>Bài đánh giá</th>
        <th>Thời điểm</th>
        <th>CDR môn học</th>
        <th>Tỷ lệ %</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>
            <button class="btn btn-primary addAssessmentComponent">
                Thêm thành phần đánh giá
            </button>
        </td>
        <td colspan="5"></td>
    </tr>
    </tbody>
</table>
<%--#######################################--%>

<div class="form-floating mb-3 mt-3">
    <form:select class="form-control" path="status" id="status">
        <form:options items="${statuses}"/>
    </form:select>
    <label for="status">Trạng thái</label>
</div>


<div class="form-floating mb-3 mt-3">
    <input type="input" class="date form-control" id="deadlineDate" name="deadlineDate"
           value="<fmt:formatDate value="${courseOutline.deadlineDate}" pattern="yyyy-MM-dd"/>"/>

    <label for="deadlineDate">Hạn chót</label>
</div>

<script>
    let type = 0;
    let method = 0;
    <c:forEach items="${courseOutline.courseAssessments}" var="assessment">
    addAssessmentComponent(new Event(''));
    $("tr").eq(type + 2).find(".component-input").val("${assessment.type}");
    <c:forEach items="${assessment.assessmentMethods}" var="method">
    addAssessmentMethod(new Event(''), type);
    $('#method-' + method + ' .method-input').val("${method.method}");
    $('#method-' + method + ' .time-input').val("${method.time}");
    $('#method-' + method + ' .clo-input').val("${method.clos}");
    $('#method-' + method + ' .weight-input').val("${method.weightPercent}");
    method++;
    </c:forEach>
    type++;
    </c:forEach>
</script>