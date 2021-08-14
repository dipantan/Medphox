package com.example.medphox.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.medphox.R;
import com.example.medphox.model.TestModel;

import java.util.ArrayList;

public class AdapterTest extends RecyclerView.Adapter<AdapterTest.MyViewHolder> {
    private final ArrayList<TestModel> list;
    private final Context context;

    public AdapterTest(ArrayList<TestModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false);
        return new MyViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TestModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.price.setText("\u20B9" + model.getPrice());
//        holder.desc.setText(model.getDescription());
        Glide.with(context).load("https://assets.pharmeasy.in/web-assets/dist/951a8017.png").into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_test);
            price = itemView.findViewById(R.id.price_test);
            imageView = itemView.findViewById(R.id.image_list_test);
        }
    }
}

