package com.example.foodbank2021;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Model> mList;
    private Context context;

    public MyAdapter(Context context, ArrayList<Model> mList){
        this.mList=mList;
        this.context=context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_profile,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(holder.itemView).load(mList.get(position).getProfile()).into(holder.iv_profile);
        holder.email.setText(mList.get(position).getEmail());
        holder.firstName.setText(mList.get(position).getFirstName());
        holder.verified.setText(mList.get(position).getVerified());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_profile;
        TextView email, firstName, verified;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.iv_profile=itemView.findViewById(R.id.iv_profile);
            this.email=itemView.findViewById(R.id.tv_email);
            this.firstName=itemView.findViewById(R.id.tv_name);
            this.verified=itemView.findViewById(R.id.tv_verified);
        }
    }
}
