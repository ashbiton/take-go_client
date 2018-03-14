package com.example.aviya.takeandgo2.controller.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.aviya.takeandgo2.R;
import com.example.aviya.takeandgo2.controller.adapter.MyCarAdapter;
import com.example.aviya.takeandgo2.controller.adapter.MyCarDetailsView;
import com.example.aviya.takeandgo2.model.datasource.List_DB_manager;
import com.example.aviya.takeandgo2.model.datasource.getManager;
import com.example.aviya.takeandgo2.model.entities.Car;
import com.example.aviya.takeandgo2.model.entities.Reservation;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FreeCarsFragment extends Fragment implements View.OnClickListener {

    View view;
    List_DB_manager manager;
    private HorizontalInfiniteCycleViewPager horizontalCycleCars;
    private Button orderCarButton;
    private Button showCarDetailsButton;
    Reservation resv = new Reservation();

    OnCarOrderedListener activityListener;

    /**
     * inner interface to update te menu activity that the client has a new reservation
     */
    public interface OnCarOrderedListener{
        void newReservationForClient(Reservation reservation);
    }


    public FreeCarsFragment() {
        // Required empty public constructor
    }

    /**
     * function to check if the activity that activated the fragment has implemented the fragment interface
     * @param activity the activity that activated the fragment
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            activityListener = (OnCarOrderedListener)activity;
        }
        catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cars, container, false);
        findViews();
        return view;
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-04 22:32:27 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        manager = getManager.getInstance();
        horizontalCycleCars = (HorizontalInfiniteCycleViewPager)view.findViewById( R.id.horizontal_cycle_cars );
        MyCarAdapter adapter = new MyCarAdapter(manager.availableCars(),getContext());
        horizontalCycleCars.setAdapter(adapter);
        orderCarButton = (Button)view.findViewById( R.id.orderCarButton );
        showCarDetailsButton = (Button)view.findViewById(R.id.showCarDetailsButton);

        orderCarButton.setOnClickListener( this );
        showCarDetailsButton.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-01-04 22:32:27 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == orderCarButton ) {
            orderCar();
        }
        else if (v == showCarDetailsButton){
            showCarDetails();
        }

    }

    /**
     * activates a dialog to show full specific car details, including branch and model
     */
    private void showCarDetails() {
        List<Car> cars = manager.availableCars();
        Car orderedCar = cars.get(horizontalCycleCars.getRealItem()); // finds the car that is now in focus
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = MyCarDetailsView.getView(getContext(),orderedCar);
        builder.setView(view);
        builder.setNeutralButton("got it", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.setTitle("FULL CAR DETAILS");
        builder.show();
        //TODO custom title?
    }

    /**
     * function to order a cer that the client choose
     * activates the dialog before adding to check if the client is certain
     */
    private void orderCar() {
        List<Car> cars = manager.availableCars();
        Car orderedCar = cars.get(horizontalCycleCars.getRealItem());
        resv.setCarNumber(orderedCar.getCarNumber());
        resv.setKilometersStart(orderedCar.getKilometers());
        Bundle args = getArguments();
        int id = args.getInt("ID");
        if (id == 0) {
            Toast.makeText(getContext(), "no ID was found", Toast.LENGTH_SHORT);
            return;
        }
        resv.setClientNumber(id);
        dialogBeforeAdding(orderedCar.getCarNumber(),resv);
    }

//      ORIGINAL FUNCTION NO ASYNCTASK
//    private void dialogBeforeAdding(final String carNumber, final Reservation resv) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setIcon(R.drawable.ic_menu_info);
//        builder.setTitle("Are you sure?");
//        String message = "a new reservation for car number "+carNumber+"\n will be opened in your name";
//        builder.setMessage(message);
//        AlertDialog.OnClickListener onClickListener = new AlertDialog.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which){
//                    case AlertDialog.BUTTON_NEGATIVE:
//                        break;
//                    case AlertDialog.BUTTON_POSITIVE:
//                        try {
//                            int resvNumber = manager.addReservation(resv);
//                            activityListener.newReservationForClient(resv);
//                            dialogAfterAdding(carNumber, resvNumber);
//                        } catch (Exception e) {
//                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                        break;
//                }
//            }
//        };

    /**
     *  check if the client wants to open a new reservation if so opens it
     *  and activate the afterDialog to let the client knows the reservation number
     * @param carNumber the car the client may want to order, needs to be known
     * @param resv reservation that holds the info of the car
     */
    private void dialogBeforeAdding(final String carNumber, final Reservation resv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.drawable.ic_menu_info);
        builder.setTitle("Are you sure?");
        String message = "a new reservation for car number "+carNumber+"\n will be opened in your name";
        builder.setMessage(message);
        AlertDialog.OnClickListener onClickListener = new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case AlertDialog.BUTTON_NEGATIVE:
                        break;
                    case AlertDialog.BUTTON_POSITIVE:
                        try {
                            new AsyncTask<Void, Integer, Integer>() {
                                @Override
                                protected void onPostExecute(Integer integer) {
                                    if(integer != 0) {                                  // if managed to open reservation
                                        activityListener.newReservationForClient(resv); // inform menu activity about the change
                                        dialogAfterAdding(carNumber, integer);          // confirm reservation number to client
                                    }
                                }

                                @Override
                                protected Integer doInBackground(Void... params) {
                                    try {
                                        int resvNumber = manager.addReservation(resv);
                                        return resvNumber;
                                    }
                                    catch (final Exception e){
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                    return 0;
                                }
                            }.execute();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                }
            }
        };

        builder.setNegativeButton("No, Do not open",onClickListener);
        builder.setPositiveButton("Yes, Do open",onClickListener);
        builder.show();
    }

    /**
     * activates a dialog to let the client know reservation number and car number
     * of the reservation opened
     * @param carNumber of the car ordered
     * @param resvNumber of te newly opened reservation
     */
    private void dialogAfterAdding(String carNumber, int resvNumber) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setIcon(R.drawable.ic_action_check);
        builder.setTitle("Reservation Opened");
        String message = "reservation number "+resvNumber+" opened \n for car number "+carNumber+" in your name";
        builder.setMessage(message);
        AlertDialog.OnClickListener onClickListener = new AlertDialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        };
        builder.setNeutralButton("Got it",onClickListener);
        builder.show();

    }


}
