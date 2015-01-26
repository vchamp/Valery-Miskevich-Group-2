package com.epam.dziashko.aliaksei.ormapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epam.dziashko.aliaksei.ormapp.db.HelperFactory;
import com.epam.dziashko.aliaksei.ormapp.model.Account;

import java.sql.SQLException;

public class AccountActivity extends ActionBarActivity implements View.OnClickListener {

    private Account account;
    private EditText editTextName;
    private EditText editTextPass;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acc);

        final int id = getIntent().getIntExtra("id", 0);
        try {
            account = HelperFactory.getHelper().getAccountDao().queryForId(id);
            editTextName = (EditText) findViewById(R.id.edit_name);
            editTextName.setText(account.getName());

            editTextPass = (EditText) findViewById(R.id.edit_pass);
            editTextPass.setText(account.getPassword());

            Button button = (Button) findViewById(R.id.save);
            button.setOnClickListener(this);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override public void onClick(View v) {
        try {
            account.setName(editTextName.getText().toString());
            account.setPassword(editTextPass.getText().toString());
            HelperFactory.getHelper().getAccountDao().update(account);
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
