package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import bean.UserBean;

public class UserDao {

	public int flag = 1;

	UserBean user = null;

	ResultSet rs = null;

	Connection conn = null;

	Statement statement = null;

	public void connect() {
		String url = "jdbc:mysql://localhost/test";

		try {
			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(url, "root", "12345678");

			statement = conn.createStatement();

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void closed() {
		try {
			if (rs != null)
				rs.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public UserBean login(String name) {

		String sql = "select * from users where name = '" + name + "'";
		connect();

		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				user = new UserBean();
				user.setPassword(rs.getString(rs.findColumn("password")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		closed();
		return user;
	}

	public int register(String name, String password) {
		String sql = "select * from users where name = '" + name + "'";
		System.out.println("name-->" + name);
		connect();

		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("register query error --> "+ e);
		}

		try {// //////////////////////////////////////////////////////////////////////////////////////////////////
			if (rs.next()) {
				flag = 2;
			} else {
				sql = "insert into users(name,password) values('" + name + "','"
						+ password + "')";
				statement.executeUpdate(sql);
			}
		} catch (SQLException e) {
			flag = 3;
		}
		
		closed();
		return flag;
	}

	public int ChangePass(String name, String NewPass){
		
		//String getPass = " select * from test where name = '"+name+"'  ";
		String changePass =" update users set password = '"+NewPass+"' where name = '"+name+"'";
		
		connect();
		
		try {
			statement.executeUpdate(changePass);
		} catch (SQLException e) {
			System.out.println("changePass sql wrong --> "+e);
			flag = 2;
		}
		
		closed();
		return flag;
	}
}
