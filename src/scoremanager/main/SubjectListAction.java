package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectListAction extends Action {

    @Override

    public void execute(

        HttpServletRequest request, HttpServletResponse response

    ) throws Exception {

        // セッションからログインユーザ情報を取得

        HttpSession session = request.getSession();

        Teacher teacher = (Teacher) session.getAttribute("user");

        // 科目一覧を取得

        SubjectDAO sDAO = new SubjectDAO();

        List<Subject> subjects = sDAO.filter(teacher.getSchool());

        // JSPにデータを渡す

        request.setAttribute("subjects", subjects);

        // subject_list.jsp へフォワード

        request.getRequestDispatcher("subject_list.jsp").forward(request, response);

    }

}
