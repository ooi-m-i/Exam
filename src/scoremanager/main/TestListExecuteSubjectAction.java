package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestListSubjectDAO;
import tool.Action;


public class TestListExecuteSubjectAction extends Action {

	public void execute(
			HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		//	セッションの保持
		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");


		//	フィールドの宣言
		String entYearStr = "";
		int entYear = 0;
		String classNum = "";
		String subjectCd = "";
		Subject subject = new Subject();
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		ClassNumDAO cNumDAO = new ClassNumDAO();
		SubjectDAO subDAO = new SubjectDAO();
		TestListSubjectDAO tlsDAO = new TestListSubjectDAO();
		Map<String, String> errors = new HashMap<>();


		//	JSPからデータを受け取る
		entYearStr = request.getParameter("f1");
		classNum = request.getParameter("f2");
		subjectCd = request.getParameter("f3");


		List<Integer> entYearSet = new ArrayList<>();


		for (int i=year-10; i<year+1; i++) {
			entYearSet.add(i);
		}


		if(entYearStr != null && !entYearStr.equals("0")) {
			entYear = Integer.parseInt(entYearStr);
		}

		//	クラス・科目をリストに格納
		List<String> list = cNumDAO.filter(teacher.getSchool());
		List<Subject> subjectList = subDAO.filter(teacher.getSchool());


		//	エラー分岐
		if (!entYearStr.equals("0") && !classNum.equals("0") && !subjectCd.equals("0")) {

			subject = subDAO.get(subjectCd, teacher.getSchool());
			List<TestListSubject> testListSubject = tlsDAO.filter(entYear, classNum, subject, teacher.getSchool());
			request.setAttribute("subject", subject);
			request.setAttribute("test_list_subject", testListSubject);

		} else {

			errors.put("e", "入学年度とクラスと科目を選択してください");
			request.setAttribute("errors", errors);

		}

		request.setAttribute("f1", entYear);
		request.setAttribute("f2", classNum);
		request.setAttribute("f3", subjectCd);

		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", list);
		request.setAttribute("subjects", subjectList);

		request.getRequestDispatcher("test_list_subject.jsp").forward(request, response);
	}
}
