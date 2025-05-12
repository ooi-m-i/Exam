package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import tool.Action;


public class TestListAction extends Action {

	public void execute(
			HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		//	セッションの保持
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");

		//	フィールドの宣言
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		ClassNumDAO cNumDAO = new ClassNumDAO();
		SubjectDAO subDAO = new SubjectDAO();


		List<Integer> entYearSet = new ArrayList<>();

		for (int i=year-10; i<year+1; i++) {
			entYearSet.add(i);
		}

		//	クラス・科目をリストに格納
		List<String> list = cNumDAO.filter(teacher.getSchool());
		List<Subject> subjectList = subDAO.filter(teacher.getSchool());


		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", list);
		request.setAttribute("subjects", subjectList);


		request.getRequestDispatcher("test_list.jsp").forward(request, response);
	}
}