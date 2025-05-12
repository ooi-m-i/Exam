package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectDeleteAction extends Action {

	public void execute(
			HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		// セッションからログインユーザー（教員）情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		// リクエストパラメータから科目コードを取得
		String cd = request.getParameter("cd");

		// 削除対象の科目情報を作成
		SubjectDAO sDao = new SubjectDAO();
		Subject subject = sDao.get(cd, teacher.getSchool());

		// JSPに値を渡すためにリクエストスコープにセット
		request.setAttribute("subject", subject);

		// 確認画面へ遷移
		request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
	}
}
