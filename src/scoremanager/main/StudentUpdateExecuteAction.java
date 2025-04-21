package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDAO;
import tool.Action;


public class StudentUpdateExecuteAction extends Action {

	public void execute(
			HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");


		String entYearStr="";
		String no = "";
		String name = "";
		String classNum = "";
		String isAttendStr = "";
		int entYear = 0;
		Boolean isAttend = false;
		Student students = new Student();

		StudentDAO sDao = new StudentDAO();

		entYearStr = request.getParameter("ent_year");
		no = request.getParameter("no");
		name = request.getParameter("name");
		isAttendStr = request.getParameter("is_attend");
		classNum = request.getParameter("class_num");

		entYear = Integer.parseInt(entYearStr);

		if (isAttendStr != null) {
			isAttend = true;
		}


		request.setAttribute("ent_year", entYear);
	    request.setAttribute("no", no);
		request.setAttribute("name", name);
		request.setAttribute("class_num", classNum);
		request.setAttribute("is_attend", isAttend);


		students.setEntYear(entYear);
		students.setNo(no);
		students.setName(name);
		students.setClassNum(classNum);
		students.setAttend(isAttend);
		students.setSchool(teacher.getSchool());


		boolean line = sDao.save(students);
		if (line==true) {
			request.getRequestDispatcher("student_update_done.jsp").forward(request, response);
		}
	}
}
