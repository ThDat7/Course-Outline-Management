<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@page import="java.time.Year" %>


<c:set var="currentUrl" value="${requestScope['javax.servlet.forward.request_uri']}"></c:set>
<c:set var="queryString" value="${pageContext.request.queryString}"></c:set>


<div class="mb-5 mt-3 btn-group w-100">
    <c:choose>
        <c:when test="${currentUrl.contains('/assign-outlines/re-use')}">
            <a class="btn btn-primary w-50 text-white"
               href="<c:url value='/assign-outlines/re-use'/>">Sử dụng lại</a>
        </c:when>
        <c:otherwise>
            <a class="btn btn-dark w-50 text-white"
               href="<c:url value='/assign-outlines/re-use'/>">Sử dụng lại</a>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${currentUrl.contains('/assign-outlines/need-create')}">
            <a class="btn btn-primary w-50 text-white"
               href="<c:url value='/assign-outlines/need-create'/>">Cần tạo mới</a>
        </c:when>
        <c:otherwise>
            <a class="btn btn-dark w-50 text-white"
               href="<c:url value='/assign-outlines/need-create'/>">Cần tạo mới</a>
        </c:otherwise>
    </c:choose>
</div>

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
    <h2 class="text-center text-info mt-1">DANH SÁCH PHÂN CÔNG BIÊN SOẠN</h2>
    <div class="filters my-5">
        <form action class="filters-form container">
            <div class="row mb-3">
                <c:set var="selected" value="${pageContext.request.getParameter('year')}"></c:set>
                <div class="col-md me-5">
                    <label for="year" class="me-1">Năm học:</label>
                    <select class="selectpicker" data-live-search="true" name="year"
                            id="year">
                        <option value=""></option>
                        <c:set var="currentYear" value="${Year.now().getValue()}"/>

                        <c:if test="${selected == null}">
                            <c:set var="selected" value="${currentYear}"/>
                        </c:if>

                        <c:forEach var="y" begin="2010" end="${currentYear + 5}">
                            <c:set var="y" value="${(currentYear + 5) - y + 2010}"/>
                            <c:choose>
                                <c:when test="${y == selected}">
                                    <option selected value="${y}">${y}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${y}">${y}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>

                <c:set var="selected" value="${pageContext.request.getParameter('course')}"></c:set>
                <div class="col-md me-5">
                    <label for="course" class="me-1">Môn học:</label>
                    <select class="selectpicker" data-live-search="true" name="course"
                            id="course">
                        <option value=""></option>
                        <c:forEach items="${courses}" var="c">
                            <c:choose>
                                <c:when test="${c.id == selected}">
                                    <option selected value="${c.id}">${c.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${c.id}">${c.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>

                <c:set var="selected" value="${pageContext.request.getParameter('major')}"></c:set>
                <div class="col-md me-5">
                    <label for="course" class="me-1">Ngành:</label>
                    <select class="selectpicker" data-live-search="true" name="major"
                            id="major">
                        <option value=""></option>
                        <c:forEach items="${majors}" var="m">
                            <c:choose>
                                <c:when test="${m.id == selected}">
                                    <option selected value="${m.id}">${m.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${m.id}">${m.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="row">
                <input value="${pageContext.request.getParameter("kw")}" class="me-3 col-4"
                       type="text" name="kw" placeholder="Nhập từ khóa...">
                <button type="submit" class="btn btn-primary col-1">Lọc</button>
            </div>
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

    <tiles:insertAttribute name="table"/>
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