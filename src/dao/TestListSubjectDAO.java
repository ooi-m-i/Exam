package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.TestListSubject;

public class TestListSubjectDAO extends DAO {

    /**
     * 指定されたクラス・科目・学校に該当する学生の成績リストを取得する。
     * 学生ごとに Map<テスト番号, 点数> を保持する形式。
     *
     * @param classNum クラス番号
     * @param subjectCd 科目コード
     * @param school 所属学校インスタンス
     * @return 成績リスト（TestListSubjectのリスト）
     * @throws Exception DB接続・SQL実行時の例外
     */
    public List<TestListSubject> filter(String classNum, String subjectCd, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();

        // SQL：クラスに所属する学生の成績を取得（特定科目）
        String sql =
            "SELECT s.no, s.name, s.ent_year, s.class_num, t.point, t.no " +
            "FROM student s " +
            "JOIN test t ON s.no = t.student_no AND s.school_cd = t.school_cd " +
            "WHERE s.class_num = ? AND t.subject_cd = ? AND s.school_cd = ? " +
            "ORDER BY s.no";

        Connection connection = getConnection();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, classNum);         // student.class_num
        statement.setString(2, subjectCd);        // test.subject_cd
        statement.setString(3, school.getCd());   // student.school_cd

        ResultSet rs = statement.executeQuery();

        // 学生番号をキーにして重複を避けながら格納
        Map<String, TestListSubject> studentMap = new LinkedHashMap<>();
        while (rs.next()) {
            String studentNo = rs.getString("no");

            // すでに存在する場合は取得、なければ新規作成
            TestListSubject subject = studentMap.get(studentNo);
            if (subject == null) {
                subject = new TestListSubject();
                subject.setStudentNo(studentNo);
                subject.setStudentName(rs.getString("name"));
                subject.setEntYear(rs.getInt("ent_year"));
                subject.setClassNum(rs.getString("class_num"));
                subject.setPoints(new LinkedHashMap<>());
                studentMap.put(studentNo, subject);
            }

            // テスト番号と得点をMapに追加
            subject.getPoints().put(rs.getInt("no"), rs.getInt("point"));
        }

        // MapからListに変換して返却
        list.addAll(studentMap.values());

        rs.close();
        statement.close();
        connection.close();

        return list;
    }
}
