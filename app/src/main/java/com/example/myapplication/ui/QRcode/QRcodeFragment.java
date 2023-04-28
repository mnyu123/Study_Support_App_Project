package com.example.myapplication.ui.QRcode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.databinding.FragmentQrcodeBinding;

public class QRcodeFragment extends Fragment {

    private FragmentQrcodeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        QRcodeViewModel qrcodeViewModel =
                new ViewModelProvider(this).get(QRcodeViewModel.class);

        binding = FragmentQrcodeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textQrcode;
        qrcodeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}