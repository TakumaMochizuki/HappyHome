package com.happy.home.dao;

import java.sql.SQLException;
import java.util.List;

import android.util.Log;

import com.happy.home.database.DatabaseUtil;
import com.happy.home.model.Facility;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;

public class FacilityDAO extends BaseDAO<Facility, Integer> implements FacilityDAOInterface {
	private static String TAG = FacilityDAO.class.getSimpleName();
	
	
	public FacilityDAO(Class<Facility> dataClass) throws SQLException {
		super(dataClass);
	}
	
	public FacilityDAO(ConnectionSource connectionSource,
			Class<Facility> dataClass) throws SQLException {
		super(connectionSource, dataClass);
	}
	
	public FacilityDAO(ConnectionSource connectionSource,
			DatabaseTableConfig<Facility> tableConfig) throws SQLException {
		super(connectionSource, tableConfig);
	}
	
	public List<Facility> fetch500MeterAround(int type, double x, double y) {
		Log.v(TAG, "fetchDramaTW() ");
		List<Facility> facilities = null;
		
		try {
			QueryBuilder<Facility, Integer> queryBuilder = queryBuilder();
			if (type == 0) {
				queryBuilder.where().ge("x_long", y - 0.009009)
				.and().le("x_long", y + 0.009009)
				.and().ge("y_long", x - 0.009009)
				.and().le("y_long", x + 0.009009);
			} else {
				queryBuilder.where().eq("type", type)
				.and().ge("x_long", y - 0.009009)
				.and().le("x_long", y + 0.009009)
				.and().ge("y_long", x - 0.009009)
				.and().le("y_long", x + 0.009009);
			}
			
			PreparedQuery<Facility> facilityQuery = queryBuilder.prepare();
			facilities = this.queryOrFail(facilityQuery);
		} catch (SQLException e) {
			DatabaseUtil.throwAndroidSQLException(TAG, e);
		}
//		facilities = this.queryForAllOrFail();
		return facilities;
	}
}
