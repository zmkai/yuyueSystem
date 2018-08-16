package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DbDao;
import dao.impl.DbDaoImpl;
import utils.Data2JsonUtils;
import utils.HttpUtils;

/**
 * Servlet implementation class MyUncheckedRecord
 */
@WebServlet("/lookMyself")
public class LookMyself extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("kaishi");
		String result = "";
		Map<String, Object> req2Map = HttpUtils.getReq2Map(request);
		List<Object> params = new ArrayList<>();
		if (req2Map.get("account") != null) {
			params.add(req2Map.get("account"));
			String sql = "SELECT account,username,email,photo_path,identity from user_table WHERE account = ? AND mark = '1'";
			DbDao dao = new DbDaoImpl();
			List<Map<String, Object>> queryData = dao.queryData(sql, params);
			if (queryData != null && queryData.size() > 0) {
				result = Data2JsonUtils.getResponseString(0, "返回数据成功", queryData);
			} else {
				result = Data2JsonUtils.getResponseString(1, "当前无数据", null);
			}
		} else {
			result = Data2JsonUtils.getResponseString(1, "缺少参数", null);
		}
		response.getWriter().write(result);
	}

}
