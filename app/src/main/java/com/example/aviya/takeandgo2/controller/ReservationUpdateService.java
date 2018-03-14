package com.example.aviya.takeandgo2.controller;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.aviya.takeandgo2.model.datasource.List_DB_manager;
import com.example.aviya.takeandgo2.model.datasource.getManager;

/**
 * Created by aviya on 13/01/2018.
 */

public class ReservationUpdateService extends IntentService {



    public ReservationUpdateService( ) {
        super("aviya");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while (true){
            try{
                Thread.sleep(10000);
                List_DB_manager manager = getManager.getInstance();
                Intent in = new Intent();
                in.setAction("CAR_CHANGE");
                if (manager.reservClosedTenSecnds())
                {
                    in.putExtra("CHANGE","change");
                    sendBroadcast(in);
                }
//                else
//                {
//                    sendBroadcast(in);
//                }
            }
            catch (Exception e){
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }
}
