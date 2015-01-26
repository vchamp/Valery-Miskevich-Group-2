package com.epam.dziashko.aliaksei.ormapp.model;

import com.epam.dziashko.aliaksei.ormapp.db.HelperFactory;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.sql.SQLException;
import java.util.Collection;

public class Client {

    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String password;

    @ForeignCollectionField(eager = true)
    private Collection<Account> accounts;

    public Client() {

    }

    public void addAccount(Account value) {
        value.setClient(this);
        try {
            HelperFactory.getHelper().getAccountDao().create(value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        accounts.add(value);
    }

    public void removeAccount(Account value) {
        accounts.remove(value);
        try {
            HelperFactory.getHelper().getAccountDao().delete(value);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Account> getAccountList() {
        return accounts;
    }

    public void setRoleList(Collection<Account> list) {
        this.accounts = list;
    }
}
