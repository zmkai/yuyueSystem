package dao;

import java.util.List;
import java.util.Map;

public interface DbDao {
	//��ѯ�ӿ�
	public List<Map<String, Object>> queryData(String sql, List<Object> params);
	//��ɾ�Ľӿ�
	public boolean addDelUp(String sql, List<Object> params);
}
