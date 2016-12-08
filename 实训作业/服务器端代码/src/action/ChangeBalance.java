package action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TallyDao;

public class ChangeBalance extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChangeBalance() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String name = request.getParameter("name");
		String balance = request.getParameter("balance");
		String detail = request.getParameter("detail");
		String date = request.getParameter("date");
		String inorout = request.getParameter("inorout");

		TallyDao tallyDao = new TallyDao();

		int flag = tallyDao.inorout(name, balance, detail, date, inorout);
		out.print("{status: " + flag + "}");

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
