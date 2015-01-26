package com.epam.dziashko.aliaksei.ormapp.dao;

import com.epam.dziashko.aliaksei.ormapp.model.Client;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public class ClientDao extends BaseDaoImpl<Client, Integer> {

    public ClientDao(ConnectionSource connectionSource, Class<Client> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<Client> getAllClients() throws SQLException{
        return this.queryForAll();
    }
}
