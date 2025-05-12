<%-- 科目変更JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >

	<c:param name="title">
	得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>

			<form action="SubjectUpdate.action" method="post">
				<div class="" id="filter">

					<%-- 科目コードの表示 --%>
					<div class="col-4 pb-3">
						<label class="form-label" for="subject-cd-update">科目コード</label><br>

						<%-- 画面設計書には「readonly属性を設定して表示」とあるが、フォームじゃないため設定不可 --%>
						<%-- とりあえず見た目を優先して作成 --%>
						<input type="hidden" value="${cd}" name="cd">
						<div style="text-indent:1em;">${cd}</div>

						<%-- ↓もし設計書通りにするなら…（見た目がフォームになる）↓ --%>
						<%-- <input type="text" value="${cd}" name="cd" class="form-control" id="subject-cd-update" readonly> --%>


						<%-- 科目が存在しない場合に警告を表示 --%>
						<c:if test="${not empty notFound}">
							<div class="text-warning pt-2">科目が存在していません</div>
						</c:if>
					</div>

					<%-- 科目名の入力欄（必須） --%>
					<div class="col-4 pb-3">
						<label class="form-label" for="subject-name-update">科目名</label><br>
						<input type="text" class="form-control" style="width:300%;" value="${name}" id="subject-name-update" name="name" required>
					</div>

					<div>
						<button type="submit" class="btn btn-primary mb-3">変更</button>
					</div>

					<a href="SubjectList.action">戻る</a>
				</div>
			</form>

		</section>
	</c:param>
</c:import>
