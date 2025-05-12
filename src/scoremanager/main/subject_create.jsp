<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>

    <c:param name="content">
        <section class="me-4">
            <!-- 科目登録の画面タイトル -->
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>

            <!-- フォーム 登録-->
            <form method="POST" action="<c:url value='/scoremanager/main/SubjectCreateExecute.action'/>">
           <!--  <form method="POST" action="<c:url value='/SubjectCreateExecute.action'/>"> -->



                <div class="row mb-3">
                    <div class="col-12">
                        <!-- 科目コードのlabelを作成 -->
                        <label for="cd" class="form-label">科目コード</label>

                        <!--name属性 cd 最大文字数=3-->
                        <input type="text" class="form-control" id="cd" name="cd" value="${cd}" required placeholder="科目コードを入力してください" maxlength="3" />
                        <!-- 空欄の場合エラー表示 -->
                        <c:if test="${not empty errorCd}">
                        <div class="mt-2 text-warning">${errorCd}</div>

                        </c:if>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-12">
                        <!--科目名のlabelを作成 -->
                        <label for="name" class="form-label">科目名</label>
                        <!--name属性 name 最大文字数=20-->
                        <!-- placeholder入力欄に文字を表示する-->
                        <input type="text" class="form-control" id="name" name="name" value="${name}" required placeholder="科目名を入力してください" maxlength="20" />

                        <c:if test="${not empty errorName}">
                        <div class="text-danger">${errorName}</div>
                        </c:if>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-4">
                        <!-- 登録ボタンを表示 -->
                        <button type="submit" class="btn btn-primary">登録</button>
                    </div>
                    <!-- 戻るリンク -->
                    <a href="javascript:history.back()">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>
