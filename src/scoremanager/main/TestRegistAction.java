package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import tool.Action;

public class TestRegistAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // ▼ マスター取得
        ClassNumDAO classNumDao = new ClassNumDAO();
        SubjectDAO subjectDao = new SubjectDAO();

        List<String> classList = classNumDao.filter(school);
        List<Subject> subjectList = subjectDao.filter(school);

        // ▼ 入学年度リスト（直近3年）
        int currentYear = java.time.LocalDate.now().getYear();
        List<Integer> entYearList = new ArrayList<>();
        entYearList.add(currentYear);
        entYearList.add(currentYear - 1);
        entYearList.add(currentYear - 2);

        // ▼ パラメータ取得
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subjectCd");
        String testNoStr = request.getParameter("testNo");

        // ▼ 検索ボタンが押されたかどうか（URLに何らかのクエリが含まれているか）
        boolean isSearchTriggered = entYearStr != null || classNum != null || subjectCd != null || testNoStr != null;
        request.setAttribute("isSearchTriggered", isSearchTriggered);

        // ▼ 必ず渡すマスタデータ
        request.setAttribute("entYearList", entYearList);
        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);

        // ▼ エラー時に再表示する
        if (request.getAttribute("errorMap") != null) {
            request.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(request, response);
            return;
        }

        // ▼ 不完全チェック（いずれか未選択 または "0"）
        boolean isIncomplete =
            entYearStr == null || entYearStr.equals("0") ||
            classNum == null || classNum.equals("0") ||
            subjectCd == null || subjectCd.equals("0") ||
            testNoStr == null || testNoStr.equals("0");

        if (isIncomplete && isSearchTriggered) {
            request.setAttribute("errorMsg", "入学年度とクラスと科目と回数を選択してください");

            // 選択状態の保持
            request.setAttribute("entYear", entYearStr);
            request.setAttribute("classNum", classNum);
            request.setAttribute("subjectCd", subjectCd);
            request.setAttribute("testNo", testNoStr);

            request.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(request, response);
            return;
        }

        // ▼ パース前に再チェック（安全対策）
        if (entYearStr == null || testNoStr == null) {
            request.setAttribute("errorMsg", "不正なアクセスです（再度条件を選択してください）");
            request.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(request, response);
            return;
        }

        // ▼ parseInt 安心して実行できる
        int entYear = Integer.parseInt(entYearStr);
        int testNo = Integer.parseInt(testNoStr);

        Subject subject = new Subject();
        subject.setCd(subjectCd);

        TestDAO testDao = new TestDAO();
        List<Test> testList = testDao.filter(entYear, classNum, subject, testNo, school);

        // ▼ 選択状態と結果をセット
        request.setAttribute("entYear", entYearStr);
        request.setAttribute("classNum", classNum);
        request.setAttribute("subjectCd", subjectCd);
        request.setAttribute("testNo", testNoStr);
        request.setAttribute("testList", testList);

        request.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(request, response);
    }
}
