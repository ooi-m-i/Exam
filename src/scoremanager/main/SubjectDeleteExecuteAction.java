package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// セッションからログインユーザー（教員）情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// リクエストパラメータから科目コードを取得
		String cd = request.getParameter("cd");

		// 入力値をリクエスト属性に保存（エラー時の再表示などに使用可能）
		request.setAttribute("cd", cd);

		// Subject オブジェクトを作成し、必要な情報をセット
		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setSchool(teacher.getSchool());

		// DAO を使って削除処理を実行
		SubjectDAO subjectDao = new SubjectDAO();
		boolean result = subjectDao.delete(subject);

		if (result) {
			// 削除成功 → 完了画面へ
			request.getRequestDispatcher("subject_delete_done.jsp").forward(request, response);
		} else {
			// 削除失敗 → エラーメッセージとともに削除確認画面へ戻す
			request.setAttribute("error", "削除に失敗しました。");
			request.setAttribute("subject", subject);
			request.getRequestDispatcher("subject_delete.jsp").forward(request, response);
		}
	}
}
