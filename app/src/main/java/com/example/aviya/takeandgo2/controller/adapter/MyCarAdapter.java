package com.example.aviya.takeandgo2.controller.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.aviya.takeandgo2.R;
import com.example.aviya.takeandgo2.model.datasource.List_DB_manager;
import com.example.aviya.takeandgo2.model.datasource.getManager;
import com.example.aviya.takeandgo2.model.entities.Branch;
import com.example.aviya.takeandgo2.model.entities.Car;
import com.example.aviya.takeandgo2.model.entities.CarModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aviya on 04/01/2018.
 */

//adapter for cover flow
public class MyCarAdapter extends PagerAdapter {

    List<Car> carList = new ArrayList<>();
    LayoutInflater mLayoutInflater;
    Context context;

    public MyCarAdapter(List<Car> carList, Context context) {
        this.carList = carList;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);


    }

    @Override
    public int getCount() {
        return carList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        List_DB_manager manager = getManager.getInstance();
        //find all the views
        View view = mLayoutInflater.inflate(R.layout.car_layout,container,false);
        TextView carPresentCarNumber = (TextView)view.findViewById( R.id.car_present_car_number );
        TextView carPresentCarCompany = (TextView)view.findViewById( R.id.car_present_car_company );
        TextView carPresentCarModel = (TextView)view.findViewById( R.id.car_present_car_model );
        TextView carPresentCarKilometers = (TextView)view.findViewById( R.id.car_present_car_kilometers );
        TextView carPresentCarHouse = (TextView)view.findViewById( R.id.car_present_car_house );
        TextView carPresentCarStreet = (TextView)view.findViewById( R.id.car_present_car_street );
        TextView carPresentCarCity = (TextView)view.findViewById( R.id.car_present_car_city );
        TextView carPresentGear = (TextView)view.findViewById( R.id.car_present_gear );
        TextView carPresentEngineCapacity = (TextView)view.findViewById( R.id.car_present_engine_capacity );
        TextView carPresentTrunkVolume = (TextView)view.findViewById( R.id.car_present_trunk_volume );
        TextView carPresentSeats = (TextView)view.findViewById( R.id.car_present_seats );
        CheckBox carPresentConvertible = (CheckBox)view.findViewById( R.id.car_present_convertible );
        //assign all values according to car values
        Car car = carList.get(position);
        carPresentCarNumber.setText(car.getCarNumber());
        carPresentCarKilometers.setText(String.valueOf(car.getKilometers()));
        //assign all values according to branch values
        Branch branch = manager.findBranch(car.getHomeBranch());
        carPresentCarCity.setText(manager.toProperFormat(branch.getCity()));
        carPresentCarHouse.setText(String.valueOf(branch.getHouseNumber()));
        carPresentCarStreet.setText(manager.toProperFormat(branch.getStreet()));
        //assign all values according to model values
        CarModel model = manager.findCarModel(car.getModelCode());
        carPresentCarCompany.setText(manager.toProperFormat(model.getCarCompany()));
        carPresentCarModel.setText(manager.toProperFormat(model.getModelName()));
        carPresentConvertible.setChecked(model.isConvertible());
        carPresentEngineCapacity.setText(String.valueOf(model.getEngineCapacity()));
        carPresentTrunkVolume.setText(String.valueOf(model.getTrunkVolume()));
        carPresentSeats.setText(String.valueOf(model.getSeats()));
        carPresentGear.setText(model.getGearBox().toString()+" gear box");
        container.addView(view);
        return view;
    }


}
