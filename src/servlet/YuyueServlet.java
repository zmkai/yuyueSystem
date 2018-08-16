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
				result = Data2JsonUtils.getResponseString(1, "ȱ�ٱ�ǲ���", null);
			}else if(req2Map.get("code")==null) {
				result = Data2JsonUtils.getResponseString(1, "ȱ�ٱ������", null);
			}else if(req2Map.get("startTime")==null) {
				
				result = Data2JsonUtils.getResponseString(1, "ȱ�ٿ�ʼԤԼʱ��", null);
			}else if(req2Map.get("endTime")==null) {
				result = Data2JsonUtils.getResponseString(1, "ȱ�ٽ���ԤԼʱ��", null);
			}else if(req2Map.get("account")==null) {
				result = Data2JsonUtils.getResponseString(1, "ȱ���û��˺�", null);
			}
			String resourcemark = (String) req2Map.get("resourcemark");
			if("0".equals(resourcemark)) {
				//ʵ����ԤԼ
				sql = "INSERT INTO yuyue_records_table(code,startTime,endTime,account,resourcemark,mark) VALUES(?,?,?,?,?,'0')";
			}else if ("1".equals(resourcemark)) {
				//�����豸ԤԼ
				sql = "INSERT INTO yuyue_records_table(equ_code,startTime,endTime,account,resourcemark,mark) VALUES(?,?,?,?,?,'0')";
			}
			params.add(req2Map.get("code"));
			params.add(req2Map.get("startTime"));
			params.add(req2Map.get("endTime"));
			params.add(req2Map.get("account"));
			params.add(req2Map.get("resourcemark"));
			DbDao dao = new DbDaoImpl();
			if(dao.addDelUp(sql, params)) {
				result = Data2JsonUtils.getResponseString(0, "ԤԼ�ɹ�����ȴ����", null);
			}else {
				result = Data2JsonUtils.getResponseString(1, "ԤԼʧ��", null);
			}
		}else {
			result = Data2JsonUtils.getResponseString(1, "�������Ϊ��", null);
		}
		response.getWriter().write(result);
	}

}
