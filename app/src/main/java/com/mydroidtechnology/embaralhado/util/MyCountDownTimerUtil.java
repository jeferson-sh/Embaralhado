package com.mydroidtechnology.embaralhado.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

/*
 * Created by Jeferson on 26/07/2017.
 */

public class MyCountDownTimerUtil extends CountDownTimer {

    private boolean start;
    private Context context;

    public MyCountDownTimerUtil(Context context, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.start = false;
        this.context = context;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        this.start = true;
        Toast.makeText(context, "Pressione novamente para sair!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinish() {
        this.start = false;
    }

    public boolean isStart() {
        return start;
    }
}
