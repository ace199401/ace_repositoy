package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TallyDao;
import bean.TallyBean;

public class GetTally extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GetTally() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();

		String name = request.getParameter("name");

		ArrayList<TallyBean> tallyList = new ArrayList<TallyBean>();

		TallyDao tallyDao = new TallyDao();

		System.out.println(name);
		tallyList = tallyDao.GetTallyList(name);
		
		
		
		String tallyListStr = "{\"tallyList\":[";

//////////////////////////////////////////////////////////////////////////////////////////////////////////
		if (tallyList.size() != 0) {
		
				
				for (int i = 0; i < tallyList.size(); i++) {
					tallyListStr = tallyListStr + "{\"balance\": \""
							+ tallyList.get(i).getBalance() + "\",\"date\": \""
							+ tallyList.get(i).getDate() + "\",\"detail\": \""
							+ tallyList.get(i).getDetail() + "\",\"inorout\": \""
							+ tallyList.get(i).getInorout() + "\"},";
				}
		
				tallyListStr = tallyListStr.substring(0, tallyListStr.length() - 1)
						+ "]}";
				
		}
		
		
		else
		{
			tallyListStr = tallyListStr	+ "]}";
		}
		out.print(tallyListStr);
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
