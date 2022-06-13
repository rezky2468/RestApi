package com.example.restapi.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.restapi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity implements MenuAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ProgressDialog progressDialog;

    MenuAdapter menuAdapter;
    ArrayList<MenuHelperClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.floating_action_button);

        arrayList = new ArrayList<>();

//        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
//        myAsyncTasks.execute("[IP_SERVER]/menu");
        
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onEditClickListener(int position) {
        Intent intent = new Intent(getApplicationContext(), AddEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("EDIT_TEACHER", arrayList);
        intent.putExtra("IDENTIFIER", "edit");
        intent.putExtra("EDIT_TEACHER_ID", String.valueOf(position));
        intent.putExtras(bundle);
        startActivity(intent);
        Toast.makeText(this, "Edit clicked at position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteClickListener(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
        builder.setMessage("Yakin ingin menghapus data ini?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Delete data
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        Toast.makeText(this, "Delete clicked at position: " + position, Toast.LENGTH_SHORT).show();
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MenuActivity.this);
            progressDialog.setMessage("processing results");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "{{access_token}}");
                connection.connect();

                if (connection.getResponseCode() == 200) { // Success, Further processing here

                    InputStream stream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();
                    String line = "";

                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                        Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                    }

                    return buffer.toString();

                } else if (connection.getResponseCode() == 400) {
                    Toast.makeText(getApplicationContext(), "Fail (missing field / unexpected value)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Fail (missing field / unexpected value)", Toast.LENGTH_SHORT).show();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();
            if (result != null) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("pokemon");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        MenuHelperClass menuHelperClass = new MenuHelperClass();
                        menuHelperClass.setName(jsonArray.getJSONObject(i).getString("name"));
                        menuHelperClass.setDescription(jsonArray.getJSONObject(i).getString("description"));
                        menuHelperClass.setPrice(jsonArray.getJSONObject(i).getString("price"));
                        arrayList.add(menuHelperClass);
                    }
                    Toast.makeText(MenuActivity.this, arrayList.size() + " item berhasil dipanggil", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                menuAdapter = new MenuAdapter(getApplicationContext(), arrayList);
                menuAdapter.setOnItemClickListener(MenuActivity.this);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(menuAdapter);

            } else {
                Toast.makeText(MenuActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }
    }
}