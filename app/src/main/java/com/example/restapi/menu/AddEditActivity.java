package com.example.restapi.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restapi.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AddEditActivity extends AppCompatActivity {

    private EditText nameEditText, descriptionEditText, priceEditText;
    private Button button;
    private ProgressDialog progressDialog;

    private String identifier, id, name, description, price, json, jsonArr;
    private ArrayList<MenuHelperClass> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        nameEditText = findViewById(R.id.menu_name_edit_text);
        descriptionEditText = findViewById(R.id.menu_description_edit_text);
        priceEditText = findViewById(R.id.menu_price_edit_text);
        button = findViewById(R.id.add_edit_button);

        identifier = getIntent().getStringExtra("IDENTIFIER");
        if (identifier.equals("edit")) {

            arrayList = getIntent().getExtras().getParcelableArrayList("EDIT_MENU");
            id = getIntent().getStringExtra("EDIT_MENU_ID");

            MenuHelperClass menuHelperClass = arrayList.get(Integer.valueOf(id));
            name = menuHelperClass.getName();
            description = menuHelperClass.getDescription();
            price = menuHelperClass.getPrice();

            button.setText("Edit");

            // The Assets folder is read-only

        } else if (identifier.equals("add")) {

            json = getIntent().getExtras().getString("JSON");

            button.setText("Add");

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    name = nameEditText.getText().toString();
                    description = descriptionEditText.getText().toString();
                    price = priceEditText.getText().toString();

//                    JSONObject jsonObject = new JSONObject();
//                    try {
//                        jsonObject.put("name", name);
//                        jsonObject.put("description", description);
//                        jsonObject.put("price", price);
//
                        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
                        myAsyncTasks.execute("https://json.extendsclass.com/bin/f6b795621c4b");
//
//                        JSONArray jsonArray = new JSONArray(result);
//                        jsonArray.put(jsonObject);
//
//                        Toast.makeText(AddEditActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();

//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }



                }
            });

        }

        nameEditText.setText(name);
        descriptionEditText.setText(description);
        priceEditText.setText(price);

    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddEditActivity.this);
            progressDialog.setMessage("processing results");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            String data = "";

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Api-key", "f30b329e-ec8d-11ec-b943-0242ac110002");
                connection.setRequestProperty("Security-key", "SMK_RESTAURANT");
                connection.connect();


                DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes("PostData=" + params[1]);
                wr.flush();
                wr.close();

                InputStream in = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }

//
//                if (connection.getResponseCode() == 200) { // Success, Further processing here
//
//                    InputStream stream = connection.getInputStream();
//
//                    reader = new BufferedReader(new InputStreamReader(stream));
//
//                    StringBuffer buffer = new StringBuffer();
//                    String line = "";
//
//                    while ((line = reader.readLine()) != null) {
//                        buffer.append(line + "\n");
//                        Log.d("Response: ", "> " + line);   // here u ll get whole response...... :-)
//                    }
//
//                    return buffer.toString();
//
//                } else if (connection.getResponseCode() == 400) {
//                    Toast.makeText(getApplicationContext(), "Fail (missing field / unexpected value)", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Fail (missing field / unexpected value)", Toast.LENGTH_SHORT).show();
//                }

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

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", name);
                    jsonObject.put("description", description);
                    jsonObject.put("price", price);

                    JSONArray jsonArray = new JSONArray(result);
                    jsonArray.put(jsonObject);

                    Toast.makeText(AddEditActivity.this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(AddEditActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }
    }

}