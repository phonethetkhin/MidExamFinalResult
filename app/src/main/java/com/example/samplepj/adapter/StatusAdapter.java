package com.example.samplepj.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.samplepj.R;
import com.example.samplepj.SamplePJDatabase;
import com.example.samplepj.model.StatusModel;
import com.example.samplepj.ui.EditActivity;

import java.util.List;

public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder> {
    private Context context;
    private SamplePJDatabase db;
    private List<StatusModel> statusModelList;

    public StatusAdapter(Context context, SamplePJDatabase db, List<StatusModel> statusModelList) {
        this.context = context;
        this.db = db;
        this.statusModelList = statusModelList;
    }

    @NonNull
    @Override
    public StatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_status, parent, false);
        return new StatusViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusViewHolder holder, int position) {
        holder.tvUserName.setText(statusModelList.get(position).getUserName());
        holder.tvStatus.setText(statusModelList.get(position).getStatus());

        holder.ivDelete.setOnClickListener(view -> {
            db.deleteStatus(statusModelList.get(position).getStatusId());
            statusModelList.remove(position);
            notifyDataSetChanged();
        });

        holder.ivEdit.setOnClickListener(view -> {
            Intent i = new Intent(context, EditActivity.class);
            i.putExtra("statusId", statusModelList.get(position).getStatusId());
            i.putExtra("status", statusModelList.get(position).getStatus());
            context.startActivity(i);

        });

    }

    @Override
    public int getItemCount() {
        return statusModelList.size();
    }

    public class StatusViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvStatus;
        ImageView ivEdit, ivDelete;

        public StatusViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);


        }
    }
}
