<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- ▼ JavaScriptバリデーションスクリプト -->
<script>
  function validateForm() {
    let isValid = true;
    const points = document.getElementsByName("points");
    const errorSpans = document.querySelectorAll(".point-error");

    // エラーメッセージの初期化
    errorSpans.forEach(span => span.textContent = "");

    for (let i = 0; i < points.length; i++) {
      const value = points[i].value.trim();
      if (value !== "") {
        const num = parseInt(value, 10);
        if (isNaN(num) || num < 0 || num > 100) {
          errorSpans[i].textContent = "0～100の範囲で入力してください";
          isValid = false;
        }
      }
    }
    return isValid;
  }
</script>

<c:import url="/common/base.jsp">
  <c:param name="title">成績登録</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績登録</h2>

      <!-- ▼ 検索条件バリデーションエラー（検索実行後にだけ表示） -->
      <c:if test="${isSearchTriggered and not empty errorMsg}">
        <div class="text-danger text-center fw-bold mb-3">${errorMsg}</div>
      </c:if>

      <!-- ▼ 検索フォーム -->
      <form action="TestRegist.action" method="get">
        <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
          <div class="col-2">
            <label class="form-label">入学年度</label>
            <select name="entYear" class="form-select">
              <option value="0">--------</option>
              <c:forEach var="year" items="${entYearList}">
                <option value="${year}" <c:if test="${year == entYear}">selected</c:if>>${year}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col-2">
            <label class="form-label">クラス</label>
            <select name="classNum" class="form-select">
              <option value="0">--------</option>
              <c:forEach var="num" items="${classList}">
                <option value="${num}" <c:if test="${num == classNum}">selected</c:if>>${num}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col-3">
            <label class="form-label">科目</label>
            <select name="subjectCd" class="form-select">
              <option value="0">--------</option>
              <c:forEach var="s" items="${subjectList}">
                <option value="${s.cd}" <c:if test="${s.cd == subjectCd}">selected</c:if>>${s.name}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col-2">
            <label class="form-label">回数</label>
            <select name="testNo" class="form-select">
              <option value="0">--------</option>
              <c:forEach begin="1" end="2" var="no">
                <option value="${no}" <c:if test="${no == testNo}">selected</c:if>>${no}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col-2 text-center mt-3">
            <input type="submit" value="検索" class="btn btn-secondary">
          </div>
        </div>
      </form>

      <!-- ▼ 成績一覧表示 -->
      <c:if test="${not empty testList}">
        <div class="mb-4">
          <c:forEach var="s" items="${subjectList}">
            <c:if test="${s.cd == subjectCd}">
              <label>科目：${s.name}（${testNo}回）</label>
            </c:if>
          </c:forEach>
        </div>

        <!-- ▼ 登録フォーム -->
        <form action="TestRegistExecute.action" method="post" onsubmit="return validateForm();" novalidate>
          <table class="table table-hover mt-4">
            <thead>
              <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学籍番号</th>
                <th>氏名</th>
                <th>点数</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="test" items="${testList}" varStatus="status">
                <tr>
                  <td>${test.student.entYear}</td>
                  <td>${test.classNum}</td>
                  <td>${test.student.no}</td>
                  <td>${test.student.name}</td>
                  <td>
                    <input type="number" name="points" value="${test.point}" class="form-control" min="0" max="100">
                    <input type="hidden" name="studentNos" value="${test.student.no}">

                    <!-- JSバリデーション -->
                    <span class="point-error text-danger small"></span>

                    <!-- サーバーサイドバリデーション -->
                    <c:if test="${not empty errorMap[test.student.no]}">
                      <div class="text-danger small">${errorMap[test.student.no]}</div>
                    </c:if>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>

          <input type="hidden" name="subjectCd" value="${subjectCd}">
          <input type="hidden" name="testNo" value="${testNo}">

          <div class="text-start mt-3">
            <input type="submit" value="登録して終了" class="btn btn-secondary">
          </div>
        </form>
      </c:if>
    </section>
  </c:param>
</c:import>
