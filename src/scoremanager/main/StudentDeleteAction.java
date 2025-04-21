package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDAO;
import tool.Action;


public class StudentDeleteAction extends Action {

	public void execute(
			HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");


		String no = "";

		Student students = new Student();

		StudentDAO sDao = new StudentDAO();


		no = request.getParameter("no");

	    request.setAttribute("no", no);



		students.setNo(no);
		students.setSchool(teacher.getSchool());


		boolean line = sDao.delete(students);
		if (line==true) {
			request.getRequestDispatcher("student_delete_done.jsp").forward(request, response);
		}
	}
}
