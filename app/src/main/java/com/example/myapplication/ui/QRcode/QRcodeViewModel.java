package com.example.myapplication.ui.QRcode;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QRcodeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;


    public QRcodeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is QRcode fragment");
    }

    public void getQRcode() {

    }
}