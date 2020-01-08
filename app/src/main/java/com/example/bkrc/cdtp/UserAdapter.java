package com.example.bkrc.cdtp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder>{

    List<Users> list;
    Context c;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements ItemClickListener, View.OnClickListener {

        public TextView Username,userId;
        public CardView cardView;
        ItemClickListener ıtemClickListener;

        public MyViewHolder(View v){
            super(v);
            Username=(TextView)v.findViewById(R.id.text_name);
            userId=(TextView)v.findViewById(R.id.text_nnumber);
            cardView = (CardView)v.findViewById(R.id.user_card_cardview);
            v.setOnClickListener(this);
        }

        @Override
        public void onItemClicked(View v, int pos) {

        }

        @Override
        public void onClick(View v) {
            this.ıtemClickListener.onItemClicked(v, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic){
            this.ıtemClickListener=ic;
        }

    }

    public UserAdapter(Context c, List<Users> list){
        this.c=c;
        this.list = list;
    }

    public UserAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.user_carview,parent,false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    public void onBindViewHolder(MyViewHolder holder, final int position){
        holder.Username.setText(list.get(position).getRealname()+" "+list.get(position).getSurname());
        holder.userId.setText(String.valueOf(list.get(position).getId()));
        Animation animation= AnimationUtils.loadAnimation(c,android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClicked(View v, int pos) {
                Intent messageIntent = new Intent(c,MessageActivity.class);
                messageIntent.putExtra("userInfo",list.get(pos));
                c.startActivity(messageIntent);

            }


        });

    }

    public int getItemCount(){
        return list.size();}

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}