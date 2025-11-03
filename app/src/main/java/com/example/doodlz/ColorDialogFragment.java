package com.example.doodlz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class ColorDialogFragment extends DialogFragment {

    private static final List<Integer> COLORS = Arrays.asList(
            Color.BLACK, Color.RED, Color.BLUE, Color.GREEN,
            Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.GRAY,
            Color.DKGRAY, Color.LTGRAY
    );

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.colorRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));

        ColorAdapter adapter = new ColorAdapter(COLORS, color -> {
            ((DoodleFragment) getParentFragment()).changeColor(color);
            dismiss();
        });
        recyclerView.setAdapter(adapter);

        return view;
    }

    public interface ColorClickListener {
        void onColorClick(int color);
    }

    private static class ColorAdapter extends RecyclerView.Adapter<ColorViewHolder> {
        private final List<Integer> colors;
        private final ColorClickListener listener;

        ColorAdapter(List<Integer> colors, ColorClickListener listener) {
            this.colors = colors;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_color, parent, false);
            return new ColorViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
            int color = colors.get(position);
            holder.bind(color, listener);
        }

        @Override
        public int getItemCount() {
            return colors.size();
        }
    }
}
