package scoremanager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDAO;
import tool.Action;


public class LoginExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//ローカル変数の宣言 1
		String id = "";
		String password = "";
		TeacherDAO teacherDao = new TeacherDAO();
		Teacher teacher = null;

		//リクエストパラメータ―の取得 2
		id = request.getParameter("id");// 教員ID
		password = request.getParameter("password");//パスワード

		//DBからデータ取得 3
		teacher = teacherDao.login(id, password);//教員データ取得

		//ビジネスロジック 4
		//DBへデータ保存 5
		//レスポンス値をセット 6
		//フォワード 7
		//条件で手順4~7の内容が分岐
		if (teacher != null) {// 認証成功の場合
			// セッション情報を取得
			HttpSession session = request.getSession(true);
			// 認証済みフラグを立てる
			teacher.setAuthenticated(true);
			// セッションにログイン情報を保存
			session.setAttribute("user", teacher);

			//リダイレクト
			response.sendRedirect("main/Menu.action");
		} else {
			// 認証失敗の場合
			// エラーメッセージをセット
			List<String> errors = new ArrayList<>();
			errors.add("ログインに失敗しました。IDまたはパスワードが正しくありません。");
			request.setAttribute("e", errors);
			// 入力された教員IDをセット
			request.setAttribute("id", id);

			//フォワード
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}

	}

}
