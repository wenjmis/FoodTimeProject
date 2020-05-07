package com.ccumis.food.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ccumis.food.Model.Message;
import com.ccumis.food.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.holder> {

    public  static  final int MSG_TYPE_LEFT = 0;
    public  static  final int MSG_TYPE_RIGHT = 1;

    private FirebaseUser firebaseUser;
    private Context context;
    private List<Message> user;


    private String imageurl;

    public MessageAdapter(Context context, List<Message> user,String imageurl) {
        this.context = context;
        this.user = user;
        this.imageurl=imageurl;

    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
            return new MessageAdapter.holder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new MessageAdapter.holder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        Message message = user.get(position);
        holder.username.setText(message.msg);

    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(user.get(position).sender_id.equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else
            return MSG_TYPE_LEFT;
    }

    public class holder extends RecyclerView.ViewHolder{

            public TextView username;
            public ImageView profile_image;
            public holder(View itemview){
                super(itemview);
                username = itemview.findViewById(R.id.show_message);
                profile_image = itemview.findViewById(R.id.profile_image);
            }
    }
}
