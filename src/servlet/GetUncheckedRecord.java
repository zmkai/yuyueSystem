package servlet;

import java.io.IOException;
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

/**
 * Servlet implementation class GetUncheckedRecord
 */
@WebServlet("/getUncheckedRecord")
public class GetUncheckedRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "";
		String sql = "SELECT Y.id, Y.account,U.username,U.email, Y.code,L.labname,Y.equ_code,E.equ_name,Y.startTime,Y.endTime,Y.resourcemark FROM yuyue_records_table Y LEFT JOIN lab_table L ON Y.code = L.code LEFT JOIN equresource_table E ON Y.equ_code = E.equ_code LEFT JOIN user_table U ON Y.account = U.account WHERE Y.mark = '0'";
		DbDao dao = new DbDaoImpl();
		List<Map<String, Object>> queryData = dao.queryData(sql, null);
		if(queryData!=null||queryData.size()>0) {
			result = Data2JsonUtils.getResponseString(0, "返回数据成功", queryData);
		}else {
			result = Data2JsonUtils.getResponseString(1, "当前无数据", null);
		}
		response.getWriter().write(result);
	}

}
