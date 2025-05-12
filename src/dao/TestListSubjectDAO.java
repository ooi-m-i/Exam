package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDAO extends DAO {

	private String baseSql =
	        "SELECT s.no AS student_no, s.name AS student_name, s.ent_year, s.class_num, " +
	                "t.no, t.point " +
	                "FROM student s " +
	                "JOIN test t ON s.no = t.student_no AND s.school_cd = t.school_cd " +
	                "WHERE s.ent_year = ? " +
	                "AND s.class_num = ? " +
	                "AND t.subject_cd = ? " +
	                "AND s.school_cd = ? " +
	                "ORDER BY s.no, t.no";



	//	フィルター後、リストへの格納処理
	public List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		Map<String, TestListSubject> map = new LinkedHashMap<>();

		try {
			while (rSet.next()) {
				String studentNo = rSet.getString("student_no");
				int no = rSet.getInt("no");
				int point = rSet.getInt("point");

				TestListSubject tlSubject = map.get(studentNo);

				if(tlSubject == null) {
					tlSubject = new TestListSubject();

					tlSubject.setEntYear(rSet.getInt("ent_year"));
					tlSubject.setStudentNo(studentNo);
					tlSubject.setStudentName(rSet.getString("student_name"));
					tlSubject.setClassNum(rSet.getString("class_num"));
					tlSubject.setPoints(new HashMap<>());
					map.put(studentNo, tlSubject);
				}


				tlSubject.putPoint(no, point);

			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return new ArrayList<>(map.values());
	}


	//	引数の情報から成績情報を取得する
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
    	List<TestListSubject> list = new ArrayList<>();

		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;


		try {
			statement = connection.prepareStatement(baseSql);

	        statement.setInt(1, entYear);
	        statement.setString(2, classNum);
	        statement.setString(3, subject.getCd());
	        statement.setString(4, school.getCd());

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