package com.example.doodlz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LineWidthDialogFragment extends DialogFragment {
    private float maxWidth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_line_width, container, false);
        SeekBar seekBar = view.findViewById(R.id.widthSeekBar);
        TextView textView = view.findViewById(R.id.textViewWidth);

        maxWidth = getResources().getDimension(R.dimen.max_line_width);
        seekBar.setMax((int) maxWidth);
        seekBar.setProgress((int) getResources().getDimension(R.dimen.default_line_width));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float width = progress;
                if (width < getResources().getDimension(R.dimen.default_line_width)) {
                    width = getResources().getDimension(R.dimen.default_line_width);
                }
                textView.setText(getString(R.string.width) + " " + (int) width);
                ((DoodleFragment) getParentFragment()).changeLineWidth(width);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                dismiss();
            }
        });
        return view;
    }
}
