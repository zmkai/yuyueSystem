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
 * Servlet implementation class YuyueServlet
 */
@WebServlet("/yuyue")
public class YuyueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = "";
		String sql = "";
		Map<String, Object> req2Map = HttpUtils.getReq2Map(request);
		List<Object> params = new ArrayList<>();
		if(req2Map!=null) {
			if(req2Map.get("resourcemark")==null) {
				result = Data2JsonUtils.getResponseString(1, "缺少标记参数", null);
			}else if(req2Map.get("code")==null) {
				result = Data2JsonUtils.getResponseString(1, "缺少编码参数", null);
			}else if(req2Map.get("startTime")==null) {
				
				result = Data2JsonUtils.getResponseString(1, "缺少开始预约时间", null);
			}else if(req2Map.get("endTime")==null) {
				result = Data2JsonUtils.getResponseString(1, "缺少结束预约时间", null);
			}else if(req2Map.get("account")==null) {
				result = Data2JsonUtils.getResponseString(1, "缺少用户账号", null);
			}
			String resourcemark = (String) req2Map.get("resourcemark");
			if("0".equals(resourcemark)) {
				//实验室预约
				sql = "INSERT INTO yuyue_records_table(code,startTime,endTime,account,resourcemark,mark) VALUES(?,?,?,?,?,'0')";
			}else if ("1".equals(resourcemark)) {
				//大型设备预约
				sql = "INSERT INTO yuyue_records_table(equ_code,startTime,endTime,account,resourcemark,mark) VALUES(?,?,?,?,?,'0')";
			}
			params.add(req2Map.get("code"));
			params.add(req2Map.get("startTime"));
			params.add(req2Map.get("endTime"));
			params.add(req2Map.get("account"));
			params.add(req2Map.get("resourcemark"));
			DbDao dao = new DbDaoImpl();
			if(dao.addDelUp(sql, params)) {
				result = Data2JsonUtils.getResponseString(0, "预约成功，请等待审核", null);
			}else {
				result = Data2JsonUtils.getResponseString(1, "预约失败", null);
			}
		}else {
			result = Data2JsonUtils.getResponseString(1, "请求参数为空", null);
		}
		response.getWriter().write(result);
	}

}
