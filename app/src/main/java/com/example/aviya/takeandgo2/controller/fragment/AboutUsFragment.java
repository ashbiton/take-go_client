package com.example.aviya.takeandgo2.controller.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.aviya.takeandgo2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment implements View.OnClickListener {

    private Button aboutDailButton;
    private Button aboutMailButton;
    private Button aboutWebsiteButton;
    private Button aboutLocationButton;
    private View view;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        findViews();
        return view;
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-01-03 22:56:44 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        aboutDailButton = (Button)view.findViewById( R.id.aboutDailButton );
        aboutMailButton = (Button)view.findViewById( R.id.aboutMailButton );
        aboutWebsiteButton = (Button)view.findViewById( R.id.aboutWebsiteButton );
        aboutLocationButton = (Button)view.findViewById( R.id.aboutLocationButton );

        aboutDailButton.setOnClickListener( this );
        aboutMailButton.setOnClickListener( this );
        aboutWebsiteButton.setOnClickListener( this );
        aboutLocationButton.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-01-03 22:56:44 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == aboutDailButton ) {
            dialCompany();
        } else if ( v == aboutMailButton ) {
            mailCompany();
        } else if ( v == aboutWebsiteButton ) {
            goToCompanyWebsite();
        } else if ( v == aboutLocationButton ) {
            showGoogleMapsLocation();
        }
    }


    //implicit intent to open google maps app for the company headquarters
    private void showGoogleMapsLocation() {
        String address = "25 Bracha Kedumim Israel";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri location = Uri.parse("geo:0,0?q=" + address.replace(" ","+").replace("\0","+"));
        intent.setData(location);
        startActivity(intent);

    }

    //implicit intent to open the company website in web browser
    private void goToCompanyWebsite() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://moodle.jct.ac.il/"));
        startActivity(intent);

    }

    //implicit intent to open email app to send mail to the company
    private void mailCompany() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"service@takengo.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT,"Customer Service");
        startActivity(Intent.createChooser(intent,"Select an app:"));
    }

    //implicit intent to open phone app to call the company number
    private void dialCompany() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:0502293998"));
        startActivity(intent);
    }




}
