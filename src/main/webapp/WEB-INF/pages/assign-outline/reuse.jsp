<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.time.Year" %>


<c:set var="currentUrl" value="${requestScope['javax.servlet.forward.request_uri']}"></c:set>

<c:set var="year" value="${pageContext.request.getParameter('year')}"></c:set>
<c:if test="${year == null}">
    <c:set var="year" value="${Year.now().getValue()}"/>
</c:if>

<a href="<c:url value='/assign-outlines/re-use-all/${year}'/>"
   class="btn btn-success">Sử dụng lại tất cả</a>

<table class="table table-hover">
    <thead>
    <tr>
        <th></th>
        <th>Môn học</th>
        <th>Ngành</th>
        <th>Năm biên soạn</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${assignOutline}" var="ao">
        <tr>
            <td>${ao.educationProgramCourseOutlineId}</td>
            <td>${ao.courseName}</td>
            <td>${ao.majorName}</td>
            <td>${ao.yearPublished}</td>
            <td>
                <a href="<c:url value='/assign-outlines/re-use/${ao.educationProgramCourseOutlineId}/${ao.courseOutlineId}'/>"
                   class="btn btn-success">Sử dụng lại</a>
                <a href="<c:url value='/assign-outlines/create/${ao.educationProgramCourseOutlineId}/'/>"
                   class="btn btn-danger">Tạo mới</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>