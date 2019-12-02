package com.gcs.testroomdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.gcs.testroomdb.databinding.ItemBinding;

import java.util.ArrayList;
import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private Context context;
    private List<User> users = new ArrayList<>();
    private Handlers handler;

    public RvAdapter(Context context, List<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item, parent, false);
        ViewHolder vh = new ViewHolder(itemBinding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = users.get(position);
        holder.itemBinding.setUser(user);
        handler = new Handlers(context, user.getId());
        holder.itemBinding.setItemHandler(handler);

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ItemBinding itemBinding;

        public ViewHolder(@NonNull ItemBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }
    }
}
