package com.example.restapi.pokemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.restapi.R;

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
import java.util.HashMap;

public class PokemonActivity extends AppCompatActivity implements PokemonAdapter.OnItemClickListener {

    TextView textView;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    PokemonAdapter pokemonAdapter;
    ArrayList<Pokemon> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);

        recyclerView = findViewById(R.id.pokemon_recycler_view);
        textView = findViewById(R.id.textV);

        arrayList = new ArrayList<>();

        MyAsyncTasks myAsyncTasks = new MyAsyncTasks();
        myAsyncTasks.execute("https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json");

    }

    @Override
    public void onItemClickListener(int position) {
        Pokemon pokemon = arrayList.get(position);
        String height = pokemon.getHeight();
        String weight = pokemon.getWeight();
        Toast.makeText(this, "Tinggi: " + height + "\nBerat: " + weight, Toast.LENGTH_SHORT).show();
    }

    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(PokemonActivity.this);
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
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();

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
                        Pokemon pokemon = new Pokemon();
                        pokemon.setName(jsonArray.getJSONObject(i).getString("name"));
                        pokemon.setHeight(jsonArray.getJSONObject(i).getString("height"));
                        pokemon.setWeight(jsonArray.getJSONObject(i).getString("weight"));
                        pokemon.setImg(jsonArray.getJSONObject(i).getString("img"));
                        arrayList.add(pokemon);
                    }
                    Toast.makeText(PokemonActivity.this, arrayList.size() + " item berhasil dipanggil", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                pokemonAdapter = new PokemonAdapter(getApplicationContext(), arrayList);
                pokemonAdapter.setOnItemClickListener(PokemonActivity.this);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2, GridLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(pokemonAdapter);

            } else {
                Toast.makeText(PokemonActivity.this, "Network Problem", Toast.LENGTH_SHORT).show();
            }
        }
    }
}