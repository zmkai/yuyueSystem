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
@WebServlet("/getMyRecord")
public class getMyRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "";
		Map<String, Object> req2Map = HttpUtils.getReq2Map(request);
		System.out.println(req2Map.get("mark"));
		List<Object> params = new ArrayList<>();
		if(req2Map!=null) {
			if(req2Map.get("mark")==null) {
				result = Data2JsonUtils.getResponseString(1, "缺少审核参数", null);
			}else if (req2Map.get("account")==null) {
				result = Data2JsonUtils.getResponseString(1, "缺少账号参数", null);
			}
			params.add(req2Map.get("account"));
			params.add(req2Map.get("mark"));
			String sql = "SELECT\r\n" + 
					"	L.labname,\r\n" + 
					"	E.equ_name,\r\n" + 
					"	Y.startTime,\r\n" + 
					"	Y.endTime,\r\n" + 
					"Y.resourcemark\r\n" + 
					"FROM\r\n" + 
					"	yuyue_records_table Y\r\n" + 
					"LEFT JOIN lab_table L on Y.code = L.code\r\n" + 
					"LEFT JOIN equresource_table E ON Y.equ_code = E.equ_code\r\n" + 
					"WHERE\r\n" + 
					"	Y.account = ?" + 
					"AND Y.mark = ?";
			DbDao dao = new DbDaoImpl();
			List<Map<String, Object>> queryData = dao.queryData(sql, params);
			if(queryData!=null&&queryData.size()>0) {
				result = Data2JsonUtils.getResponseString(0, "返回数据成功", queryData);
			}else {
				result = Data2JsonUtils.getResponseString(1, "当前无数据", null);
			}
		}else {
			result = Data2JsonUtils.getResponseString(1, "缺少参数", null);
		}
		response.getWriter().write(result);
	}

}
