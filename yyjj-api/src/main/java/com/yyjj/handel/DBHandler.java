package com.yyjj.handel;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



/**
 * 获得数据库表的元数据
 */
@Component
public class DBHandler {

	@Autowired
	DataSource dataSource;
	
	static Map<String, Map<String, String>> tableNameMap;
	
	private  Set<String> excludeSet;
	
	
	public Set<String> getExcludeSet() {
		return excludeSet;
	}
	
	public void setExcludeSet(Set<String> excludeSet) {
		this.excludeSet = excludeSet;
	}
	
	/**
	 * 构造器初始化excludeSet
	 */
	public DBHandler() {
		excludeSet = new HashSet<String>();
		excludeSet.add("createTime");
		excludeSet.add("updateTime");
		excludeSet.add("password");
		excludeSet.add("userStatus");
	}
	private static final Logger log = LoggerFactory.getLogger(DBHandler.class);
	/**
	 * 获取所有数据表信息
	 * @return
	 */
	public  Map<String, Map<String, String>> getAllTableNameMap() {
		if (tableNameMap == null) {
			tableNameMap = getAllTableColumn();
		}
		
		return tableNameMap;
	}
	
	/**
	 * 获取指定数据表信息
	 * @param key
	 * @return
	 */
	public  Map<String, String> getTableMap(String key){
		
		if (tableNameMap == null) {
			tableNameMap = getAllTableColumn();
		}
		return tableNameMap.get(key);
	}
	
	private  Map<String, Map<String, String>> getAllTableColumn() {
		//log.info("装载数据库信息");
		tableNameMap = new HashMap<>();
		// 获得数据库连接
		Connection connection = null;
		ResultSet tables = null;
		try {
			connection = dataSource.getConnection();
			// 获得元数据
			DatabaseMetaData metaData = connection.getMetaData();
			// 获得表信息
			
			tables = metaData.getTables(null, null, null, new String[] { "TABLE" });
			ResultSet columns = null;
			while (tables.next()) {
				Map<String, String> columnNameMap = new HashMap<>(); // 保存字段名
				// 获得表名
				String table_name = tables.getString("TABLE_NAME");
				// 通过表名获得所有字段名
				columns = metaData.getColumns(null, null, table_name, "%");
				// 获得所有字段名
				while (columns.next()) {
					// 获得字段名
					String column_name = columns.getString("COLUMN_NAME");
					// 获得字段类型
					// String type_name = columns.getString("TYPE_NAME");
					//获得字段注释   注意： 对于此列，SQL Server 始终会返回 Null。
					String remarks = columns.getString("REMARKS");
					//https://docs.microsoft.com/zh-cn/sql/connect/jdbc/reference/getcolumns-method-sqlserverdatabasemetadata?view=sql-server-2017
					//System.out.println(table_name+":"+column_name+":"+remarks);					
					columnNameMap.put(lineToHump(column_name), remarks);
				}
				columns.close();
				tableNameMap.put(lineToHump(table_name), columnNameMap);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {	
				if(tables != null) {					
					tables.close();						
				}
				if(connection != null) {									
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return tableNameMap;
	}
	private static Pattern linePattern = Pattern.compile("_(\\w)");
	public static String lineToHump(String str) {
        str = str.toLowerCase();
     Matcher matcher = linePattern.matcher(str);
      StringBuffer sb = new StringBuffer();
      while (matcher.find()) {
          matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
     }
       matcher.appendTail(sb);
     return sb.toString();
	}
}