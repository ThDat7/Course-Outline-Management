<%--
    Document   : products
    Created on : Jul 21, 2023, 1:18:29 PM
    Author     : admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<h1 class="text-center text-info mt-1">Chi tiết ${entityLabelName}</h1>
<c:url value="${student.user.status.name() == 'PENDING' ?
                   '/students/pending/accept' :
                  rootEndpoint}" var="action"/>
<form:form method="post" action="${action}" modelAttribute="${entityName}" enctype="multipart/form-data">
  <form:errors path="*" element="div"/>
  <form:hidden path="id"/>
  <form:hidden path="user.id"/>

  <jsp:include page="../utils/form-input.jsp">
    <jsp:param name="field" value="user.lastName"/>
    <jsp:param name="label" value="Họ"/>
  </jsp:include>

  <jsp:include page="../utils/form-input.jsp">
    <jsp:param name="field" value="user.firstName"/>
    <jsp:param name="label" value="Tên"/>
  </jsp:include>

  <jsp:include page="../utils/form-input.jsp">
    <jsp:param name="field" value="studentCode"/>
    <jsp:param name="label" value="Mssv"/>
  </jsp:include>

  <jsp:include page="../utils/form-input.jsp">
    <jsp:param name="field" value="schoolYear"/>
    <jsp:param name="label" value="Năm học"/>
  </jsp:include>

  <div class="form-floating mb-3 mt-3">
    <form:select path="major" items="${majorList}"
                 itemLabel="name" itemValue="id"
                 class="selectpicker form-select"></form:select>
  </div>

  <jsp:include page="../utils/form-input.jsp">
    <jsp:param name="field" value="user.email"/>
    <jsp:param name="label" value="Email"/>
  </jsp:include>

  <jsp:include page="../utils/form-input.jsp">
    <jsp:param name="field" value="user.phone"/>
    <jsp:param name="label" value="Điện thoại"/>
  </jsp:include>

  <jsp:include page="../utils/form-input.jsp">
    <jsp:param name="field" value="user.username"/>
    <jsp:param name="label" value="Tên đăng nhập"/>
  </jsp:include>

  <jsp:include page="../utils/form-input.jsp">
    <jsp:param name="field" value="user.password"/>
    <jsp:param name="label" value="Mật khẩu"/>
  </jsp:include>

  <div class="form-floating mb-3 mt-3">
    <form:select path="user.status" items="${statusList}"
                 itemLabel="name" itemValue="name"
                 class="selectpicker form-select"></form:select>
  </div>

  <div class="form-floating mb-3 mt-3">
    <form:select path="user.role" items="${roleList}"
                 itemLabel="name" itemValue="name"
                 class="selectpicker form-select"></form:select>
  </div>

  <div class="form-floating mb-3 mt-3">
    <input type="file" class="form-control"
           name="avatar" id="avatar"/>
    <label for="avatar">Avatar</label>

    <form:hidden path="user.image" id="image"/>
  </div>
  <img class="preview-avatar" src="#" style="max-height: 300px"/>


  <div class="form-floating mt-1">
    <button class="btn btn-info" type="submit">
      ${student.user.status.name() == 'PENDING' ?
            'Chấp nhận' : 'Lưu'}
    </button>
  </div>
</form:form>
<script>
  $(document).ready(function () {
    const oldImg = $('#image').val()
    if (oldImg) {
      $('.preview-avatar').attr('src', oldImg)
    }

    $('#avatar').change(function () {
      let file = this.files[0];
      let reader = new FileReader();
      reader.onload = function (e) {
        $('.preview-avatar').attr('src', e.target.result);
      };
      reader.readAsDataURL(file);
    });
  });
</script>