package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDAO;
import tool.Action;


public class StudentUpdateAction extends Action {

	public void execute(
			HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");


		String no = request.getParameter("no");
		String name;
		int entYear;
		String classNum;
		Boolean isAttend;

		StudentDAO sDao = new StudentDAO();
		Student student = sDao.get(no);

		name = student.getName();
		entYear = student.getEntYear();
		classNum = student.getClassNum();
		isAttend = student.isAttend();


		ClassNumDAO cNumDao = new ClassNumDAO();

		List<String> list = cNumDao.filter(teacher.getSchool());

		request.setAttribute("no", no);
		request.setAttribute("name", name);
		request.setAttribute("ent_year", entYear);
		request.setAttribute("class_num", classNum);

		if(isAttend==true) {
			request.setAttribute("is_attend", isAttend);
		}

		request.setAttribute("class_num_set", list);

		request.getRequestDispatcher("student_update.jsp").forward(request, response);
	}
}
