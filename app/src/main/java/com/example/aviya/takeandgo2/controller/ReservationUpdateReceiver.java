package com.example.aviya.takeandgo2.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by aviya on 14/01/2018.
 */

public class ReservationUpdateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("CHANGE"))
            Toast.makeText(context,"car list updated",Toast.LENGTH_SHORT).show();
//        else
//            Toast.makeText(context,"car list have not changed",Toast.LENGTH_SHORT).show();
    }
}
