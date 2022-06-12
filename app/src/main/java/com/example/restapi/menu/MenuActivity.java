package com.example.restapi.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restapi.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MenuActivity extends AppCompatActivity implements MenuAdapter.OnItemClickListener {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    
    MenuAdapter menuAdapter;

    ProgressDialog progressDialog;
    String myUrl = "[IP_SERVER]/menu";
    List<HashMap<String, String>> myList;

    ArrayList<MenuHelperClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.floating_action_button);

        arrayList = new ArrayList<>();
        MenuHelperClass menuHelperClass = new MenuHelperClass();
        menuHelperClass.setName("Burger");
        menuHelperClass.setDescription("Very tasty");
        menuHelperClass.setPrice("50000");
        arrayList.add(menuHelperClass);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        menuAdapter = new MenuAdapter(getApplicationContext(), arrayList);
        recyclerView.setAdapter(menuAdapter);
        menuAdapter.setOnItemClickListener(MenuActivity.this);
        
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MenuActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        String myUrl = "https://api.mocki.io/v1/a44b26bb";
        TextView resultsTextView;
        ProgressDialog progressDialog;
        Button displayData;

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
            // display a progress dialog to show the user what is happening
            progressDialog = new ProgressDialog(MenuActivity.this);
            progressDialog.setMessage("processing results");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            // Fetch data from the API in the background.
            String result = "";
            try {
                URL url;
                HttpsURLConnection urlConnection = null;
                try {

                    url = new URL(myUrl);

                    // open a URL coonnection
                    urlConnection = (HttpsURLConnection) url.openConnection();
                    urlConnection.setDoOutput(true);
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setRequestProperty("Authorization", "{{access_token}}");

                    if (urlConnection.getResponseCode() == 200) { // Success, Further processing here

                        InputStream inputStream = urlConnection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                        int data = inputStreamReader.read();
                        while (data != -1) {
                            result += (char) data;
                            data = inputStreamReader.read();
                        }

                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                        // return the data to onPostExecute method
                        return result;

                    } else if (urlConnection.getResponseCode() == 400) { // Error handling code goes here
                        Toast.makeText(getApplicationContext(), "Fail (missing field / unexpected value)", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            // show results, dismiss the progress dialog after receiving data from API
            progressDialog.dismiss();
            try {

                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    map.put("username", jsonObject.getString("username"));
                    map.put("description", jsonObject.getString("description"));
                    map.put("price", jsonObject.getString("price"));
                    myList.add(map);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}