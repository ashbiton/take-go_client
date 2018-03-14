package com.example.aviya.takeandgo2.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.aviya.takeandgo2.R;
import com.example.aviya.takeandgo2.model.datasource.List_DB_manager;
import com.example.aviya.takeandgo2.model.datasource.getManager;
import com.example.aviya.takeandgo2.model.entities.Client;
import com.example.aviya.takeandgo2.model.entities.UserClient;

/**
 * activity for adding client and userClient information
 */
public class AddActivity extends Activity implements View.OnClickListener {

    private LinearLayout clientInfoLayout;
    private EditText editTextClientID;
    private EditText editTextClientName;
    private EditText editTextClientLastName;
    private EditText editTextClientPhone;
    private EditText editTextClientEmail;
    private EditText editTextClientCreditNumber;
    private Button nextButton;
    private LinearLayout finalAddLayout;
    private EditText clientUserNameText;
    private EditText clientPasswordText;
    private Button registerButton;

    private Client client;
    private UserClient userClient;

    private List_DB_manager manager;

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2017-12-31 23:00:20 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        clientInfoLayout = (LinearLayout)findViewById( R.id.clientInfoLayout );
        editTextClientID = (EditText)findViewById( R.id.editTextClientID );
        editTextClientName = (EditText)findViewById( R.id.editTextClientName );
        editTextClientLastName = (EditText)findViewById( R.id.editTextClientLastName );
        editTextClientPhone = (EditText)findViewById( R.id.editTextClientPhone );
        editTextClientEmail = (EditText)findViewById( R.id.editTextClientEmail );
        editTextClientCreditNumber = (EditText)findViewById( R.id.editTextClientCreditNumber );
        nextButton = (Button)findViewById( R.id.nextButton );
        finalAddLayout = (LinearLayout)findViewById( R.id.finalAddLayout );
        clientUserNameText = (EditText)findViewById( R.id.clientUserNameText );
        clientPasswordText = (EditText)findViewById( R.id.clientPasswordText );
        registerButton = (Button)findViewById( R.id.registerButton );

        nextButton.setOnClickListener( this );
        registerButton.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2017-12-31 23:00:20 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == nextButton ) {
            collectClientData();
        } else if ( v == registerButton ) {
            register();
        }
    }

//original implementation of function register, no asyncTask
 /*   private void register() {
        String userName = clientUserNameText.getText().toString();
        String password = clientPasswordText.getText().toString();
        if(manager.existUserName(userName)){
            Toast.makeText(getBaseContext(), "this user name already exists in the system",Toast.LENGTH_LONG).show();
            return;
        }

        userClient.setPassword(password);
        userClient.setUserName(userName);

        try{manager.addUserClient(userClient);}
        catch (Exception exception){exception.printStackTrace(); return;}

        try {
            manager.addClient(client);
            Toast.makeText(this,"Registration successful",Toast.LENGTH_SHORT).show();
            //add alert dialog??
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
        }
        catch (Exception exception){exception.printStackTrace();}

    }*/

    /**
     * validates the user client info and add all Client info to the database
     */
    private void register() {
        String userName = clientUserNameText.getText().toString();
        String password = clientPasswordText.getText().toString();
        if(manager.existUserName(userName)){
            Toast.makeText(getBaseContext(), "this user name already exists in the system",Toast.LENGTH_LONG).show();
            return;
        }

        userClient.setPassword(password);
        userClient.setUserName(userName);


        try {
            new AsyncTask<Void,Boolean,Boolean>(){
                @Override
                protected void onPostExecute(Boolean aBoolean) {
                    if(aBoolean){
                        Toast.makeText(getBaseContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), MenuActivity.class);
                        intent.putExtra("ID",client.get_id()); // so the menu activity knows what client it is
                        startActivity(intent);
                    }
                }

                //return true if successfully added
                @Override
                protected Boolean doInBackground(Void... params) {
                    try{
                        manager.addUserClient(userClient);
                        manager.addClient(client);
                        return true;
                    }
                    catch (Exception e){
                        return false;
                    }
                }
            }.execute();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * collect all the info of the client (not userClient) and validates it
     * if all info is correctly written, moves on to fill all userClient info and register
     */
    private void collectClientData() {

        String id = editTextClientID.getText().toString();
        String name = editTextClientName.getText().toString();
        String lastName = editTextClientLastName.getText().toString();
        String phone = editTextClientPhone.getText().toString();
        String email = editTextClientEmail.getText().toString();
        String credit = editTextClientCreditNumber.getText().toString();

        //validating input
        if (id.length() == 0 || name.length() == 0||  lastName.length() == 0 ||
                phone.length() == 0 || email.length() == 0 || credit.length() == 0) {
            Toast.makeText(getBaseContext(), "please fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }
        int _id =0;
        try {
            _id = Integer.parseInt(id);
        }catch(Exception e){
            Toast.makeText(getBaseContext(), "ID must contain only letters",Toast.LENGTH_SHORT).show();
        }
        if(manager.existClient(_id)){
            Toast.makeText(getBaseContext(), "this client already exists in the system",Toast.LENGTH_LONG).show();
            return;
        }
        if(manager.existUserClient(_id)){
            Toast.makeText(getBaseContext(), "this client already exists in the system",Toast.LENGTH_LONG).show();
            return;
        }
        if(!manager.checkEmailAddress(email)){
            Toast.makeText(getBaseContext(), "email address is not valid",Toast.LENGTH_LONG).show();
            return;
        }
        if(!manager.checkId(id)){
            Toast.makeText(getBaseContext(), "identification number is not valid",Toast.LENGTH_LONG).show();
            return;
        }
        if(!manager.checkPhone(phone)){
            Toast.makeText(getBaseContext(), "cellphone number is not valid",Toast.LENGTH_LONG).show();
            return;
        }

        client.set_id(_id);
        client.setName(name);
        client.setLastName(lastName);
        client.setCreditCardNumber(credit);
        client.setEmailAddress(email);
        client.setPhone(phone);

        userClient.set_id(_id);

        Toast.makeText(this,"Client information was successfully received",Toast.LENGTH_SHORT).show();

        clientInfoLayout.setVisibility(View.GONE);
        finalAddLayout.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Take & Go");
        client = new Client();
        userClient = new UserClient();
        manager = getManager.getInstance();
        findViews();
    }
}
