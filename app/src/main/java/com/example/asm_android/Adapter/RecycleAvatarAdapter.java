package com.example.asm_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm_android.Fragment.FragmentGioiThieu;
import com.example.asm_android.QuanLyThuChi;
import com.example.asm_android.R;

import java.util.ArrayList;

public class RecycleAvatarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    private ArrayList<Integer> integers;
    public RecycleAvatarAdapter(Context context,ArrayList<Integer> integers){
        this.context=context;
        this.integers=integers;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView avatar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar=(ImageView)itemView.findViewById(R.id.avatar);
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.avatar,parent,false);
        return new RecycleAvatarAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Integer integer=integers.get(position);
        ((MyViewHolder)holder).avatar.setImageResource(integer);

    }


    @Override
    public int getItemCount() {
        return integers.size();
    }
}
