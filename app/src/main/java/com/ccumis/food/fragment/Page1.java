package com.ccumis.food.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccumis.food.Model.Message;
import com.ccumis.food.Model.commodity;
import com.ccumis.food.R;
import com.ccumis.food.adapter.chatAdapter;
import com.ccumis.food.adapter.goodAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Page1 extends Fragment {
    private  goodAdapter goodAdapter;
    private List<commodity> commodities;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refresh_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page1, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commodities = new ArrayList<>();
        refresh_layout = view.findViewById(R.id.refresh_layout);
        refresh_layout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                list_Goods();
                refresh_layout.setRefreshing(false);
            }
        });
        list_Goods();
        return view;
    }

    private void list_Goods() {
        final FirebaseUser firebaseUser =  FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("commodity");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                commodities.clear();
                for(DataSnapshot Snapshot : dataSnapshot.getChildren()){
                    commodity commodity = Snapshot.getValue(com.ccumis.food.Model.commodity.class);
                    commodities.add(commodity);
                }
                goodAdapter = new goodAdapter(getContext(),commodities);
                recyclerView.setAdapter(goodAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
