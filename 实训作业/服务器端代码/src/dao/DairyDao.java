package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.DairyBean;

public class DairyDao {

	DairyBean dairy = null;

	ResultSet rs = null;

	Connection conn = null;

	Statement statement = null;

	ArrayList<DairyBean> dairyList = new ArrayList<DairyBean>();

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
	
	
	public int deleteDairy(String name , String date){
		int flag = 1;
		
		String deletesql = "delete from dairy where dates ='"+date+"' and name ='"+name+"'"; 
		
		connect();
		
		try{
			System.out.println("delete dairy "+name + " " + date + "?");
			statement.executeUpdate(deletesql);
			System.out.println("delete dairy DONE !!");
		}
		catch(SQLException e) {
			System.out.println("delete dairy sql wrong -->"+e);
			flag = 2;
		}
		
		
		closed();
		return flag;
		}

	public int writeDairy(String name, String content, String date) {

		int flag = 1;
		String sql = "insert into dairy(name,content,dates) values('" + name
				+ "','" + content + "','" + date + "')";
		System.out.println("sql -- >"+sql);
		connect();
		try {
			System.out.println("insert into dairy  -->"+sql);
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			statement.executeUpdate(sql);
			
			System.out.println("insert into dairy done");
		} catch (SQLException e) {
			System.out.println(e);
			flag = 0;
		}
		closed();
		return flag;
	}

	public ArrayList<DairyBean> getDairy(String name) {
		String sql = "select * from dairy where name = '" + name + "'";
		connect();
		System.out.println(name);

		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
		
		
			while (rs.next()) {
				dairy = new DairyBean();
		
				dairy.setContent(rs.getString(rs.findColumn("content")));
				dairy.setDate(rs.getString(rs.findColumn("dates")));
				dairyList.add(dairy);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closed();
		return dairyList;
	}
}
