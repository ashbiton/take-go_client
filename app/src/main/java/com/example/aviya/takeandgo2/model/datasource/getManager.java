package com.example.aviya.takeandgo2.model.datasource;

/**
 * Created by aviya on 01/01/2018.
 */

public class getManager {
    private static List_DB_manager instance;
    public static List_DB_manager getInstance(){
        if (instance == null)
            instance = new List_DB_manager();
        return instance;
    }
}
