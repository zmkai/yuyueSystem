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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "";
		DbDao dao = new DbDaoImpl();
		Map<String, Object> req2Map = HttpUtils.getReq2Map(request);
		HttpUtils.queryMap(req2Map);
		List<Object> params = new ArrayList<>();
		params.add(req2Map.get("account"));
		params.add(req2Map.get("password"));
		String sql = "select * from user_table where account = ? and password = ? and mark = '1'";
		List<Map<String, Object>> queryData = dao.queryData(sql, params);
		if(queryData!=null&&queryData.size()>0) {
			result = Data2JsonUtils.getResponseString(0, "登录成功", queryData);
		}else {
			result = Data2JsonUtils.getResponseString(1, "用户名不存在或者账号密码错误", null);
		}
		response.getWriter().write(result);
	}

}
