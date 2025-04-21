package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDAO;
import tool.Action;


public class StudentCreateExecuteAction extends Action {

	public void execute(
			HttpServletRequest request, HttpServletResponse response
	) throws Exception {

		HttpSession session = request.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");


		String entYearStr="";
		String no = "";
		String name = "";
		String classNum="";
		int entYear = 0;
		LocalDate todaysDate = LocalDate.now();
		int year = todaysDate.getYear();
		Student students = new Student();
		Map<String, String> errors = new HashMap<>();

		StudentDAO sDao = new StudentDAO();
		ClassNumDAO cNumDao = new ClassNumDAO();

		entYearStr = request.getParameter("ent_year");
		no = request.getParameter("no");
		name = request.getParameter("name");
		classNum = request.getParameter("class_num");


		if (entYearStr != null) {
			entYear = Integer.parseInt(entYearStr);
		}


		List<Integer> entYearSet = new ArrayList<>();
		for (int i=year-10; i<year+1; i++) {
			entYearSet.add(i);
		}

		List<String> list = cNumDao.filter(teacher.getSchool());

		if (entYear==0 && sDao.get(no) != null) {
		    errors.put("e1", "入学年度を選択してください");
		    errors.put("e2", "学生番号が重複しています");
		} else if (entYear==0) {
		    errors.put("e1", "入学年度を選択してください");
		} else if (sDao.get(no) != null) {
		    errors.put("e2", "学生番号が重複しています");
		}

		request.setAttribute("ent_year", entYear);
		request.setAttribute("no", no);
		request.setAttribute("name", name);
	    request.setAttribute("class_num", classNum);
	    request.setAttribute("errors", errors);
		request.setAttribute("ent_year_set", entYearSet);
		request.setAttribute("class_num_set", list);

	    // エラーがあれば戻す
	    if (!errors.isEmpty()) {
	        request.getRequestDispatcher("student_create.jsp").forward(request, response);
	        return;
	    }


		students.setEntYear(entYear);
		students.setNo(no);
		students.setName(name);
		students.setClassNum(classNum);
		students.setSchool(teacher.getSchool());


		boolean line = sDao.save(students);
		if (line==true) {
			request.getRequestDispatcher("student_create_done.jsp").forward(request, response);
		}
	}
}
