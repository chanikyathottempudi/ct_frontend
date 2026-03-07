package com.simats.finalapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScanAdapter extends RecyclerView.Adapter<ScanAdapter.ScanViewHolder> {

    private Context context;
    private List<String> scanUris;
    private OnScanDeleteListener deleteListener;

    public interface OnScanDeleteListener {
        void onDelete(String uri, int position);
    }

    public ScanAdapter(Context context, List<String> scanUris, OnScanDeleteListener deleteListener) {
        this.context = context;
        this.scanUris = scanUris;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public ScanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_scan, parent, false);
        return new ScanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScanViewHolder holder, int position) {
        String uriString = scanUris.get(position);
        if (uriString.startsWith("res/")) {
             // Handle resource images if any
             int resId = context.getResources().getIdentifier(uriString.substring(4), "drawable", context.getPackageName());
             holder.scanImage.setImageResource(resId);
        } else {
             holder.scanImage.setImageURI(Uri.parse(uriString));
        }

        holder.deleteButton.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(uriString, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return scanUris.size();
    }

    public static class ScanViewHolder extends RecyclerView.ViewHolder {
        ImageView scanImage;
        ImageView deleteButton;

        public ScanViewHolder(@NonNull View itemView) {
            super(itemView);
            scanImage = itemView.findViewById(R.id.scan_image);
            deleteButton = itemView.findViewById(R.id.delete_scan_button);
        }
    }
}
