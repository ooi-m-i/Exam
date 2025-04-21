package tool;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns={"*.action"})
public class FrontController extends HttpServlet {

	public void doGet(
			HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {

		try{

			//1文字目の削除
			String path = request.getServletPath().substring(1);

			//文字を置き換えて、該当のファイルパスに変換
			String name = path.replace(".a", "A").replace('/', '.');

			System.out.println("★ servlet path ->" + request.getServletPath());
			System.out.println("★ class name ->" + name);
			
			//アクションのクラスに関連付けられた、
			//パスに該当するクラスのコンストラクタを呼び出し、インスタンス化
			Action action = (Action)Class.forName(name).
					getDeclaredConstructor().newInstance();

			//フォワード先のURLを取得し、返却する処理
			action.execute(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}

	public void doPost(
			HttpServletRequest request, HttpServletResponse response
	) throws ServletException, IOException {
		doGet(request, response);

	}

}
