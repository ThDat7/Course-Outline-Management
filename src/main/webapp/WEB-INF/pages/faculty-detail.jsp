<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form:hidden path="id"/>
<jsp:include page="../utils/form-input.jsp">
    <jsp:param name="field" value="name"/>
    <jsp:param name="label" value="Tên khoa"/>
</jsp:include>

<jsp:include page="../utils/form-input.jsp">
    <jsp:param name="field" value="alias"/>
    <jsp:param name="label" value="Tên viết tắt"/>
</jsp:include>