package dao.impl;

import java.util.List;
import java.util.Map;

import dao.DbDao;
import utils.DaoUtils;

public class DbDaoImpl implements DbDao {
	private DaoUtils dao;
	public DbDaoImpl() {
		dao = new DaoUtils();
	}
	@Override
	public List<Map<String, Object>> queryData(String sql, List<Object> params) {
		return dao.queryData(sql, params);
	}
	@Override
	public boolean addDelUp(String sql, List<Object> params) {
		boolean flag = true;
		try {
			dao.addDelUp(sql, params);
		}catch (Exception e) {
			flag = false;
		}
		return flag;
	}

}
