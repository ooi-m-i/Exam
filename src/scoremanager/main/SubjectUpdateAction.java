package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public void execute(
			HttpServletRequest request, HttpServletResponse response
		) throws Exception {

		// セッションからログインユーザー（教員）情報を取得
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// SubjectDAOのインスタンスを作成（DB操作に使用）
		SubjectDAO subjectDao = new SubjectDAO();

		// リクエストメソッド（GET または POST）を取得
		String method = request.getMethod();

		if (method.equalsIgnoreCase("GET")) {
			// ----- GETリクエスト時（変更フォームの表示） -----

			// パラメータから科目コードを取得
			String cd = request.getParameter("cd");

			// 科目コードと学校情報を使って、科目情報を取得
			Subject subject = subjectDao.get(cd, teacher.getSchool());

			if (subject != null) {
				// 科目が存在する場合、取得した値をリクエスト属性にセット
				request.setAttribute("cd", subject.getCd());
				request.setAttribute("name", subject.getName());
			} else {
				// 科目が存在しない場合、警告用のフラグとコードをセット
				request.setAttribute("notFound", true);
				request.setAttribute("cd", cd);
			}

			// 科目変更JSPへフォワード（フォーム表示）
			request.getRequestDispatcher("subject_update.jsp").forward(request, response);

		} else if (method.equalsIgnoreCase("POST")) {
			// ----- POSTリクエスト時（更新処理） -----

			// フォームから送信された科目コードと科目名を取得
			String cd = request.getParameter("cd");
			String name = request.getParameter("name");

			// Subjectオブジェクトを作成し、データをセット
			Subject subject = new Subject();
			subject.setCd(cd);
			subject.setName(name);
			subject.setSchool(teacher.getSchool());  // 所属学校情報もセット

			// 科目情報をDBに保存（INSERTまたはUPDATE）
			subjectDao.save(subject);

			// 更新完了画面へフォワード
			request.getRequestDispatcher("subject_update_done.jsp").forward(request, response);
		}
	}
}