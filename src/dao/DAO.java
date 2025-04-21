package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

//抽象クラスDAOを定義
public class DAO {
	static DataSource ds;

	public Connection getConnection() throws Exception {
		if (ds==null) {
			InitialContext ic=new InitialContext();
			ds = (DataSource)ic.lookup("java:/comp/env/jdbc/school");
		}

		return ds.getConnection();

	}
}
