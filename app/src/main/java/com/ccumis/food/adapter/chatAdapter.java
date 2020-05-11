package com.ccumis.food.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccumis.food.Model.Room;
import com.ccumis.food.chatroom;
import com.ccumis.food.Model.Message;
import com.ccumis.food.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolder> {
    private Context context;
    private List<Room> rooms;

    public chatAdapter(Context context, List<Room> rooms) {
        this.context = context;
        this.rooms = rooms;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item,parent,false);
        return new chatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Room room = rooms.get(position);
        if (room.getMenber_1().equals(firebaseUser.getUid())){
            holder.username.setText(room.getMenber_2());
        }
        else if(room.getMenber_2().equals(firebaseUser.getUid())){
            holder.username.setText(room.getMenber_1());
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, chatroom.class);
                Bundle bundle = new Bundle();
                bundle.putString("receiver_id",holder.username.getText().toString());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profile_image;
        public ViewHolder(View itemview){
            super(itemview);
            username = itemview.findViewById(R.id.username);
            profile_image = itemview.findViewById(R.id.profile_image);
        }
    }
}
