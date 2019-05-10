package com.samerkanakri.instaclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    private EditText edtUsername, edtPassword;
    private ImageView imgLogo;
    private RelativeLayout relativeLayout;

    private String strUsername, strPassword;

    Button btnClear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_id);
        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                edtUsername.setText("");
            }
        });

        edtPassword.setOnKeyListener(this);
        imgLogo.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
        relativeLayout.setSoundEffectsEnabled(false);
    }

    /**
     * submit using enter key
     * @param view
     * @param i
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            login(view);
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.relativelayout_id || view.getId() == R.id.imgLogo){
            // hide soft keyboard on touching the background
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void login(View view){
        strUsername = edtUsername.getText().toString();
        strPassword = edtPassword.getText().toString();

        if(strUsername.matches("") || strPassword.matches("")){
            Toast.makeText(this, "username or password can not be empty", Toast.LENGTH_SHORT).show();
        }else{
            ParseUser user = new ParseUser();

            user.setUsername(strUsername);
            user.setPassword(strPassword);

            user.logInInBackground(strUsername,strPassword, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(e == null){
                        Toast.makeText(Login.this, "logged in", Toast.LENGTH_SHORT).show();
                        clear();
                        Intent i = new Intent(Login.this, Feed.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.i("sign up", "failed");
                    }
                }
            });
        }
    }

    public void signUpPage(View view){
        clear();
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
        finish();
    }

    private void clear(){
        edtUsername.setText("");
        edtPassword.setText("");
    }

}
