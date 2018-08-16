package dao;

import java.util.List;
import java.util.Map;

public interface DbDao {
	//查询接口
	public List<Map<String, Object>> queryData(String sql, List<Object> params);
	//增删改接口
	public boolean addDelUp(String sql, List<Object> params);
}
