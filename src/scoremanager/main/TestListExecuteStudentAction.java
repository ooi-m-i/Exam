package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.TestListStudentDAO;
import tool.Action;


public class TestListExecuteStudentAction extends Action {

	public void execute(
			HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		//	セッションの保持
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");


		//	フィールドの宣言
		String studentNo = "";
		Student student = new Student();
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		StudentDAO sDAO = new StudentDAO();
		ClassNumDAO cNumDAO = new ClassNumDAO();
		SubjectDAO subDAO = new SubjectDAO();
		TestListStudentDAO tlsDAO = new TestListStudentDAO();

		//	JSPからデータを受け取る
		studentNo = request.getParameter("f4");
		student = sDAO.get(studentNo);


		List<Integer> entYearSet = new ArrayList<>();

		for (int i=year-10; i<year+1; i++) {
			entYearSet.add(i);
		}

		//	クラス・成績情報・科目をリストに格納
		List<String> list = cNumDAO.filter(teacher.getSchool());
		List<TestListStudent> testListStudent = tlsDAO.filter(student);
		List<Subject> subjectList = subDAO.filter(teacher.getSchool());


		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", list);
		request.setAttribute("subjects", subjectList);
		request.setAttribute("f4", studentNo);

		request.setAttribute("student", student);
		request.setAttribute("test_list_student", testListStudent);


		request.getRequestDispatcher("test_list_student.jsp").forward(request, response);
	}
}
