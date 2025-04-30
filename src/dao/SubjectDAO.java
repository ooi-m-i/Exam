package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;

public class SubjectDAO extends DAO{

    public List<Subject> getAll() throws Exception {
        List<Subject> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement st = connection.prepareStatement("SELECT * FROM subject ORDER BY cd");
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject s = new Subject();
            s.setCd(rs.getString("cd"));
            s.setName(rs.getString("name"));
            list.add(s);
        }

        rs.close();
        st.close();
        connection.close();

        return list;
    }

    public List<Subject> filter(String schoolCd) throws Exception {
        List<Subject> list = new ArrayList<>();

        Connection connection = getConnection();
        String sql = "SELECT * FROM subject WHERE school_cd = ?";
        PreparedStatement st = connection.prepareStatement(sql);
        st.setString(1, schoolCd);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Subject s = new Subject();
            s.setCd(rs.getString("cd"));
            s.setName(rs.getString("name"));
            // s.setSchool(...) が必要なら SchoolDao から取得してもOK
            list.add(s);
        }

        rs.close();
        st.close();
        connection.close();

        return list;
    }

}
