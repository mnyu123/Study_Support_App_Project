package com.example.myapplication;

import android.os.CountDownTimer;

public class CheckSup {

    private double prlo;
    private double prla;


    public void setprlo(double prlo){
        this.prlo = prlo;
    }
    public void setprla(double prla){
        this.prla = prla;
    }

    public double getprlo(){
        return prlo;
    }
    public double getprla(){
        return prla;
    }




    class TimerRest extends CountDownTimer {

        public TimerRest(long millisInFuture, long countDownInterval){
            super(millisInFuture,countDownInterval);
        }
        public void onTick(long millisInFuture){

        }
        public void onFinish(){



        }
    }


    public void startth()
    {


        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });


    }

}






