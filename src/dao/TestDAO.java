package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDAO extends DAO {

    // 成績1件取得（対象の学生・科目・回数）
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;

        Connection connection = getConnection();
        String sql = "SELECT * FROM test WHERE student_no = ? AND subject_cd = ? AND no = ? AND school_cd = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, student.getNo());
        st.setString(2, subject.getCd());
        st.setInt(3, no);
        st.setString(4, school.getCd());

        ResultSet rs = st.executeQuery();
        if (rs.next()) {
            test = new Test();
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            test.setNo(no);
            test.setPoint(rs.getInt("point"));
        }

        rs.close();
        st.close();
        connection.close();

        return test;
    }

    // ResultSet → List<Test> に変換（主に一覧取得用など）
    public List<Test> postFilter(ResultSet rs, School school) throws Exception {
        List<Test> list = new ArrayList<>();

        while (rs.next()) {
            Student student = new Student();
            student.setNo(rs.getString("student_no"));
            student.setName(rs.getString("name"));
            student.setEntYear(rs.getInt("ent_year"));
            student.setClassNum(rs.getString("class_num"));
            student.setAttend(true);
            student.setSchool(school);

            Subject subject = new Subject();
            subject.setCd(rs.getString("subject_cd"));
            subject.setSchool(school);

            Test test = new Test();
            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            test.setNo(rs.getInt("no"));
            test.setPoint(rs.getInt("point"));

            list.add(test);
        }

        return list;
    }

    // filter(...) メソッド（登録画面での一覧表示用）はそのままでOK
    public List<Test> filter(int entYear, String classNum, Subject subject, int no, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();

        String sql =
            "SELECT s.no, s.name, s.ent_year, s.class_num, " +
            "       t.point " +
            "FROM student s " +
            "LEFT JOIN test t ON s.no = t.student_no " +
            "                AND t.subject_cd = ? " +
            "                AND t.no = ? " +
            "                AND t.school_cd = ? " +
            "WHERE s.ent_year = ? AND s.class_num = ? AND s.school_cd = ? AND s.is_attend = true ORDER BY s.no";

        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, subject.getCd());
        st.setInt(2, no);
        st.setString(3, school.getCd());
        st.setInt(4, entYear);
        st.setString(5, classNum);
        st.setString(6, school.getCd());

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Student student = new Student();
            student.setNo(rs.getString("no"));
            student.setName(rs.getString("name"));
            student.setEntYear(rs.getInt("ent_year"));
            student.setClassNum(rs.getString("class_num"));
            student.setAttend(true);
            student.setSchool(school);

            Test test = new Test();
            test.setStudent(student);
            test.setClassNum(classNum);
            test.setSubject(subject);
            test.setSchool(school);
            test.setNo(no);

            // 点数が null の場合は設定しない
            int point = rs.getInt("point");
            if (!rs.wasNull()) {
                test.setPoint(point);
            } else {
                test.setPoint(null); // 空欄として表現するために null を保持
            }

            list.add(test);
        }

        rs.close();
        st.close();
        connection.close();

        return list;
    }


    // 成績リストを登録（nullの点数はスキップ）
    public boolean save(List<Test> list) throws Exception {
        Connection connection = getConnection();
        boolean result = true;

        for (Test test : list) {
            // 点数がnullならスキップ
            if (test.getPoint() == null) continue;

            boolean r = save(test, connection);
            if (!r) result = false;
        }

        connection.close();
        return result;
    }

    // 1件登録（存在すればUPDATE、なければINSERT）
    public boolean save(Test test, Connection connection) throws Exception {
        // まず存在確認
        String sqlCheck = "SELECT COUNT(*) FROM test WHERE student_no = ? AND subject_cd = ? AND no = ? AND school_cd = ?";
        PreparedStatement check = connection.prepareStatement(sqlCheck);
        check.setString(1, test.getStudent().getNo());
        check.setString(2, test.getSubject().getCd());
        check.setInt(3, test.getNo());
        check.setString(4, test.getSchool().getCd());

        ResultSet rs = check.executeQuery();
        rs.next();
        boolean exists = rs.getInt(1) > 0;
        rs.close();
        check.close();

        if (exists) {
        	String sqlUpdate = "UPDATE test SET point = ?, class_num = ? WHERE student_no = ? AND subject_cd = ? AND no = ? AND school_cd = ?";
        	PreparedStatement st = connection.prepareStatement(sqlUpdate);
        	st.setInt(1, test.getPoint());
        	st.setString(2, test.getClassNum());
        	st.setString(3, test.getStudent().getNo());
        	st.setString(4, test.getSubject().getCd());
        	st.setInt(5, test.getNo());
        	st.setString(6, test.getSchool().getCd());
            int updated = st.executeUpdate();
            st.close();
            return updated > 0;
        } else {
        	String sqlInsert = "INSERT INTO test (student_no, subject_cd, no, school_cd, point, class_num) VALUES (?, ?, ?, ?, ?, ?)";
        	PreparedStatement st = connection.prepareStatement(sqlInsert);
        	st.setString(1, test.getStudent().getNo());
        	st.setString(2, test.getSubject().getCd());
        	st.setInt(3, test.getNo());
        	st.setString(4, test.getSchool().getCd());
        	st.setInt(5, test.getPoint());
        	st.setString(6, test.getClassNum()); // ← これでOK

            int inserted = st.executeUpdate();
            st.close();
            return inserted > 0;
        }
    }
}
