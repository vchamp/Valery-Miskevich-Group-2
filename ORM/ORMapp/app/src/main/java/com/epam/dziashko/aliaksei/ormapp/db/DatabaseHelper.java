package com.epam.dziashko.aliaksei.ormapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.epam.dziashko.aliaksei.ormapp.dao.AccountDao;
import com.epam.dziashko.aliaksei.ormapp.dao.ClientDao;
import com.epam.dziashko.aliaksei.ormapp.model.Account;
import com.epam.dziashko.aliaksei.ormapp.model.Client;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME ="ormapp.db";

    private static final int DATABASE_VERSION = 1;

    private ClientDao clientDao = null;
    private AccountDao accountDao = null;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource){
        try
        {
            TableUtils.createTable(connectionSource, Client.class);
            TableUtils.createTable(connectionSource, Account.class);
        }
        catch (SQLException e){
            Log.e(TAG, "error creating DB " + DATABASE_NAME);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer,
                          int newVer){
        try{
            TableUtils.dropTable(connectionSource, Client.class, true);
            TableUtils.dropTable(connectionSource, Account.class, true);
            onCreate(db, connectionSource);
        }
        catch (SQLException e){
            Log.e(TAG,"error upgrading db "+DATABASE_NAME+"from ver "+oldVer);
            throw new RuntimeException(e);
        }
    }

    public ClientDao getClientDao() throws SQLException{
        if(clientDao == null){
            clientDao = new ClientDao(getConnectionSource(), Client.class);
        }
        return clientDao;
    }
    public AccountDao getAccountDao() throws SQLException{
        if(accountDao == null){
            accountDao = new AccountDao(getConnectionSource(), Account.class);
        }
        return accountDao;
    }

    @Override
    public void close(){
        super.close();
        clientDao = null;
        accountDao = null;
    }
}
