<%--
    Document   : index
    Created on : Jul 7, 2023, 1:08:19 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.time.Year" %>

<%--<c:forEach items="${pageContext.request.queryString}" var="param">--%>
<%--    param: ${param}--%>
<%--</c:forEach>--%>
<c:set var="currentUrl" value="${requestScope['javax.servlet.forward.request_uri']}"></c:set>
<c:set var="queryString" value="${pageContext.request.queryString}"></c:set>

<c:choose>
    <c:when test="${queryString == null || queryString.indexOf('page=') == 0}">
        <c:set var="noPageUrl" value="${currentUrl}?"></c:set>
    </c:when>
    <c:when test="${queryString.indexOf('&page=') != -1}">
        <c:set var="noPageUrl" value="${currentUrl}?${queryString.substring(0,
                            queryString.indexOf('&page='))}&"></c:set>
    </c:when>
    <c:otherwise>
        <c:set var="noPageUrl"
               value="${currentUrl}?${queryString}&"></c:set>
    </c:otherwise>
</c:choose>

<section class="container">
    <h1 class="text-center text-info mt-1">DANH SÁCH ${entityLabelName}</h1>

    <c:if test="${pageContext.request.getParameter('error') != null}">
        <div class="alert alert-danger" role="alert">
                ${pageContext.request.getParameter('error')}
        </div>
    </c:if>

    <c:if test="${pageContext.request.getParameter('success') != null}">
        <div class="alert alert-success" role="alert">
                ${pageContext.request.getParameter('success')}
        </div>
    </c:if>

    <div class="filters my-5">
        <form action class="filters-form container">
            <c:if test="${filters != null && filters.size() >0}">
                <div class="row mb-3">
                    <c:forEach items="${filters}" var="filter">
                        <c:set var="selected" value="${pageContext.request.getParameter(filter.path)}"></c:set>
                        <div class="col-md me-5">
                            <label for="${filter.path}" class="me-1">${filter.label}:</label>
                            <select class="selectpicker" data-live-search="true" name="${filter.path}"
                                    id="${filter.path}">
                                <option value=""></option>
                                <c:if test="${filter.path == 'year' && selected == null}">
                                    <c:set var="selected" value="${Year.now().getValue()}"/>
                                </c:if>


                                <c:forEach items="${filter.items}" var="option">
                                    <c:choose>
                                        <c:when test="${option.value == selected}">
                                            <option selected value="${option.value}">${option.name}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${option.value}">${option.name}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>
                    </c:forEach>
                </div>
            </c:if>

            <div class="row">
                <input value="${pageContext.request.getParameter("kw")}" class="me-3 col-4"
                       type="text" name="kw" placeholder="Nhập từ khóa...">
                <button type="submit" class="btn btn-primary col-1">Lọc</button>
            </div>
        </form>
    </div>
    <div class="btn-group" role="group" aria-label="Basic example">
        <c:if test="${isCanCreate == true}">
            <a href="<c:url value='${rootEndpoint}/create' />" class="btn btn-info me-5">Thêm ${entityLabelName}</a>
        </c:if>
        <form action="<c:url value='${rootEndpoint}/clonebyyear'/>" method="get">
            <c:set var="selectedYear" value="${pageContext.request.getParameter('year') != null ?
                                            pageContext.request.getParameter('year') : Year.now().getValue()}"/>
            <label for="ByYear">Số năm clone từ:</label>
            <input id="ByYear" name="ByYear" value="${selectedYear - 1}"
                   placeholder="Nhập số năm clone từ"/>
            <input id="year" name="year" value="${selectedYear}" hidden/>
            <button class="btn btn-success">
                Clone từ các năm trước
            </button>
        </form>
    </div>

    <c:if test="${counter > 1}">
        <ul class="pagination mt-1">
            <c:forEach begin="1" end="${counter}" var="i">
                <li class="page-item">
                    <a class="page-link" data-page-number=${i} href="${noPageUrl}page=${i}">${i}</a>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <table class="table table-hover">
        <thead>
        <tr>
            <c:forEach items="${labels}" var="k">
                <th>${k}</th>
            </c:forEach>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${records}" var="r">
            <tr>
                <c:forEach items="${r}" var="v">
                    <td>${v}</td>
                </c:forEach>
                <td>
                    <a href="<c:url value='${rootEndpoint}/${r.get(0)}?${queryString}'/>" class="btn btn-success">Cập
                        nhật</a>
                    <a href="<c:url value='${rootEndpoint}/delete/${r.get(0)}?${queryString}'/>" class="btn btn-danger">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>

<script>
    $(function () {
        $('li.page-item.active').removeClass('active').removeAttr('aria-current');
        let pageActive = 1;
        let currentPage = (new URL(location.href)).searchParams.get("page")
        if (currentPage)
            pageActive = currentPage;
        $('a.page-link[data-page-number="' + pageActive + '"]').closest('li').addClass('active').attr('aria-current', 'page');

        $('form.filters-form').on('submit', function () {
            let form = this;
            let selects = $(form).find('select');
            if ($('input[name="kw"]').val().trim() === "")
                $('input[name="kw"]').remove();

            selects.each(function () {
                console.log(123)
                if ($(this).val() === "") {
                    let paramName = $(this).attr('name');
                    if (paramName) {
                        let paramSelect = $(form).find('select[name="' + paramName + '"]');
                        if ($(paramSelect))
                            $(paramSelect).remove();
                    }
                }
            });
        });
    });
</script>