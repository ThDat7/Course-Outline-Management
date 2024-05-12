<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="border">
    <div class="col-4">
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" id="type" name="type" value="${param.type}"/>
            <label for="type">Loại đánh giá</label>
        </div>
    </div>

    <div class="col-4">
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" id="method" name="method" value="${param.method}"/>
            <label for="method">Cách thức</label>
        </div>
    </div>

    <div class="col-4">
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" id="time" name="time" value="${param.time}"/>
            <label for="time">Thời điểm</label>
        </div>
    </div>

    <div class="col-4">
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" id="clos" name="clos" value="${param.clos}"/>
            <label for="clos">Clos</label>
        </div>
    </div>

    <div class="col-4">
        <div class="form-floating mb-3 mt-3">
            <input class="form-control" id="weightPercent" name="weightPercent" value="${param.weightPercent}"/>
            <label for="weightPercent">Tỷ lệ</label>
        </div>
    </div>
</div>