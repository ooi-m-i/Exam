package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.TestListStudent;

public class TestListStudentDAO extends DAO {

    /**
     * 指定された生徒番号・学校に対して、その生徒が受験した科目ごとの成績を取得する。
     *
     * @param studentNo 生徒番号
     * @param school 所属学校インスタンス
     * @return 成績情報のリスト（TestListStudentのリスト）
     * @throws Exception DB接続・SQL実行時の例外
     */
    public List<TestListStudent> filter(String studentNo, School school) throws Exception {
        List<TestListStudent> list = new ArrayList<>();

        // SQL：生徒の成績を科目名とともに取得
        String sql =
            "SELECT sub.name AS subject_name, sub.cd AS subject_cd, t.point, t.no " +
            "FROM test t " +
            "JOIN subject sub ON t.subject_cd = sub.cd AND sub.school_cd = ? " +
            "WHERE t.student_no = ? AND t.school_cd = ? " +
            "ORDER BY t.no";

        // DB接続を取得
        Connection connection = getConnection();

        // プリペアドステートメントにパラメータを設定
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, school.getCd());   // subject.school_cd
        statement.setString(2, studentNo);        // test.student_no
        statement.setString(3, school.getCd());   // test.school_cd

        // クエリ実行
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            // 各行のデータをBeanに詰める
            TestListStudent bean = new TestListStudent();
            bean.setSubjectName(rs.getString("subject_name"));
            bean.setSubjectCd(rs.getString("subject_cd"));
            bean.setNum(rs.getInt("no"));
            bean.setPoint(rs.getInt("point"));
            list.add(bean);
        }

        // リソースをクローズ
        rs.close();
        statement.close();
        connection.close();

        return list;
    }
}
