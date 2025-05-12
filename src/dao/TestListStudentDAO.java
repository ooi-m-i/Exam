package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;


public class TestListStudentDAO extends DAO {

	private String baseSql =
			"SELECT t.subject_cd, s.name AS subject_name, t.no, t.point " +
			"FROM TEST t " +
			"INNER JOIN SUBJECT s ON t.subject_cd = s.cd AND t.school_cd = s.school_cd " +
			"WHERE t.student_no = ? AND t.school_cd = ? AND t.class_num = ? " +
			"ORDER BY t.subject_cd, t.no";


	//	フィルター後、リストへの格納処理
	public List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		List<TestListStudent> list = new ArrayList<>();

		try {
			while (rSet.next()) {
				TestListStudent tlStudent = new TestListStudent();

				tlStudent.setSubjectName(rSet.getString("subject_name"));
				tlStudent.setSubjectCd(rSet.getString("subject_cd"));
				tlStudent.setNum(rSet.getInt("no"));
				tlStudent.setPoint(rSet.getInt("point"));

				list.add(tlStudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}


	//	学生情報から成績情報を取得する
	public List<TestListStudent> filter(Student student) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;


		try {
			statement = connection.prepareStatement(baseSql);

			statement.setString(1, student.getNo());
			statement.setString(2, student.getSchool().getCd());
			statement.setString(3, student.getClassNum());

			rSet = statement.executeQuery();
			list = postFilter(rSet);

		}catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}


}