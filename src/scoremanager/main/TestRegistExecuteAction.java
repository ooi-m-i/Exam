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

        // セッションからログイン中の教師情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // リクエストパラメータを取得
        String[] studentNos = request.getParameterValues("studentNos");
        String[] points = request.getParameterValues("points");
        String subjectCd = request.getParameter("subjectCd");
        String testNoStr = request.getParameter("testNo");

        // テスト番号をint型に変換
        int testNo = Integer.parseInt(testNoStr);

        // エラーメッセージ格納用マップと登録用テストリストの準備
        Map<String, String> errorMap = new HashMap<>();
        List<Test> testList = new ArrayList<>();

        StudentDAO studentDao = new StudentDAO();

        // 学生ごとの点数をループ処理
        for (int i = 0; i < studentNos.length; i++) {
            String no = studentNos[i];
            String pointStr = points[i];

            Integer point = null;

            // 点数が空でなければバリデーションとパースを実施
            if (pointStr != null && !pointStr.trim().isEmpty()) {
                try {
                    int parsed = Integer.parseInt(pointStr);
                    if (parsed < 0 || parsed > 100) {
                        throw new NumberFormatException();
                    }
                    point = parsed;
                } catch (NumberFormatException e) {
                    // バリデーションエラーがある場合はエラーメッセージを記録し次の学生へ
                    errorMap.put(no, "0～100の範囲で入力してください");
                    continue;
                }
            }

            // 学生情報を取得
            Student student = studentDao.get(no);

            // テスト情報を生成し、各項目を設定
            Test test = new Test();
            test.setStudent(student);
            test.setClassNum(student.getClassNum());
            test.setSchool(school);
            test.setNo(testNo);
            test.setPoint(point);

            // 科目情報を設定
            Subject subject = new Subject();
            subject.setCd(subjectCd);
            test.setSubject(subject);

            // 登録対象のテストリストに追加
            testList.add(test);
        }

        // エラーがある場合は元の入力画面へ戻し、エラー内容と入力値を表示
        if (!errorMap.isEmpty()) {
            request.setAttribute("errorMap", errorMap);
            request.setAttribute("testList", testList);
            request.setAttribute("subjectCd", subjectCd);
            request.setAttribute("testNo", testNoStr);
            request.getRequestDispatcher("/scoremanager/main/test_regist.jsp").forward(request, response);
            return;
        }

        // エラーがない場合は、テストデータをDBに保存
        TestDAO testDao = new TestDAO();
        testDao.save(testList);

        // 完了画面に遷移
        request.getRequestDispatcher("/scoremanager/main/test_regist_done.jsp").forward(request, response);
    }
}
