/**
 * 
 */
package com.epam.trn.dao.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;

/**
 * @author Siarhei Klimuts
 *
 */
public class IdCallback implements PreparedStatementCallback<Long> {
	@Override
	public Long doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
		Long result = null;
		ResultSet rs = ps.executeQuery();
	    if(rs.next()) {
	    	result =  rs.getLong("ID");
	    }
	    rs.close();
	    return result;
	}
}
