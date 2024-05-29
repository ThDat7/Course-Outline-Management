<%--
    Document   : header
    Created on : Jul 21, 2023, 1:12:19 PM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="action" value=""/>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark nav-pills">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">E-commerce Website</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav me-auto">
                <li class="nav-item home">
                    <a class="nav-link" href=<c:url value='/'/>>Trang chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href=<c:url value='/users/'/>>Quản lý Người dùng</a>
                </li>
                <li class=" nav-item">
                    <a class="nav-link" href=<c:url value="/users/pending/"/>>Quản lý Yêu cầu tài khoản</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href=<c:url value="/faculties/"/>>Quản lý Khoa</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href=<c:url value="/majors/"/>>Quản lý Ngành</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href=<c:url value="/courses/"/>>Quản lý Môn học</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href=<c:url value="/education-programs/"/>>Quản lý Chương trình đào tạo</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href=<c:url value="/assign-outlines/"/>>Quản lý Phân công biên soạn</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href=<c:url value="/course-outlines/"/>>Quản lý Đề cương</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<script>
    $(document).ready(function () {
        let exactMath = false;
        let homeUrl = $('li.nav-item.home a').attr('href');
        $('a.nav-link').each(function () {
            let navHref = $(this).attr('href')

            if (location.pathname === navHref) {
                $('li.nav-item.active').removeClass('active').removeAttr('aria-current');
                $(this).addClass('active').attr('aria-current', 'page');
                exactMath = true
            }
        })

        if (!exactMath) {
            $('a.nav-link').each(function () {
                if ($(this).attr('href') === homeUrl) return;

                let isActive = location.pathname.startsWith($(this).attr('href'));
                if (isActive) {
                    $('li.nav-item.active').removeClass('active').removeAttr('aria-current');
                    $(this).addClass('active').attr('aria-current', 'page');
                }
            })
        }

    });
</script>