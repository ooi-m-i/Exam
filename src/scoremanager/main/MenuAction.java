package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;


public class MenuAction extends Action {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

	    request.getRequestDispatcher("menu.jsp").forward(request, response);
	}

}
