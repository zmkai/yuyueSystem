package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils.Null;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Data2JsonUtils {
	// 将数据封装成json格式返回给前端
	public static String getResponseString(int code, String msg, Object object) {
		JSONObject mydata = new JSONObject();
		mydata.element("code", code);
		mydata.element("msg", msg);
		if(object==null) {
			mydata.element("data", JSONObject.fromObject(null));
			//mydata.element("data", "");
			return mydata.toString();
		}
		System.out.println(object instanceof List);
		if (object instanceof Map) {
			JSONObject fromObject = JSONObject.fromObject(object);
			mydata.element("data", fromObject);
		} else if (object instanceof List) {
			JSONArray fromObject = JSONArray.fromObject(object);
			mydata.element("data", fromObject);
		}else {
			mydata.element("data", object);
		}
		return mydata.toString();
	}

	public static void main(String[] args) {
		Map<Object, Object> map = new HashMap<>();
		map.put("name", "json");
		map.put("bool", Boolean.TRUE);
		map.put("int", new Integer(1));
		map.put("arr", new String[] { "a", "b" });
		map.put("func", "function(i){ returnthis.arr[i]; }");
		System.out.println(getResponseString(0, "陈功", map));
		
		List<String> list = new ArrayList<String>();
		list.add( "first" );
		list.add( "second" );
		System.out.println(getResponseString(0, "陈功", list));
	}
	

}
