package com.epam.dziashko.aliaksei.ormapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.epam.dziashko.aliaksei.ormapp.db.HelperFactory;
import com.epam.dziashko.aliaksei.ormapp.model.Account;
import com.epam.dziashko.aliaksei.ormapp.model.Client;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class DetailActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private AccountAdapter accountAdapter;
    private int clientId;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processIntent(getIntent());
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(createAdapter());
        listView.setOnItemClickListener(this);
        Button addButton = (Button) findViewById(R.id.btn_add);
        addButton.setText("add account");
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                addAccount();
            }
        });


    }

    @Override protected void onResume() {
        super.onResume();
        accountAdapter.notifyDataSetChanged();
    }

    private void processIntent(Intent intent) {
        clientId = intent.getIntExtra("id", 0);
        try {
            client = HelperFactory.getHelper().getClientDao().queryForId(clientId);
            setTitle("Client: "+client.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ListAdapter createAdapter() {
        accountAdapter = new AccountAdapter(clientId);
        return accountAdapter;
    }

    private void addAccount() {

        Account account = new Account();
        account.setName("New account" + new Random().nextInt());
        account.setPassword(new Random().nextInt() + "");
        account.setClient(client);
        try {
            HelperFactory.getHelper().getAccountDao().create(account);
            accountAdapter.notifyDataSetChanged();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Account account = (Account) parent.getItemAtPosition(position);
        final Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra("id", account.getId());
        startActivity(intent);
    }

    class AccountAdapter extends BaseAdapter {

        private final int clientId;
        private List<Account> accountList;

        public AccountAdapter(int clientId) {
            this.clientId = clientId;
            updateData();
        }

        @Override public int getCount() {
            return accountList.size();
        }

        @Override public Object getItem(int position) {
            return accountList.get(position);
        }

        @Override public long getItemId(int position) {
            return position;
        }

        @Override public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getBaseContext(), R.layout.list_item_client, null);
            } else {
                view = convertView;
            }
            bindView(view, accountList.get(position));
            return view;
        }

        private void bindView(View view, final Account account) {
            ((TextView) view.findViewById(R.id.name)).setText(account.getName());
            view.findViewById(R.id.btn_del_client).setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    try {
                        HelperFactory.getHelper().getAccountDao().delete(account);
                        notifyDataSetChanged();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @Override public void notifyDataSetChanged() {
            updateData();
            super.notifyDataSetChanged();
        }

        private void updateData() {
            try {
                final Collection<Account> accountList1 = HelperFactory.getHelper().getClientDao().queryForId(clientId).getAccountList();
                accountList = new ArrayList<>(accountList1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
