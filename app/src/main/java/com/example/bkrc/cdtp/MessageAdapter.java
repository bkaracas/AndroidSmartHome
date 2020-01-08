package com.example.bkrc.cdtp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import static com.example.bkrc.cdtp.SignActivity.user;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MesViewHolder>{

    List<Message> list;
    Context d;

    public static class MesViewHolder extends RecyclerView.ViewHolder  {

        public TextView message,time;
        public CardView cardView;
        public RelativeLayout relativeLayout,relativeLayout2;

        public MesViewHolder(View v){
            super(v);
            message=(TextView)v.findViewById(R.id.message_textview);
            time=(TextView)v.findViewById(R.id.message_textviewtime);
            relativeLayout=(RelativeLayout)v.findViewById(R.id.message_rellay);
            cardView = (CardView)v.findViewById(R.id.message_cardview);
            relativeLayout2=(RelativeLayout)v.findViewById(R.id.message_rellay2);
        }

    }

    public MessageAdapter(Context d,List<Message> list){
        this.d=d;
        this.list = list;
    }

    public MessageAdapter.MesViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.message_cardview,parent,false);
        MesViewHolder vh = new MesViewHolder(v);
        vh.setIsRecyclable(false);
        return vh;
    }

    @SuppressLint("ResourceAsColor")
    public void onBindViewHolder(MesViewHolder holder, int position){
        if (list.get(position).getSender().equals(user.getName())){
            //holder.message.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            holder.relativeLayout2.setGravity(5);
            holder.relativeLayout.setBackgroundResource(R.drawable.message_background);
        }

        holder.message.setText(list.get(position).getMes());
        holder.time.setText("  "+list.get(position).getHour()+":"+list.get(position).getMin());
        //Animation animation= AnimationUtils.loadAnimation(d,android.R.anim.slide_in_left);
        //holder.itemView.startAnimation(animation);

    }

    public int getItemCount(){
        return list.size();}

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}