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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolder> {
    private Context context;
    private List<Room> rooms;
    private String temp;


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
        final Room room = rooms.get(position);
        if (room.getMenber_1().equals(firebaseUser.getUid())){

            holder.username.setText(room.getMenber_2_name());
            temp = room.getMenber_2();

        }
        else if(room.getMenber_2().equals(firebaseUser.getUid())){
            holder.username.setText(room.getMenber_1_name());
            temp = room.getMenber_1();

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(context, chatroom.class);
                Bundle bundle = new Bundle();
                bundle.putString("name",holder.username.getText().toString());
                bundle.putString("receiver_id",temp);
                bundle.putString("good_name",room.getGood_name());
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
