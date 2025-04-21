<%-- 学生変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >

	<c:param name="title">
	得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>

			<form action="StudentUpdateExecute.action" method="post">
				<div class="" id="filter">
					<div class="col-4 pb-3">
						<label class="form-label" for="student-ent_year-update">入学年度</label><br>
						<input type="hidden" value="${ent_year}" name="ent_year">
						${ent_year}
					</div>

					<div class="col-4 pb-3">
						<label class="form-label" for="student-no-update">学生番号</label><br>
						<input type="hidden" value="${no}" name="no">
						${no}
					</div>

					<div class="col-4 pb-3">
						<label class="form-label" for="student-name-insert">氏名</label><br>
							<input type="text" class="form-control" style="width:300%;" value="${name}" placeholder="氏名を入力してください" id="student-name-update" name="name" required>
					</div>

					<div class="col-4 pb-3">
						<label class="form-label" for="student-class_num-update">クラス</label><br>
						<select class="form-select" style="width:300%;" id="student-class_num-update" name="class_num">
							<c:forEach var="num" items="${class_num_set}">
								<%-- 現在のnumと選択されていたclass_numが一致していた場合selectedを追記 --%>
								<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-2 form-check text-center mb-3">
						<label class="form-check-label" for="student-f3-check">在学中
							<%-- パラメーターis_attendが存在している場合checkedを追記 --%>
							<input class="form-check-input" type="checkbox"
							id="student-is_attend-check" name="is_attend" value="t"
							<c:if test="${is_attend==true}">checked</c:if> />
						</label>
					</div>

					<div>
						<button type="submit" class="btn btn-primary mb-3">変更</button>
					</div>

					<a href="StudentList.action">戻る</a>
				</div>
			</form>

		</section>
	</c:param>
</c:import>