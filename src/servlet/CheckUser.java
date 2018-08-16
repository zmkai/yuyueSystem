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

import org.apache.commons.lang.ObjectUtils.Null;

import dao.DbDao;
import dao.impl.DbDaoImpl;
import utils.Data2JsonUtils;
import utils.HttpUtils;
import utils.WebUtils;

/**
 * Servlet implementation class CheckUser
 */
@WebServlet("/checkUser")
public class CheckUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("���");
		String result = "";
		Map<String, Object> req2Map = HttpUtils.getReq2Map(request);
		List<Object> params = new ArrayList<>();
		if(req2Map!=null) {
			if(req2Map.get("account")==null) {
				result = Data2JsonUtils.getResponseString(1, "ȱ���˺Ų���", null);
			}else if(req2Map.get("email")==null) {
				result = Data2JsonUtils.getResponseString(1, "ȱ���������", null);
			}else if(req2Map.get("mark")==null) {
				result = Data2JsonUtils.getResponseString(1, "ȱ����˲���", null);
			}
			System.out.println("mark="+req2Map.get("mark"));
			params.add(req2Map.get("account"));
			String email = (String)req2Map.get("email");
			if("0".equals(req2Map.get("mark"))) {
				String sql = "UPDATE user_table SET mark = '1' WHERE account = ?";
				DbDao dao = new DbDaoImpl();
				if(dao.addDelUp(sql, params)) {
					//��������
					try {
						WebUtils.sendEmail(email, 0);
					} catch (Exception e) {
						e.printStackTrace();
					}
					result = Data2JsonUtils.getResponseString(0, "����ˣ����ѷ����ʼ�", null);
				}else {
					try {
						WebUtils.sendEmail(email, 1);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					result = Data2JsonUtils.getResponseString(1, "��������쳣", null);
				}
				
			}else if("1".equals(req2Map.get("mark"))) {
				//��������
				try {
					WebUtils.sendEmail(email, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				result = Data2JsonUtils.getResponseString(0, "����ˣ����ѷ����ʼ�", null);
			}
		}else {
			result = Data2JsonUtils.getResponseString(1, "ȱ�ٲ���", null);
		}
		response.getWriter().write(result);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	

}
