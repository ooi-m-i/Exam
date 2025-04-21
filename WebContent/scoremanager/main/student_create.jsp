<%-- 学生登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >

	<c:param name="title">
	得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>

			<form action="StudentCreateExecute.action" method="post">
				<div class="" id="filter">
					<div class="col-4 pb-3">
						<label class="form-label" for="student-ent_year-insert">入学年度</label><br>
						<select class="form-select" style="width:300%;" id="student-ent_year-insert" name="ent_year">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<%-- 現在のyearと選択されていたent_yearが一致していた場合selectedを追記 --%>
								<option value="${year}" <c:if test="${year==ent_year}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
						<c:if test="${not empty errors.e1}">
							<div class="mt-2 text-warning">${errors.get("e1")}</div>
						</c:if>
					</div>

					<div class="col-4 pb-3">
						<label class="form-label" for="student-no-insrt">学生番号</label><br>
							<input type="text" class="form-control" style="width:300%;" value="${no}" placeholder="学生番号を入力してください" id="student-no-insert" name="no" required>
							<c:if test="${not empty errors.e2}">
								<div class="mt-2 text-warning">${errors.get("e2")}</div>
							</c:if>
					</div>

					<div class="col-4 pb-3">
						<label class="form-label" for="student-name-insert">氏名</label><br>
							<input type="text" class="form-control" style="width:300%;" value="${name}" placeholder="氏名を入力してください" id="student-name-insert" name="name" required>
					</div>

					<div class="col-4 pb-3">
						<label class="form-label" for="student-class_num-insert">クラス</label><br>
						<select class="form-select" style="width:300%;" id="student-class_num-insert" name="class_num">

							<c:forEach var="num" items="${class_num_set}">
								<%-- 現在のnumと選択されていたclass_numが一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>

					</div>

					<div class="col-2 pb-3">
						<button class="btn btn-secondary" type="submit" id="filter-button">登録して終了</button>
					</div>

					<a href="StudentList.action">戻る</a>
				</div>
			</form>

		</section>
	</c:param>
</c:import>