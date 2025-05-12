<%-- 科目削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >

	<c:param name="title">
	得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-4 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>

			<form action="SubjectDeleteExecute.action" method="post">
				<div class="col-10 pb-3">

					<%-- 画面設計書で、name属性/Value属性 → subject_cd/subject_nameとなっているが、cd/nameで作成 --%>
					「${subject.name}（${subject.cd}）」を削除してもよろしいですか

					<input type="hidden" name="cd" value="${subject.cd}">
				</div>

				<div class="pb-5">
					<button type="submit" class="btn btn-danger mb-3">削除</button>
				</div>

				<a href="SubjectList.action">戻る</a>
			</form>

		</section>
	</c:param>
</c:import>
