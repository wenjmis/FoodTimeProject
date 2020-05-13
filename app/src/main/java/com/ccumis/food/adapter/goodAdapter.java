package com.ccumis.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccumis.food.Goods_view;
import com.ccumis.food.Model.commodity;
import com.ccumis.food.R;
import com.ccumis.food.chatroom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.List;

public class goodAdapter extends RecyclerView.Adapter<goodAdapter.ViewHolder> {
    private Context context;
    private List<commodity> commodities;

    public goodAdapter(Context context, List<commodity> commodities) {
        this.context = context;
        this.commodities = commodities;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_item,parent,false);
        return new goodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final commodity commodity = commodities.get(position);

        holder.good_name.setText(commodity.good_name);
        holder.good_distribution.setText(commodity.good_distribution);
        holder.last_time.setText(commodity.last_time);
        //holder.profile_image.setImageURI("");
        if(commodity.good_price.equals("0")) {
            holder.category.setText("免費");
        }
        else
            holder.category.setText("未提供");

        if(commodity.user_id.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            holder.username.setText("你自己");
            holder.background.setBackgroundColor(Color.WHITE);
        }
        else {
            holder.username.setText(commodity.name);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent =  new Intent(context, Goods_view.class);
            Bundle bundle = new Bundle();

            bundle.putString("address",commodity.address);
            bundle.putString("good_distribution",commodity.good_distribution);
            bundle.putString("good_name",commodity.good_name);
            bundle.putString("receiver_id",commodity.user_id);
            bundle.putString("good_price",commodity.good_price);
            bundle.putString("last_time",commodity.last_time);
            bundle.putString("name",commodity.name);

            intent.putExtras(bundle);
            context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return commodities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username,good_name,last_time,good_distribution,category;
        public ImageView profile_image;
        public RelativeLayout background;
        public ViewHolder(View interview){
            super(interview);
            username = interview.findViewById(R.id.username);
            profile_image = interview.findViewById(R.id.profile_image);
            good_name = interview.findViewById(R.id.good_name);
            last_time = interview.findViewById(R.id.last_time);
            good_distribution = interview.findViewById(R.id.good_distribution);
            category = interview.findViewById(R.id.category);
            background = interview.findViewById(R.id.background);
        }
    }
}
