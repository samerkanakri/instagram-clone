package com.samerkanakri.instaclone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {

    private EditText edtUsername, edtPassword;
    private ImageView imgLogo;
    private RelativeLayout relativeLayout;

    private String strUsername, strPassword;

    private static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout_id);

        edtPassword.setOnKeyListener(this);
        imgLogo.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);

    }

    /**
     * Feature - submit using enter key
     * @param view
     * @param i
     * @param keyEvent
     * @return
     */
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            signUp(view);
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.relativelayout_id || view.getId() == R.id.imgLogo){
            // Feature - hide soft keyboard on touching the background
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void signUp(View view) {

        strUsername = edtUsername.getText().toString();
        strPassword = edtPassword.getText().toString();

        if (strUsername.matches("") || strPassword.matches("")) {
            Toast.makeText(this, "username or password can not be empty", Toast.LENGTH_SHORT).show();
        } else {
            ParseUser user = new ParseUser();

            user.setUsername(strUsername);
            user.setPassword(strPassword);

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(SignUp.this, "signed up", Toast.LENGTH_SHORT).show();
                        clear();
                        Intent i = new Intent(SignUp.this, Feed.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.i("sign up", "failed");
                    }
                }
            });
        }
    }

    public void loginPage(View view) {
        clear();
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        finish();
    }

    private void clear() {
        edtUsername.setText("");
        edtPassword.setText("");
    }

    private boolean ValidEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

}
