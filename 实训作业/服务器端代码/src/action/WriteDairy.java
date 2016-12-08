package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DairyDao;

public class WriteDairy extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WriteDairy() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		String date = request.getParameter("date");

		System.out.println(date);
		
		DairyDao dairyDao = new DairyDao();

		int flag = dairyDao.writeDairy(name, content, date);
		out.print("{status: " + flag + "}");
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
