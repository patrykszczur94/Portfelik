package com.example.patryk.portfelik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText etName = (EditText) findViewById(R.id.register_name_ed) ;
        final EditText etUsername = (EditText) findViewById(R.id.register_login_et) ;
        final EditText etPassword = (EditText) findViewById(R.id.passwor_register_et) ;
        final EditText etEmail = (EditText) findViewById(R.id.email_register_et) ;
        final Button bRegister = (Button) findViewById(R.id.register_bt) ;

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = etName.getText().toString();
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();
                final String username = etUsername.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success") ;

                            if(success){
                                Intent intent = new Intent(Register.this , Login.class) ;
                                Register.this.startActivity(intent) ;

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this) ;
                                builder.setMessage("Register filed ")
                                        .setNegativeButton("Retry" , null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(name ,email , password ,  username  , responseListener );
                RequestQueue queue = Volley.newRequestQueue(Register.this) ;
                queue.add(registerRequest) ;
            }
        });
    }

}