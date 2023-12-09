package com.example.myapplication.ui.slideshow;

import android.os.CountDownTimer;
import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.myapplication.R;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    private static double prlo;
    private static double prla;

    private static int arlam_val;


    public void setprlo(double prlo){
        this.prlo = prlo;
    }
    public void setprla(double prla){
        this.prla = prla;
    }

    public void setaval(int val){this.arlam_val = val;}

    public double getprlo(){
        return prlo;
    }
    public double getprla(){
        return prla;
    }

    public int getval(){return arlam_val;}


    public void Timersta(){
        TimerRest timer = new TimerRest(300000,1000);
        timer.start();
    }


    class TimerRest extends CountDownTimer {

        public TimerRest(long millisInFuture, long countDownInterval){
            super(millisInFuture,countDownInterval);
        }
        public void onTick(long millisInFuture){
            SlideshowFragment.button1.setEnabled(true);
        }
        public void onFinish(){

        SlideshowFragment.button1.setEnabled(false);


        }
    }


    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Calendar Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }








}