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
import com.epam.dziashko.aliaksei.ormapp.model.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ClientsAdapter clientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(createAdapter());
        listView.setOnItemClickListener(this);
        Button addButton = (Button) findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                addClient();
            }
        });


    }

    @Override protected void onResume() {
        super.onResume();
        clientsAdapter.notifyDataSetChanged();
    }

    private ListAdapter createAdapter() {
        clientsAdapter = new ClientsAdapter();
        return clientsAdapter;
    }

    private void addClient() {
        Client client = new Client();
        client.setName("New client"+ new Random().nextInt());
        client.setPassword(new Random().nextInt()+"");
        try {
            HelperFactory.getHelper().getClientDao().create(client);
            clientsAdapter.notifyDataSetChanged();
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
        Client client = (Client) parent.getItemAtPosition(position);
        final Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("id", client.getId());
        startActivity(intent);
    }

    class ClientsAdapter extends BaseAdapter {

        private List<Client> clientList;

        public ClientsAdapter() {
            updateData();
        }

        @Override public int getCount() {
            return clientList.size();
        }

        @Override public Object getItem(int position) {
            return clientList.get(position);
        }

        @Override public long getItemId(int position) {
            return clientList.get(position).getId();
        }

        @Override public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(getBaseContext(), R.layout.list_item_client, null);
            } else {
                view = convertView;
            }
            bindView(view, clientList.get(position));
            return view;
        }

        private void bindView(View view, Client client) {
            final int id = client.getId();
            ((TextView) view.findViewById(R.id.name)).setText(client.getName());
            ((TextView) view.findViewById(R.id.info)).setText("Accounts: "+client.getAccountList().size());
            view.findViewById(R.id.btn_del_client).setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    try {
                        HelperFactory.getHelper().getClientDao().deleteById(id);
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
                clientList = HelperFactory.getHelper().getClientDao().getAllClients();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
