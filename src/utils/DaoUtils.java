package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.alibaba.druid.pool.DruidDataSource;

public class DaoUtils {
	private static DruidDataSource dataSource = null;
	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public DaoUtils() {
		if (dataSource == null) {
			dataSource = new DruidDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql://localhost:3306/yuyueSystem");
			dataSource.setUsername("root");
			dataSource.setPassword("157382");
			dataSource.setInitialSize(10);
			dataSource.setMinIdle(0);
			dataSource.setMaxActive(20);
		}
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 返回连接
	public Connection getConnection() {
		try {
			if (connection == null) {
				connection = dataSource.getConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;

	}

	// 释放连接
	public void releaseConnection(Connection connection) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (ps != null) {
				ps.close();
				ps = null;
			}
			if (connection != null) {
				connection.close();
				connection = null;
			}
		} catch (SQLException e) {
			System.out.println("释放资源");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {
		// Connection connection = new DaoUtils().getConnection();
		// Statement createStatement = connection.createStatement();
		// ResultSet executeQuery = createStatement.executeQuery("select * from
		// user_table");
		// while(executeQuery.next()) {
		// System.out.println(executeQuery.getString(1));
		// }
		// System.out.println(executeQuery);
		// System.out.println(new DaoUtils().getConnection());
		DaoUtils daoUtils = new DaoUtils();
		List<Object> params = new ArrayList<>();
		params.add("195140040");
		// System.out.println(daoUtils.getConnection());
		System.out.println(daoUtils.queryData("select * from user_table where account = ?", params));
	}

	// 增删改操作
	public void addDelUp(String sql, List<Object> params) throws Exception {
		if (ps == null) {
			try {
				ps = connection.prepareStatement(sql);
				for (int i = 0; i < params.size(); i++) {
					ps.setObject(i + 1, params.get(i));
				}
				// boolean execute = ps.execute();
				boolean execute = true;
				int executeUpdate = ps.executeUpdate();
				if (executeUpdate > 0) {
					execute = true;
				} else {
					execute = false;
				}
				if (execute) {
					System.out.println("操作成功");
				} else {
					System.out.println("发生异常");

					throw new Exception();
				}
				releaseConnection(connection);
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	public List<Map<String, Object>> queryData(String sql, List<Object> params) {

		List<Map<String, Object>> result2List = null;
		if (ps == null) {
			try {
				ps = connection.prepareStatement(sql);
				if (params != null) {
					for (int i = 0; i < params.size(); i++) {
						ps.setObject(i + 1, params.get(i));
					}

				}
				ResultSet executeQuery = ps.executeQuery();
				result2List = result2List(executeQuery);
				// executeQuery.next();
				// System.out.println(executeQuery.getObject(1));
				releaseConnection(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result2List;
	}

	// 将查询结果封装成一个list集合
	public List<Map<String, Object>> result2List(ResultSet rs) {
		List<Map<String, Object>> resultLists = new ArrayList<>();
		try {
			ResultSetMetaData metaData = rs.getMetaData();
			int column = metaData.getColumnCount();
			while (rs.next()) {
				Map<String, Object> oneRaw = new HashMap<>();
				
				for (int i = 0; i < column; i++) {
					if(metaData.getColumnName(i+1).contains("Time")) {
						System.out.println(rs.getTimestamp(i+1));
						oneRaw.put(metaData.getColumnName(i + 1), rs.getDate(i+1)+" "+rs.getTime(i+1));
					}else {
						oneRaw.put(metaData.getColumnName(i + 1), rs.getObject(i + 1));
					}
				}
				resultLists.add(oneRaw);
			}
		} catch (SQLException e) {
			System.out.println("数据发生错误");
			e.printStackTrace();
		}
		return resultLists;
	}

}
