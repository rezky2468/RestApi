package com.example.restapi.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restapi.R;
import com.example.restapi.menu.MenuActivity;
import com.example.restapi.menu.MenuAdapter;
import com.example.restapi.menu.MenuHelperClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RegisterActivity extends AppCompatActivity {

    private Button button;
    private EditText usernameEditText, passwordEditText, confirmPasswordEditText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        button = findViewById(R.id.register_button);
        usernameEditText = findViewById(R.id.register_username_edit_text);
        passwordEditText = findViewById(R.id.register_password_edit_text);
        confirmPasswordEditText = findViewById(R.id.register_confirm_password_edit_text);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (usernameEditText.getText().toString().equals("") || passwordEditText.getText().toString().equals("") || confirmPasswordEditText.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else if (passwordEditText.getText().toString() != confirmPasswordEditText.getText().toString()) {
                    Toast.makeText(RegisterActivity.this, "Password tidak sama", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                }
            }
        });

//        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
//        myAsyncTasks.execute("[IP_SERVER]/register");

    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage("processing results");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                String myData = "message=Hello"; // Create the data
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.connect();

                if (connection.getResponseCode() == 200) { // Success, Further processing here

                    InputStream inputStream = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(inputStream));

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


            } else {

            }

        }
    }

}