package com.example.aviya.takeandgo2.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aviya.takeandgo2.R;
import com.example.aviya.takeandgo2.model.entities.Car;

import java.util.List;

/**
 * Created by aviya on 08/01/2018.
 */

//simple adapter for the inner list view in the expandable branch list
public class MyListViewAdapter extends BaseAdapter {
    List<Car> carList;
    Context context;
    LayoutInflater mLayoutInflater;

    public MyListViewAdapter(List<Car> carList, Context context) {
        this.carList = carList;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public Object getItem(int position) {
        return carList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Car car = carList.get(position);
        View view = mLayoutInflater.inflate(R.layout.list_view_item,parent,false);
        TextView textView = (TextView)view.findViewById(R.id.listViewCarNumber);
        textView.setText(car.getCarNumber());
        return view;
    }

}
