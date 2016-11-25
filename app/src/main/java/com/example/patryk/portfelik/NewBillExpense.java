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

import static com.example.patryk.portfelik.R.id.confirmBillBt;

public class NewBillExpense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bill_expense);

        final EditText etExpense = (EditText) findViewById(R.id.expenseBillEt) ;
        final EditText etDescription = (EditText) findViewById(R.id.descriptionBillEt) ;
        final Button billconfirm = (Button) findViewById(R.id.confirmBillBt) ;

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");

        billconfirm.setOnClickListener(new View.OnClickListener() {
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
                                Intent intent = new Intent(NewBillExpense.this, MainActivity.class);
                                intent.putExtra("password" , password );
                                intent.putExtra("username" , username ) ;
                                intent.putExtra("userID" , userID ) ;
                                NewBillExpense.this.startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(NewBillExpense.this);
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

                NewBillExpenseRequest newBillExpenseRequest =  new NewBillExpenseRequest ( expense , description , userID ,   responseListener );
                RequestQueue bill = Volley.newRequestQueue(NewBillExpense.this) ;
                bill.add(newBillExpenseRequest) ;

            }
        });

    }
}
