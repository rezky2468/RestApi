package com.example.restapi.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.restapi.R;
import com.example.restapi.menu.MenuAdapter;
import com.example.restapi.menu.MenuHelperClass;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private Context context;
    private ArrayList<Pokemon> arrayList;
    private OnItemClickListener listener;
    private String imageUrl;

    private String myUrl = "https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json";
    private ProgressDialog progressDialog;

    public PokemonAdapter(Context context, ArrayList<Pokemon> arrayList, String imageUrl) {
        this.context = context;
        this.arrayList = arrayList;
        this.imageUrl = imageUrl;
    }

    @NonNull
    @NotNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_pokemon, parent, false);
        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PokemonAdapter.PokemonViewHolder holder, int position) {
//        Pokemon pokemon = arrayList.get(position);
        Glide.with(context).load("https://www.serebii.net/pokemongo/pokemon/002.png").into(holder.imageView);
//        Toast.makeText(context, arrayList.size(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class PokemonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;

        public PokemonViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.pokemon_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClickListener(position);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}