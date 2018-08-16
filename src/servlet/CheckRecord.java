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
import utils.WebUtils;

/**
 * Servlet implementation class CheckRecord
 */
@WebServlet("/checkRecord")
public class CheckRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("审核记录");
		String result = "";
		Map<String, Object> req2Map = HttpUtils.getReq2Map(request);
		List<Object> params = new ArrayList<>();
		if(req2Map.get("mark")==null) {
			result = Data2JsonUtils.getResponseString(1, "缺少审核参数", null);
		}else if(req2Map.get("email")==null) {
			result = Data2JsonUtils.getResponseString(1, "缺少邮箱参数", null);
		}else if(req2Map.get("id")==null) {
			result = Data2JsonUtils.getResponseString(1, "缺少id参数", null);
		}else {
			params.add(req2Map.get("id"));
			String email = (String)req2Map.get("email");
			String mark = (String) req2Map.get("mark");
			if("1".equals(mark)) {
				String sql = "UPDATE yuyue_records_table SET mark = '1' WHERE id = ?";
				DbDao dao = new DbDaoImpl();
				if(dao.addDelUp(sql, params)) {
					//发送邮箱
					try {
						WebUtils.sendEmail(email, 0);
					} catch (Exception e) {
						e.printStackTrace();
					}
					result = Data2JsonUtils.getResponseString(0, "已审核，并已发送邮件", null);
				}else {
					//发送邮箱
					try {
						WebUtils.sendEmail(email, 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
					result = Data2JsonUtils.getResponseString(1, "已审核，并已发送邮件", null);
				}
			}else {
				//发送邮箱
				try {
					WebUtils.sendEmail(email, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
				result = Data2JsonUtils.getResponseString(0, "已审核，并已发送邮件", null);
			}
			
		}
	
		response.getWriter().write(result);
	}

}
