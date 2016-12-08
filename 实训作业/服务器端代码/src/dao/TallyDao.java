package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import bean.TallyBean;

public class TallyDao {

	ResultSet rs = null;

	Connection conn = null;

	Statement statement = null;

	ArrayList<TallyBean> tallyContentList = new ArrayList<TallyBean>();

	TallyBean tally = null;

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

	
	public int deleteTally(String name , String date){
		int flag = 1;
		
		String deletesql = "delete tally where dates ='"+date+"' and name ='"+name+"' "; 
		
		connect();
		
		try{
			System.out.println("delete tally "+name + " " + date + "?");
			statement.executeUpdate(deletesql);
			System.out.println("delete tally DONE !!");
		}
		catch(SQLException e) {
			System.out.println("delete tally sql wrong -->"+e);
			flag = 2;
		}
		
		
		closed();
		return flag;
		}
	
	
	
	public int inorout(String name, String balance, String detail, String date,
			String inorout) {

		int flag = 1;
		String sql = "insert into tally(name,balance,detail,dates,inorout) values('"
				+ name
				+ "','"
				+ balance
				+ "','"
				+ detail
				+ "','"
				+ date
				+ "','" + inorout + "')";

		connect();

		try {
			System.out.println("tally -->"+sql);
			
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			flag = 0;
			e.printStackTrace();
		}

		closed();
		return flag;

	}

	public ArrayList<TallyBean> GetTallyList(String name) {

		String sql = "select * from tally where name = '" + name + "'";

		connect();

		try {
			rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			while (rs.next()) {
				tally = new TallyBean();
				tally.setBalance(rs.getString(rs.findColumn("balance")));
				tally.setDate(rs.getString(rs.findColumn("dates")));
				tally.setDetail(rs.getString(rs.findColumn("detail")));
				tally.setInorout(rs.getString(rs.findColumn("inorout")));
				tallyContentList.add(tally);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		closed();
		return tallyContentList;
	}
}
