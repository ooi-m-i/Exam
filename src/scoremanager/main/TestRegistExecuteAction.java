package scoremanager.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDAO;
import dao.TestDAO;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String[] studentNos = request.getParameterValues("studentNos");
        String[] points = request.getParameterValues("points");
        String subjectCd = request.getParameter("subjectCd");
        String testNoStr = request.getParameter("testNo");

        int testNo = Integer.parseInt(testNoStr);
        Map<String, String> errorMap = new HashMap<>();
        List<Test> testList = new ArrayList<>();

        StudentDAO studentDao = new StudentDAO();

        for (int i = 0; i < studentNos.length; i++) {
            String no = studentNos[i];
            String pointStr = points[i];

            Integer point = null;
            if (pointStr != null && !pointStr.trim().isEmpty()) {
                try {
                    int parsed = Integer.parseInt(pointStr);
                    if (parsed < 0 || parsed > 100) {
                        throw new NumberFormatException();
                    }
                    point = parsed;
                } catch (NumberFormatException e) {
                    errorMap.put(no, "0～100の範囲で入力してください");
                    continue;
                }
            }

            Student student = studentDao.get(no);

            Test test = new Test();
            test.setStudent(student);
            test.setClassNum(student.getClassNum());
            test.setSchool(school);
            test.setNo(testNo);
            test.setPoint(point);

            Subject subject = new Subject();
            subject.setCd(subjectCd);
            test.setSubject(subject);

            testList.add(test);
        }

        if (!errorMap.isEmpty()) {
            request.setAttribute("errorMap", errorMap);
            request.setAttribute("testList", testList);
            request.setAttribute("subjectCd", subjectCd);
            request.setAttribute("testNo", testNoStr);
            request.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(request, response);
            return;
        }

        TestDAO testDao = new TestDAO();
        testDao.save(testList);

        request.getRequestDispatcher("/scoremanager/main/test_regist_done.jsp").forward(request, response);
    }
}
