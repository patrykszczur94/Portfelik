package com.example.patryk.portfelik;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NewExpenseLove extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_expense_love);

        final EditText etExpense = (EditText) findViewById(R.id.expenseLoveEt) ;
        final EditText etDescription = (EditText) findViewById(R.id.descriptionLove) ;
        final Button bConfirm = (Button) findViewById(R.id.confirmLove) ;

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");

        bConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String description = etDescription.getText().toString();
                final String expense = etExpense.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success") ;

                            if (success){
                                Intent intent = new Intent(NewExpenseLove.this, MainActivity.class);
                                intent.putExtra("password" , password );
                                intent.putExtra("username" , username ) ;
                                intent.putExtra("userID" , userID ) ;
                                NewExpenseLove.this.startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewExpenseLove.this);
                                builder.setMessage("Error")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } ;

                NewExpenseLoveRequest newExpenseTreastRequest =  new NewExpenseLoveRequest ( expense , description , userID ,  responseListener );
                RequestQueue treast = Volley.newRequestQueue(NewExpenseLove.this) ;
                treast.add(newExpenseTreastRequest) ;

            }
        });

    }
}
