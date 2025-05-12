package scoremanager.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ログインチェック
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/scoremanager/Login.action");
            return;
        }

        // パラメータ取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // 入力チェック（未入力）
        if (cd == null || cd.isEmpty() || name == null || name.isEmpty()) {
            request.setAttribute("errorCd", "このフィールドを入力してください");
            request.setAttribute("cd", cd);
            request.setAttribute("name", name);
            request.getRequestDispatcher("/scoremanager/main/subject_create.jsp").forward(request, response);
            return;
        }

        // 科目コード3文字チェック
        if (cd.length() != 3) {
            request.setAttribute("errorCd", "科目コードは3文字で入力してください");
            request.setAttribute("cd", cd);
            request.setAttribute("name", name);
            request.getRequestDispatcher("/scoremanager/main/subject_create.jsp").forward(request, response);
            return;
        }

        // 重複チェック
        try {
            SubjectDAO subjectDAO = new SubjectDAO();
            Subject existing = subjectDAO.get(cd, teacher.getSchool());

            if (existing != null) {
                request.setAttribute("errorCd", "科目コードが重複しています");
                request.setAttribute("cd", cd);
                request.setAttribute("name", name);
                request.getRequestDispatcher("/scoremanager/main/subject_create.jsp").forward(request, response);
                return;
            }

            // 登録処理（DBへ接続）
            Class.forName("org.h2.Driver");
            String url    = "jdbc:h2:tcp://localhost/~/school";
            String dbUser = "sa", dbPass = "";

            try (Connection conn = DriverManager.getConnection(url, dbUser, dbPass);
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO subject (cd, name, school_cd) VALUES (?, ?, ?)")) {
                ps.setString(1, cd);
                ps.setString(2, name);
                ps.setString(3, teacher.getSchool().getCd());
                ps.executeUpdate();
            }

            // 登録完了 → 完了画面へリダイレクト
            request.getRequestDispatcher("/scoremanager/main/subject_create_done.jsp")
                   .forward(request, response);
            return;


        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("科目登録処理でエラーが発生しました", e);
        }
    }
}
