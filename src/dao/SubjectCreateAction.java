package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

/**
 * 科目作成フォームを表示するアクション
 */
public class SubjectCreateAction extends Action {

    @Override
    //public：呼び出し元（FrontController）からアクセス可能であることを示します
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        // 文字化け対策（必要なら）
    	request.setCharacterEncoding("UTF-8");
        // jspフォワード先：subject_create.jsp
    	request.getRequestDispatcher("subject_create.jsp")
           .forward(request, response);
    }
}

