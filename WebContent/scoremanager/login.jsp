<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >

	<c:param name="title">
	得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">

	<div class="mx-auto border border-secondary border-opacity-10 border-3 text-center">
		<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4" style="text-align: center">ログイン</h2>

		<c:if test="${not empty errors.e}">
			${errors.get("e")}
		</c:if>

		<form class="mx-auto py-3" style="width: 50%" action="LoginExecute.action" method="post">
		  <div class="form-floating mb-3">
		    <label for="ID" class="form-label text-secondary text-opacity-10" style="top: -0.6rem; font-size: 0.9rem;">ID</label>
		    <input type="text" class="form-control" id="ID" name="id" required>
		  </div>
		  <div class="form-floating mb-3">
		    <label for="password" class="form-label text-secondary text-opacity-10" style="top: -0.6rem; font-size: 0.9rem;">パスワード</label>
		    <input type="password" class="form-control" id="password" name="password" required>
		    <div class="text-center pt-3">
		    	<input type="checkbox" id="togglePassword"> パスワードを表示
		    </div>
		  </div>
		  <div class="text-center">
		  	<button type="submit" class="btn btn-primary" >ログイン</button>
		  </div>
		</form>
	</div>

	<script>
	  document.getElementById("togglePassword").addEventListener("change", function() {
	    const pwInput = document.getElementById("password");
	    if (this.checked) {
	      pwInput.type = "text";  // 表示
	    } else {
	      pwInput.type = "password";  // 非表示
	    }
	  });
	</script>

	</c:param>


</c:import>