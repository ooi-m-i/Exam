package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

//データーベースの連携処理
public class SubjectDAO extends DAO {

    // 科目を1件取得
    public Subject get(String cd, School school) throws Exception {
        Subject subject = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {

            statement = connection.prepareStatement("SELECT * FROM subject WHERE cd = ? AND school_cd = ?");

            statement.setString(1, cd);
            statement.setString(2, school.getCd());

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
            	subject = new Subject();

                subject.setCd(rSet.getString("cd"));
                subject.setName(rSet.getString("name"));
                subject.setSchool(school);
            }

        } catch (Exception e) {
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
        return subject;
    }


    //  学校情報から科目情報を取得する
	public List<Subject> filter(School school) throws Exception {
		List<Subject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;


		try {
			statement = connection.prepareStatement(
					"SELECT * FROM subject WHERE school_cd = ? ORDER BY cd");

			statement.setString(1, school.getCd());

			ResultSet rSet = statement.executeQuery();

			while (rSet.next()) {
				Subject subject = new Subject();

				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
				list.add(subject);
			}

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


	//	科目情報の登録・更新を行う
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            Subject old = get(subject.getCd(), subject.getSchool());

            if (old == null) {
                statement = connection.prepareStatement(
                        "insert into subject (school_cd, cd, name) values(?, ?, ?)");

                statement.setString(1, subject.getSchool().getCd());
                statement.setString(2, subject.getCd());
                statement.setString(3, subject.getName());

            } else {
                statement = connection.prepareStatement(
                        "update subject set school_cd=?, name=? where cd=?");

                statement.setString(1, subject.getSchool().getCd());
                statement.setString(2, subject.getName());
                statement.setString(3, subject.getCd());
            }

            count = statement.executeUpdate();

        } catch (Exception e) {
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

        return count > 0;
    }


    // 削除処理
    public boolean delete(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            statement = connection.prepareStatement("DELETE FROM subject WHERE school_cd = ? AND cd = ?");

            statement.setString(1, subject.getSchool().getCd());
            statement.setString(2, subject.getCd());

            count = statement.executeUpdate();

        } catch (Exception e) {
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

        return count > 0;
    }
}