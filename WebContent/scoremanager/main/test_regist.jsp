<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">成績登録</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <h2 class="h4 bg-light py-2 px-3 rounded">成績登録</h2>

    <form action="TestRegist.action" method="post" class="my-3">
      <div class="d-flex gap-3 mb-2">
        <label>入学年度：
          <select name="entYear" class="form-select">
            <c:forEach var="year" items="${entYearList}">
              <option value="${year}">${year}</option>
            </c:forEach>
          </select>
        </label>
        <label>クラス：
          <select name="classNum" class="form-select">
            <c:forEach var="c" items="${classList}">
              <option value="${c.class_num}">${c.class_num}</option>
            </c:forEach>
          </select>
        </label>
        <label>科目：
          <select name="subjectCd" class="form-select">
            <c:forEach var="s" items="${subjectList}">
              <option value="${s.cd}">${s.name}</option>
            </c:forEach>
          </select>
        </label>
        <label>回数：
          <select name="testNo" class="form-select">
            <c:forEach begin="1" end="2" var="no">
              <option value="${no}">${no}</option>
            </c:forEach>
          </select>
        </label>
        <input type="submit" value="検索" class="btn btn-secondary align-self-end">
      </div>
    </form>

    <c:if test="${not empty testList}">
      <form action="TestRegistExecute.action" method="post">
        <table class="table table-bordered mt-4">
          <thead>
            <tr>
              <th>入学年度</th><th>クラス</th><th>学籍番号</th><th>氏名</th><th>点数</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="test" items="${testList}">
              <tr>
                <td>${test.student.entYear}</td>
                <td>${test.classNum}</td>
                <td>${test.student.no}</td>
                <td>${test.student.name}</td>
                <td>
                  <input type="number" name="points" value="${test.point}" class="form-control" min="0" max="100">
                  <input type="hidden" name="studentNos" value="${test.student.no}">
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
        <input type="hidden" name="subjectCd" value="${subjectCd}">
        <input type="hidden" name="testNo" value="${testNo}">
        <input type="submit" value="登録して終了" class="btn btn-primary mt-3">
      </form>
    </c:if>
  </c:param>
</c:import>
