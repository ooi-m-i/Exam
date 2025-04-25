package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import tool.Action;

public class TestRegistAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 各マスターデータを取得
        ClassNumDAO classNumDao = new ClassNumDAO();
        SubjectDAO subjectDao = new SubjectDAO();

        List<ClassNum> classList = classNumDao.filter(school.getCd());
        List<Subject> subjectList = subjectDao.filter(school.getCd());

        // 入学年度リスト（最新3年）
        int currentYear = java.time.LocalDate.now().getYear();
        List<Integer> entYearList = new ArrayList<>();
        entYearList.add(currentYear);
        entYearList.add(currentYear - 1);
        entYearList.add(currentYear - 2);

        // 入力パラメータ取得
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subjectCd");
        String testNoStr = request.getParameter("testNo");

        List<Test> testList = null;

        if (entYearStr != null && classNum != null && subjectCd != null && testNoStr != null) {
            int entYear = Integer.parseInt(entYearStr);
            int testNo = Integer.parseInt(testNoStr);

            StudentDAO studentDao = new StudentDAO();
            List<Student> studentList = studentDao.filter(school, entYear, classNum, true);

            testList = new ArrayList<>();

            for (Student s : studentList) {
                Test test = new Test();
                test.setStudent(s);
                test.setClassNum(s.getClassNum());
                test.setSchool(school);
                test.setNo(testNo);
                test.setPoint(0); // 初期値

                Subject subject = new Subject();
                subject.setCd(subjectCd);
                test.setSubject(subject);

                testList.add(test);
            }

            request.setAttribute("subjectCd", subjectCd);
            request.setAttribute("testNo", testNoStr);
        }

        // JSPに渡す
        request.setAttribute("entYearList", entYearList);
        request.setAttribute("classList", classList);
        request.setAttribute("subjectList", subjectList);
        request.setAttribute("testList", testList);

        request.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(request, response);
    }
}
