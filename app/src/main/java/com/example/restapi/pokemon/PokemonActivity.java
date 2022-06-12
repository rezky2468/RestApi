package com.example.restapi.pokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restapi.R;
import com.example.restapi.menu.MenuActivity;
import com.example.restapi.menu.MenuAdapter;
import com.example.restapi.menu.MenuHelperClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PokemonActivity extends AppCompatActivity implements PokemonAdapter.OnItemClickListener {

    String myUrl = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json";
    String imageUrl;
    TextView resultsTextView;
    ProgressDialog progressDialog;
    Button displayData;

    RecyclerView recyclerView;
    PokemonAdapter pokemonAdapter;

    ArrayList<Pokemon> arrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

//        resultsTextView = findViewById(R.id.results);
//        displayData = findViewById(R.id.displayData);
        recyclerView = findViewById(R.id.pokemon_recycler_view);

        arrayList = new ArrayList<>();

//        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
//        myAsyncTasks.execute();



        arrayList = new ArrayList<>();
        Pokemon pokemon = new Pokemon();
        arrayList.add(pokemon);
//
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));

        pokemonAdapter = new PokemonAdapter(getApplicationContext(), arrayList, imageUrl);
        recyclerView.setAdapter(pokemonAdapter);
        pokemonAdapter.setOnItemClickListener(PokemonActivity.this);

//        // implement setOnClickListener event on displayData button
//        displayData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // create object of MyAsyncTasks class and execute it
//                MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
//                myAsyncTasks.execute();
//
//            }
//        });
    }

    @Override
    public void onItemClickListener(int position) {

    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // display a progress dialog to show the user what is happening
            // display a progress dialog for good user experiance
            progressDialog = new ProgressDialog(PokemonActivity.this);
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
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(myUrl);

                    // open a URL connection
                    urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                    int data = inputStreamReader.read();
                    while (data != -1) {
                        result += (char) data;
                        data = inputStreamReader.read();
                    }

                    // return the data to onPostExecute method
                    return result;

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
            // show results
            // dismiss the progress dialog after receiving data from API
            progressDialog.dismiss();
            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray1 = jsonObject.getJSONArray("pokemon");

//                for (int i = 0; i < jsonArray1.length(); i++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(1);
//                    Pokemon pokemon = new Pokemon();
//                    pokemon.setImg(jsonObject1.getString("img"));
//                    arrayList.add(pokemon);
//                }

                imageUrl = jsonObject1.getString("img");

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));

                pokemonAdapter = new PokemonAdapter(getApplicationContext(), arrayList, imageUrl);
                recyclerView.setAdapter(pokemonAdapter);
                pokemonAdapter.setOnItemClickListener(PokemonActivity.this);

                Toast.makeText(PokemonActivity.this, imageUrl, Toast.LENGTH_SHORT).show();


//                //Show the Textview after fetching data
//                resultsTextView.setVisibility(View.VISIBLE);
//
//                //Display data with the Textview
//                resultsTextView.setText(name);

//                new DownloadImageFromInternet((ImageView) findViewById(R.id.image_view)).execute(image);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
//
//    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
//        ImageView imageView;
//
//        public DownloadImageFromInternet(ImageView imageView) {
//            this.imageView = imageView;
//            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String imageURL = urls[0];
//            Bitmap bimage = null;
//            try {
//                InputStream in = new java.net.URL(imageURL).openStream();
//                bimage = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error Message", e.getMessage());
//                e.printStackTrace();
//            }
//            return bimage;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            imageView.setImageBitmap(result);
//        }
//    }


    ////////////////////////////////////////////
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

//    public class MyAsyncTasks extends AsyncTask<String, String, String> {
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
//        @Override
//        protected String doInBackground(String... strings) {
//            // Fetch data from the API in the background.
//            String result = "";
//            try {
//                URL url;
//                HttpsURLConnection urlConnection = null;
//                try {
//
//                    url = new URL(myUrl);
//
//                    // open a URL coonnection
//                    urlConnection = (HttpsURLConnection) url.openConnection();
//                    urlConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");
//                    urlConnection.setRequestProperty("Accept", "application/vnd.github.v3+json");
//                    urlConnection.setRequestProperty("Contact-Me", "hathibelagal@example.com");
//
//                    urlConnection.setRequestProperty("Content-Type", "application/json");
//
//                    // OPTIONAL - Sets an authorization header
//                    urlConnection.setRequestProperty("Authorization", "someAuthString");
//
//                    String myData = "message=Hello"; // Create the data
//                    urlConnection.setDoInput(true);;
//                    urlConnection.setDoOutput(true); // Enable writing
//                    urlConnection.setRequestMethod("POST");
//
//                    urlConnection.getOutputStream().write(myData.getBytes()); // Write the data
//
//                    // Send the post body
//                    if (this.postData != null) {
//                        OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
//                        writer.write(postData.toString());
//                        writer.flush();
//                    }
//
//                    if (urlConnection.getResponseCode() == 200) {
//                        // Success
//                        // Further processing here
//                        InputStream inputStream = urlConnection.getInputStream();
//                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//
//                        int data = inputStreamReader.read();
//                        while (data != -1) {
//                            result += (char) data;
//                            data = inputStreamReader.read();
//                        }
//
//                        Toast.makeText(getApplicationContext(), "Sukses", Toast.LENGTH_SHORT).show();
//
//                        // return the data to onPostExecute method
//                        return result;
//
//                    } else {
//                        // Error handling code goes here
//                        Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    if (urlConnection != null) {
//                        urlConnection.disconnect();
//                    }
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                return "Exception: " + e.getMessage();
//            }
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            // show results
//            // dismiss the progress dialog after receiving data from API
//            progressDialog.dismiss();
//            try {
//
//                JSONObject jsonObject = new JSONObject(s);
//                JSONArray jsonArray1 = jsonObject.getJSONArray("pokemon");
//                JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
//
//                String name = jsonObject1.getString("name");
//                String image = jsonObject1.getString("img");
//
//                //Show the Textview after fetching data
////                resultsTextView.setVisibility(View.VISIBLE);
//
//                //Display data with the Textview
////                resultsTextView.setText(name);
//
////                JsonReader jsonReader = new JsonReader(responseBodyReader);
////                jsonReader.beginObject(); // Start processing the JSON object
////                while (jsonReader.hasNext()) { // Loop through all keys
////                    String key = jsonReader.nextName(); // Fetch the next key
////                    if (key.equals("organization_url")) { // Check if desired key
////                        // Fetch the value as a String
////                        String value = jsonReader.nextString();
////
////                        // Do something with the value
////                        // ...
////
////                        break; // Break out of the loop
////                    } else {
////                        jsonReader.skipValue(); // Skip values of other keys
////                    }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}