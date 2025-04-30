package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
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

        // マスター取得
        ClassNumDAO classNumDao = new ClassNumDAO();
        SubjectDAO subjectDao = new SubjectDAO();

        List<ClassNum> classList = classNumDao.filter(school.getCd());
        List<Subject> subjectList = subjectDao.filter(school.getCd());

        // 入学年度リスト（直近3年）
        int currentYear = java.time.LocalDate.now().getYear();
        List<Integer> entYearList = new ArrayList<>();
        entYearList.add(currentYear);
        entYearList.add(currentYear - 1);
        entYearList.add(currentYear - 2);

        // パラメータ取得
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subjectCd");
        String testNoStr = request.getParameter("testNo");

        List<Test> testList = null;

        if (entYearStr != null && classNum != null && subjectCd != null && testNoStr != null) {
            int entYear = Integer.parseInt(entYearStr);
            int testNo = Integer.parseInt(testNoStr);

            Subject subject = new Subject();
            subject.setCd(subjectCd);

            TestDAO testDao = new TestDAO();
            testList = testDao.filter(entYear, classNum, subject, testNo, school);

            request.setAttribute("entYear", entYearStr);
            request.setAttribute("classNum", classNum);
            request.setAttribute("subjectCd", subjectCd);
            request.setAttribute("testNo", testNoStr);
            request.setAttribute("testList", testList);
        }

        // 画面に渡すマスタデータ
        request.setAttribute("entYearList", entYearList);
        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);

        request.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(request, response);
    }
}
