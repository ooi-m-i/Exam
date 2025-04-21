<%-- 学生登録完了JSP --%>
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

			<div style="background-color: #a3d8b3; padding: 5px; text-align: center;">
				変更が完了しました
			</div>


			<div style="margin-top: 10rem;">
				<a href="StudentList.action">学生一覧</a>
			</div>

		</section>
	</c:param>
</c:import>