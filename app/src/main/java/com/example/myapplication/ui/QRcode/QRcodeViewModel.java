package com.example.myapplication.ui.QRcode;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRcodeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;


    public QRcodeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is QRcode fragment");
    }

    public void getQRcode() {

    }
}