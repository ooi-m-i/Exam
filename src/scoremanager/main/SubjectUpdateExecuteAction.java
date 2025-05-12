package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションからログインユーザー（教員）情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// リクエストパラメータから科目コードと科目名を取得
		String cd = request.getParameter("cd");
		String name = request.getParameter("name");

		// 入力値をリクエスト属性に保存（エラー時の再表示などに使用可能）
		request.setAttribute("cd", cd);
		request.setAttribute("name", name);

		// Subjectオブジェクトを作成して値をセット
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());

		// DAOを使って保存（INSERTまたはUPDATE）
		SubjectDAO subjectDao = new SubjectDAO();
		boolean result = subjectDao.save(subject);

		if (result) {
			// 成功時に完了画面へ遷移
			request.getRequestDispatcher("subject_update_done.jsp").forward(request, response);
		}
	}
}
