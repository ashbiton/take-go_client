package com.example.aviya.takeandgo2.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aviya.takeandgo2.R;
import com.example.aviya.takeandgo2.model.datasource.List_DB_manager;
import com.example.aviya.takeandgo2.model.datasource.getManager;

/**
 * class for login an existing user/client and saving login data
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText userNameTextView;
    private EditText passwordTextView;
    private TextView goToRegisterView;
    private Button loginButton;
    private CheckBox savePreferences;
    private List_DB_manager manager;

    private int idToPass;       // client id to pass to te menu activity so it "knows" which client is signed in
    private Boolean textChange; // a bool parameter to check if the login info changed and needs to be reevaluated


    private void findViews() {
        userNameTextView = (EditText) findViewById(R.id.userNameTextView);
        passwordTextView = (EditText) findViewById(R.id.passwordTextView);
        goToRegisterView = (TextView) findViewById(R.id.goToRegisterView);
        loginButton = (Button) findViewById(R.id.loginButton);
        savePreferences = (CheckBox) findViewById(R.id.savePreferencesCheckBox); 

        goToRegisterView.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        //if login info changed set the textChange value to true
        userNameTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChange = true;
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        passwordTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textChange = true;
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    @Override
    public void onClick(View v) {
        if ( v == loginButton ) {
            Login();
        }
        else if(v == goToRegisterView){
            Register();
        }
    }

    /**
     * moving to the registration activity if there is no existing user/client
     */
    private void Register() {
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }

    /**
     * validating the input for login info and
     * moving to menu activity if validated
     */
    private void Login() {
        if (textChange){
            if(validateInput()) {
                if (savePreferences.isChecked())
                    savePreferencesData();
                goToMenu();
            }
        }
        else {
            if(savePreferences.isChecked())
                savePreferencesData();
            goToMenu();
        }
    }

    /**
     * validate the input for login info according to the database
     * @return true if info exists correctly in the database
     */
    private boolean validateInput() {
        String message = null;
        String userName = userNameTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        if(!manager.existUserName(userName))
            message = "this user name does not exist in the system";
        if(manager.findClientByUser(userName) == null)
            message = "this user name is not associated with any client";
        else
            idToPass = manager.findClientByUser(userName).get_id();
        if(manager.userClientMatch(userName,password) == -1)
            message = "password does not match user name";
        if(message == null)
            return true;
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        return false;
    }


    private void goToMenu() {
        Intent intent = new Intent(this,MenuActivity.class);
        intent.putExtra("ID",idToPass);
        startActivity(intent);
    }

    /**
     * save the ogin info in the SharedPreferences for future use
     */
    private void savePreferencesData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();

        String userName = userNameTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        editor.putString("USER_NAME",userName);
        editor.putString("PASSWORD",password);
        if(idToPass == 0) { // no id means no client found for this userClient info
            Toast.makeText(this, "no ID for this user was found", Toast.LENGTH_LONG).show();
            return;
        }
        editor.putInt("ID",idToPass);
        editor.commit();
    }

    /**
     * check if there is an info in the SharedPreferences, if there is load it to the proper view
     * and to the id to pass.
     */
    private void addPreference() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.contains("USER_NAME")) {
            userNameTextView.setText(preferences.getString("USER_NAME", null));
            savePreferences.setChecked(true);
        }
        if(preferences.contains("PASSWORD"))
            passwordTextView.setText(preferences.getString("PASSWORD",null));
        if(preferences.contains("ID"))
            idToPass = preferences.getInt("ID",0);
        textChange = false; //set textChange false because so far there was no change in login info
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Take & Go");
        manager = getManager.getInstance();
        findViews();
        addPreference();
        loginButton.setVisibility(View.INVISIBLE);
        new AsyncTask<Void,Boolean,Boolean>(){
            @Override
            protected void onPostExecute(Boolean aBoolean) {
                if(aBoolean){
                    //login button is not visible so we dont press it before the list database is initialized
                    loginButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try{
                    //initializing all the lists from database must be done here because its the launcher activity
                    manager.initializeLists();
                    return true;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            }
        }.execute();
    }
}
