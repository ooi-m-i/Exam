package scoremanager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;


public class LoginAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    request.getRequestDispatcher("login.jsp").forward(request, response);
	}

}
