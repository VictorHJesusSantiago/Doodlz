package com.example.doodlz;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ColorViewHolder extends RecyclerView.ViewHolder {
    private final View colorView;

    public ColorViewHolder(@NonNull View itemView) {
        super(itemView);
        colorView = itemView.findViewById(R.id.colorView);
    }

    public void bind(int color, ColorDialogFragment.ColorClickListener listener) {
        colorView.setBackgroundColor(color);
        colorView.setOnClickListener(v -> listener.onColorClick(color));
    }
}