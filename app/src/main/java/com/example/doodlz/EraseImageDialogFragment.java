package com.example.doodlz;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EraseImageDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_erase_image, container, false);
        Button yesButton = view.findViewById(R.id.buttonYes);
        Button noButton  = view.findViewById(R.id.buttonNo);

        yesButton.setOnClickListener(v -> {
            ((DoodleFragment) getParentFragment()).eraseDrawing();
            dismiss();
        });
        noButton.setOnClickListener(v -> dismiss());

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Confirmar");
        return dialog;
    }
}
