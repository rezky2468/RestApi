package com.example.restapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.restapi.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class RegisterActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    String myUrl = "[IP_SERVER]/register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog to show the user what is happening
            progressDialog = new ProgressDialog(RegisterActivity.this);
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