package com.epam.dziashko.aliaksei.ormapp.dao;

import com.epam.dziashko.aliaksei.ormapp.model.Account;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class AccountDao extends BaseDaoImpl<Account, Integer> {

    public AccountDao(ConnectionSource connectionSource, Class<Account> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Account> getAllAccounts() throws SQLException{
        return this.queryForAll();
    }
}
