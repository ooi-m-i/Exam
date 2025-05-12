<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <!--  画面タイトル表示-->
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>


              <p style="background-color: #a3d8b3; padding: 5px; text-align: center;">
				登録が完了しました
			</p>

                 <!-- 5行分の空白を追加 -->
                <br><br><br><br><br>

                <!-- 科目登録画面に遷移するリンク -->
                <div class="my-3 text-start px-4 d-flex">
                <a href="<c:url value='SubjectCreate.action'/>" class="me-3">戻る</a>
                <!-- 科目管理一覧画面 -->
                <a href="<c:url value='SubjectList.action'/>">科目一覧</a>

               </div>

        </section>
    </c:param>
</c:import>
