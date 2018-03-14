package com.example.aviya.takeandgo2.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.aviya.takeandgo2.R;
import com.example.aviya.takeandgo2.model.datasource.List_DB_manager;
import com.example.aviya.takeandgo2.model.datasource.getManager;
import com.example.aviya.takeandgo2.model.entities.Branch;
import com.example.aviya.takeandgo2.model.entities.Car;
import com.example.aviya.takeandgo2.model.entities.CarModel;

/**
 * Created by aviya on 10/01/2018.
 */

/* a help class for the dialog that shows full details about a car,
including its branch full details and model full details*/
public class MyCarDetailsView {

    public static View getView(Context context, final Car car){

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        final View view = layoutInflater.inflate(R.layout.technical_details_layout,null);
        final List_DB_manager manager = getManager.getInstance();

        TextView carNumber = (TextView)view.findViewById(R.id.carNumber);
        TextView carKilometers = (TextView)view.findViewById(R.id.carKilometers);
        TextView carHomeBranch = (TextView)view.findViewById(R.id.carHomeBranch);
        TextView carCompany = (TextView)view.findViewById(R.id.carCompany);
        TextView carModelName = (TextView)view.findViewById(R.id.carModelName);
        TextView carModelCode = (TextView)view.findViewById(R.id.carModelCode);
        TextView carModelDoors = (TextView)view.findViewById(R.id.carModelDoors);
        TextView carModelSeats = (TextView)view.findViewById(R.id.carModelSeats);
        TextView carModelGear = (TextView)view.findViewById(R.id.carModelGear);
        TextView carModelAcc = (TextView)view.findViewById(R.id.carModelAcc);
        TextView carModelMaxSpeed = (TextView)view.findViewById(R.id.carModelMaxSpeed);
        TextView carModelHorsePower = (TextView)view.findViewById(R.id.carModelHorsePower);
        TextView carModelTrunk = (TextView)view.findViewById(R.id.carModelTrunk);
        TextView carModelKilosPerTank = (TextView)view.findViewById(R.id.carModelKilosPerTank);
        CheckBox carModelCodeRequired = (CheckBox)view.findViewById(R.id.carModelCodeRequired);
        CheckBox carModelConvertible = (CheckBox)view.findViewById(R.id.carModelConvertible);
        CheckBox carModelTurbo = (CheckBox)view.findViewById(R.id.carModelTurbo);
        ImageView carBranchFullDetails = (ImageView)view.findViewById(R.id.carBranchFullDetails);
        final LinearLayout branchFullDetailsLayout = (LinearLayout)view.findViewById(R.id.branchFullDetailsLayout);

        //cannot see full branch details
        branchFullDetailsLayout.setVisibility(View.GONE);

        //open and close the full branch data layout
        carBranchFullDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(branchFullDetailsLayout.getVisibility() == View.VISIBLE)
                    branchFullDetailsLayout.setVisibility(View.GONE);
                else {
                    TextView carBranchParking = (TextView) view.findViewById(R.id.carBranchParking);
                    //in the original xml file we set the value to -5 to indicates if the layout details have already updated
                    if (!(carBranchParking.getText().toString().equals("-5")))//its already updated
                        branchFullDetailsLayout.setVisibility(View.VISIBLE);
                    else {
                        Branch branch = manager.findBranch(car.getHomeBranch());
                        TextView carBranchHouse = (TextView) view.findViewById(R.id.carBranchHouse);
                        TextView carBranchCity = (TextView) view.findViewById(R.id.carBranchCity);
                        TextView carBranchStreet = (TextView) view.findViewById(R.id.carBranchStreet);
                        carBranchCity.setText(manager.toProperFormat(branch.getCity()));
                        carBranchHouse.setText(String.valueOf(branch.getHouseNumber()));
                        carBranchStreet.setText(manager.toProperFormat(branch.getStreet()));
                        carBranchParking.setText(String.valueOf(branch.getParkingSpaces()));
                        branchFullDetailsLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        CarModel carModel = manager.findCarModel(car.getModelCode());

        carNumber.setText(car.getCarNumber());
        carKilometers.setText(String.valueOf(car.getKilometers()));
        carHomeBranch.setText(String.valueOf(car.getHomeBranch()));
        carCompany.setText(manager.toProperFormat(carModel.getCarCompany()));
        carModelName.setText(manager.toProperFormat(carModel.getModelName()));
        carModelCode.setText(carModel.getModelCode());
        carModelDoors.setText(String.valueOf(carModel.getDoors()));
        carModelSeats.setText(String.valueOf(carModel.getSeats()));
        carModelGear.setText(carModel.getGearBox().toString());
        carModelAcc.setText(String.valueOf(carModel.getAcceleration()));
        carModelMaxSpeed.setText(String.valueOf(carModel.getMaxSpeed()));
        carModelHorsePower.setText(String.valueOf(carModel.getHorsePower()));
        carModelTrunk.setText(String.valueOf(carModel.getTrunkVolume()));
        carModelKilosPerTank.setText(String.valueOf(carModel.getKilometersPerTank()));
        carModelCodeRequired.setChecked(carModel.isCodeRequired());
        carModelConvertible.setChecked(carModel.isConvertible());
        carModelTurbo.setChecked(carModel.isBuildInTurbo());

        return view;
    }
}
