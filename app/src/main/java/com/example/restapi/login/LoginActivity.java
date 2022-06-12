package com.example.restapi.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.restapi.pokemon.PokemonActivity;
import com.example.restapi.R;
import com.example.restapi.RegisterActivity;
import com.example.restapi.menu.MenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton, registerButton, pokemonButton;
    ProgressDialog progressDialog;
    String myUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.login_username_edit_text);
        passwordEditText = findViewById(R.id.login_password_edit_text);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.login_register_button);
        pokemonButton = findViewById(R.id.pokemon_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        pokemonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PokemonActivity.class);
                startActivity(intent);
            }
        });

    }

//    public class MyAsyncTasks extends AsyncTask<String, String, String> {
//
//        // This is the JSON body of the post
//        JSONObject postData;
//
//        // This is a constructor that allows you to pass in the JSON body
//        public HttpPostAsyncTask(Map<String, String> postData) {
//            if (postData != null) {
//                this.postData = new JSONObject(postData);
//            }
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            // display a progress dialog to show the user what is happening
//            // display a progress dialog for good user experiance
//            progressDialog = new ProgressDialog(LoginActivity.this);
//            progressDialog.setMessage("processing results");
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//        }
//
//        // This is a function that we are overriding from AsyncTask.
//        // It takes Strings as parameters because that is what we defined for the parameters of our async task
//        @Override
//        protected String doInBackground(String... strings) {
//
//            try {
//
//                // This is getting the url from the string we passed in
//                URL url = new URL("[IP_SERVER]/login");
//
//                // Create the urlConnection
//                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//
//                urlConnection.setDoInput(true);
//                urlConnection.setDoOutput(true);
//
//                urlConnection.setRequestProperty("Content-Type", "application/json");
//
//                urlConnection.setRequestMethod("POST");
//
//                // OPTIONAL - Sets an authorization header
//                urlConnection.setRequestProperty("Authorization", "someAuthString");
//
//                // Send the post body
//                if (this.postData != null) {
//                    OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
//                    writer.write(postData.toString());
//                    writer.flush();
//                }
//
//                int statusCode = urlConnection.getResponseCode();
//
//                if (statusCode ==  200) {
//
//                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
//                    String response = convertInputStreamToString(inputStream);
//
//                    // From here you can convert the string to JSON with whatever JSON parser you like to use
//                    // After converting the string to JSON, I call my custom callback.
//                    // You can follow this process too, or you can implement the onPostExecute(Result) method
//
//                    Map<String, String> postData = new HashMap<>();
//                    postData.put("param1", param1);
//                    postData.put("anotherParam", anotherParam);
//                    PostTask task = new HttpPostAsyncTask(postData);
//                    task.execute(baseUrl + "/some/path/goes/here");
//
//                } else {
//                    // Status code is not 200
//                    // Do something to handle the error
//                }
//
//            } catch (Exception e) {
//                Log.d("error", e.getLocalizedMessage());
//            }
//            return null;
//        }
//
//
//    }
//
//    private String convertInputStreamToString(InputStream inputStream) {
//        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
//        StringBuilder sb = new StringBuilder();
//        String line;
//        try {
//            while((line = bufferedReader.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return sb.toString();
//    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog to show the user what is happening
            progressDialog = new ProgressDialog(LoginActivity.this);
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

                    String myData = "message=Hello"; // Create the data
                    urlConnection.setDoInput(true);;
                    urlConnection.setDoOutput(true); // Enable writing
                    urlConnection.setRequestMethod("POST");

                    urlConnection.getOutputStream().write(myData.getBytes()); // Write the data

//                    // Send the post body
//                    if (this.postData != null) {
//                        OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
//                        writer.write(postData.toString());
//                        writer.flush();
//                    }

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

                JSONObject jsonObject = new JSONObject(s);

                String name = jsonObject.getString("username");
                String image = jsonObject.getString("password");


//                JsonReader jsonReader = new JsonReader(responseBodyReader);
//                jsonReader.beginObject(); // Start processing the JSON object
//                while (jsonReader.hasNext()) { // Loop through all keys
//                    String key = jsonReader.nextName(); // Fetch the next key
//                    if (key.equals("organization_url")) { // Check if desired key
//                        // Fetch the value as a String
//                        String value = jsonReader.nextString();
//
//                        // Do something with the value
//                        // ...
//
//                        break; // Break out of the loop
//                    } else {
//                        jsonReader.skipValue(); // Skip values of other keys
//                    }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}