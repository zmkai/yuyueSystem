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
 * Servlet implementation class MyRecordServlet
 */
@WebServlet("/getCheckedRecord")
public class GetCheckRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "";
		List<Object> params = new ArrayList<>();
			String sql ="SELECT\r\n" + 
					"   Y.id,\r\n" +
					"	L.labname,\r\n" + 
					"	E.equ_name,\r\n" + 
					"	Y.startTime,\r\n" + 
					"	Y.endTime,\r\n" + 
					"	Y.resourcemark,\r\n" + 
					"	U.username\r\n" + 
					"FROM\r\n" + 
					"	yuyue_records_table Y\r\n" + 
					"LEFT JOIN lab_table L ON Y. CODE = L. CODE\r\n" + 
					"LEFT JOIN equresource_table E ON Y.equ_code = E.equ_code\r\n" + 
					"LEFT JOIN user_table U\r\n" + 
					"ON Y.account = U.account\r\n" + 
					"WHERE\r\n" + 
					"	Y.mark = '1'";
			DbDao dao = new DbDaoImpl();
			List<Map<String, Object>> queryData = dao.queryData(sql, null);
			if(queryData!=null&&queryData.size()>0) {
				result = Data2JsonUtils.getResponseString(0, "返回数据成功", queryData);
			}else {
				result = Data2JsonUtils.getResponseString(1, "当前无数据", null);
			}
		response.getWriter().write(result);
}

}
