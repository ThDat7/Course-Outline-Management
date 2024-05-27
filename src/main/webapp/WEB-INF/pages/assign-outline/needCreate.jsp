<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<table class="table table-hover">
    <thead>
    <tr>
        <th>Môn học</th>
        <th>Ngành</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${assignOutline}" var="ao">
        <tr>
            <td>${ao.courseName}</td>
            <td>${ao.majorName}</td>
            <td>
                <a href="<c:url value='/assign-outlines/create/${ao.educationProgramCourseOutlineId}/'/>"
                   class="btn btn-danger">Tạo mới</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>