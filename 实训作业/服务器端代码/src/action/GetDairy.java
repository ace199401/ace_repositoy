package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.DairyBean;
import dao.DairyDao;

public class GetDairy extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GetDairy() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String name = request.getParameter("name");

		DairyDao dairyDao = new DairyDao();
		ArrayList<DairyBean> dairyList = new ArrayList<DairyBean>();
		dairyList = dairyDao.getDairy(name);

		String dairyListStr = "{\"dairyList\":[";
//////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (dairyList.size() != 0) {

			for (int i = 0; i < dairyList.size(); i++) {

				dairyListStr = dairyListStr + "{\"content\": \""
						+ dairyList.get(i).getContent() + "\",\"date\": \""
						+ dairyList.get(i).getDate() + "\"},";

			}

			dairyListStr = dairyListStr.substring(0, dairyListStr.length() - 1)
					+ "]}";
		}

		else {
			dairyListStr = dairyListStr	+ "]}";
		}
		out.print(dairyListStr);

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
