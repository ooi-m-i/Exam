package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDAO;
import tool.Action;


public class StudentCreateAction extends Action {

	public void execute(
			HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");


		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		ClassNumDAO cNumDao = new ClassNumDAO();


		List<Integer> entYearSet = new ArrayList<>();

		for (int i=year-10; i<year+1; i++) {
			entYearSet.add(i);
		}


		List<String> list = cNumDao.filter(teacher.getSchool());


		request.setAttribute("class_num_set", list);
		request.setAttribute("ent_year_set", entYearSet);

		request.getRequestDispatcher("student_create.jsp").forward(request, response);
	}
}
