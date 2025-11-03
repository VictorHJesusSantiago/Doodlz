package com.example.doodlz;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.print.PrintHelper;

import java.io.OutputStream;

public class DoodleFragment extends Fragment implements SensorEventListenerHelper.ShakeListener {
    private DoodleView doodleView;
    private boolean dialogOnScreen = false;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        SensorEventListenerHelper.getInstance(requireContext()).setShakeListener(this);

        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    Boolean writeGranted = result.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false);
                    Boolean readGranted = result.getOrDefault(Manifest.permission.READ_EXTERNAL_STORAGE, false);
                    if (!writeGranted || !readGranted) {
                        Toast.makeText(requireContext(), "Permissão negada para salvar imagens", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doodle, container, false);
        doodleView = view.findViewById(R.id.doodleView);

        view.findViewById(R.id.button_color).setOnClickListener(v -> openColorDialog());
        view.findViewById(R.id.button_width).setOnClickListener(v -> openLineWidthDialog());
        view.findViewById(R.id.button_clear).setOnClickListener(v -> openEraseDialog());
        view.findViewById(R.id.button_save).setOnClickListener(v -> saveImage());
        view.findViewById(R.id.button_print).setOnClickListener(v -> printImage());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SensorEventListenerHelper.getInstance(requireContext()).start();
    }

    @Override
    public void onPause() {
        super.onPause();
        SensorEventListenerHelper.getInstance(requireContext()).stop();
    }

    private void openColorDialog() {
        dialogOnScreen = true;
        ColorDialogFragment dialog = new ColorDialogFragment();
        dialog.setCancelable(false);
        dialog.show(getChildFragmentManager(), "ColorDialogFragment");
    }

    private void openLineWidthDialog() {
        dialogOnScreen = true;
        LineWidthDialogFragment dialog = new LineWidthDialogFragment();
        dialog.setCancelable(false);
        dialog.show(getChildFragmentManager(), "LineWidthDialogFragment");
    }

    private void openEraseDialog() {
        dialogOnScreen = true;
        EraseImageDialogFragment dialog = new EraseImageDialogFragment();
        dialog.setCancelable(false);
        dialog.show(getChildFragmentManager(), "EraseImageDialogFragment");
    }

    private void saveImage() {
        Bitmap bitmap = doodleView.getBitmap();
        String filename = "Doodlz_" + System.currentTimeMillis() + ".png";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Doodlz");

        try {
            Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            Uri uri = requireContext().getContentResolver().insert(collection, values);
            if (uri == null) throw new Exception("Falha ao criar registro no MediaStore");
            try (OutputStream out = requireContext().getContentResolver().openOutputStream(uri)) {
                if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) throw new Exception("Falha ao comprimir bitmap");
            }
            showToast(R.string.save_success);
        } catch (Exception e) {
            Log.e("DoodleFragment", "Erro ao salvar imagem", e);
            showToast(R.string.save_error);
        }
    }

    private void printImage() {
        try {
            PrintHelper printHelper = new PrintHelper(requireContext());
            printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);
            Bitmap bitmap = doodleView.getBitmap();
            printHelper.printBitmap("Doodlz_Print", bitmap);
        } catch (Exception e) {
            Log.e("DoodleFragment", "Erro ao imprimir doodle", e);
            showToast(R.string.print_error);
        }
    }

    private void showToast(int resId) {
        new Handler(requireActivity().getMainLooper()).post(() ->
                Toast.makeText(requireContext(), resId, Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public void onShake() {
        if (!dialogOnScreen) {
            doodleView.clear();
        }
    }

    public void changeColor(int color) {
        doodleView.setColor(color);
        dialogOnScreen = false;
    }

    public void changeLineWidth(float width) {
        doodleView.setStrokeWidth(width);
        dialogOnScreen = false;
    }

    public void eraseDrawing() {
        doodleView.clear();
        dialogOnScreen = false;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}