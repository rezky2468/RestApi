package com.example.restapi.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.restapi.R;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context context;
    private ArrayList<MenuHelperClass> arrayList;
    private OnItemClickListener listener;

    public MenuAdapter(Context context, ArrayList<MenuHelperClass> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull MenuAdapter.MenuViewHolder holder, int position) {
        MenuHelperClass menuHelperClass = arrayList.get(position);
        holder.nameTextView.setText(menuHelperClass.name);
        holder.descriptionTextView.setText(menuHelperClass.description);
        holder.priceTextView.setText(menuHelperClass.price);

//        holder.nameTextView.setText("Burger");
//        holder.descriptionTextView.setText("Very Tasty");
//        holder.priceTextView.setText("50000");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTextView, descriptionTextView, priceTextView;
        private ImageView editImageView, deleteImageView;

        public MenuViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.menu_name_text_view);
            descriptionTextView = itemView.findViewById(R.id.menu_description_text_view);
            priceTextView = itemView.findViewById(R.id.menu_price_text_view);

            editImageView = itemView.findViewById(R.id.edit_image_view);
            deleteImageView = itemView.findViewById(R.id.delete_image_view);

            editImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onEditClickListener(position);
                        }
                    }
                }
            });

            deleteImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClickListener(position);
                        }
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onEditClickListener(int position);

        void onDeleteClickListener(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
