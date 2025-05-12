<%-- 成績参照（学生） --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >

	<c:param name="title">
	得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績参照(学生)</h2>

			<form action="TestListExecuteSubject.action">
			  <div class="row border mx-3 mb-3 py-2 align-items-center rounded" style="display:flex; flex-direction:row;" id="filter">

			  	<div class="col-2 ms-3">科目情報</div>

			    <!-- 入学年度 -->
			    <div class="col-2">
			      <label class="form-label" for="student-f1-select">入学年度</label>
			      <select class="form-select" id="student-f1-select" name="f1">
			        <option value="0">--------</option>
			        <c:forEach var="year" items="${ent_year_set}">
			          <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
			        </c:forEach>
			      </select>
			    </div>

			    <!-- クラス -->
			    <div class="col-2">
			      <label class="form-label" for="student-f2-select">クラス</label>
			      <select class="form-select" id="student-f2-select" name="f2">
			        <option value="0">--------</option>
			        <c:forEach var="num" items="${class_num_set}">
			          <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
			        </c:forEach>
			      </select>
			    </div>

			    <!-- 科目 -->
			    <div class="col-3">
			      <label class="form-label" for="student-f3-select">科目</label>
			      <select class="form-select" id="student-f3-select" name="f3">
			        <option value="0">--------</option>
			        <c:forEach var="subject" items="${subjects}">
			          <option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
			        </c:forEach>
			      </select>
			    </div>

			    <!-- 検索ボタン -->
			    <div class="col-2">
			      <button type="submit" class="btn btn-secondary" id="filter-button">検索</button>
			    </div>

			    <!-- エラーメッセージ -->
			    <div class="mt-2 text-warning">${errors.get("e")}</div>

			  </div>
			</form>

			<form action="TestListExecuteStudent.action">
			  <div class="row border mx-3 mb-3 py-2 align-items-center rounded"  id="filter">

			  <div class="col-2 ms-3">学生情報</div>

			    <!-- 学生番号 -->
			    <div class="col-4">
			      <label class="form-label" for="student-f4-select">学生番号</label>
			      <input type="text" class="form-control" style="width: 100%;" placeholder="学生番号を入力してください" id="student-name-update" value="${f4}" name="f4" required>
			    </div>

			    <!-- 検索ボタン -->
			    <div class="col-3 d-flex align-items-end">
			      <button class="btn btn-secondary ms-3" id="filter-button">検索</button>
			    </div>
			  </div>
			</form>


			<!-- 検索結果表示 -->
			<c:choose>
				<c:when test="${not empty test_list_student}">
					<div>氏名：${student.name}(${student.no})</div>
					<table class="table table-hover">
						<tr>
						    <th>科目名</th>
						    <th>科目コード</th>
						    <th>回数</th>
						    <th>点数</th>
						    <th></th>
						    <th></th>
						</tr>

						<c:forEach var="test" items="${test_list_student}">
							<tr>
								<td>${test.subjectName}</td>
								<td>${test.subjectCd}</td>
								<td>${test.num}</td>
								<td>${test.point}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:otherwise>
					<div>氏名：${student.name}(${student.no})</div>
					<div>成績情報が存在しませんでした</div>
				</c:otherwise>
			</c:choose>

		</section>
	</c:param>
</c:import>