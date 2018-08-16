package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;



public class HttpUtils {
	//封装request的请求参数，封装成一个map集合
	public static Map<String, Object> getReq2Map(HttpServletRequest request) {
		Map<String, Object> params = new HashMap<>();
		if ("POST".equals(request.getMethod().toUpperCase())) {
			Map<String, String[]> parameterMap = request.getParameterMap();
			for (String key : parameterMap.keySet()) {
				params.put(key, request.getParameter(key));
			}
		}else if("GET".equals(request.getMethod().toUpperCase())){
			String queryString = request.getQueryString();
			//System.out.println(queryString);
			String[] split = queryString.split("&");
			for(int i = 0;i<split.length;i++) {
				//System.out.println(split[i].split("=")+split[i].split("=")[1]);
				params.put(split[i].split("=")[0],split[i].split("=")[1]);
			}
			
		}else {
			params = null;
		}
		return params;
	}
	//遍历map集合
	public static void queryMap(Map<String, Object> params) {
		System.out.println("map中的数据如下");
		for ( String key : params.keySet()) {
			System.out.println(key+":"+params.get(key));
		}
	}
	 public static void main(String[] args) throws Exception {
	       
	    }


}
