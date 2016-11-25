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

public class AddExpenseSport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense_sport);

        final EditText etExpense = (EditText) findViewById(R.id.sportexpense) ;
        final EditText etDescription = (EditText) findViewById(R.id.sportdescr) ;
        final Button bConfirm = (Button) findViewById(R.id.sportBt) ;

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String password = intent.getStringExtra("password");
        final String username = intent.getStringExtra("username");

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
                                Intent intent = new Intent(AddExpenseSport.this, MainActivity.class);
                                intent.putExtra("password" , password );
                                intent.putExtra("username" , username ) ;
                                intent.putExtra("userID" , userID ) ;
                                AddExpenseSport.this.startActivity(intent);
                                finish();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddExpenseSport.this);
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

                AddExpenseSportRequest addExpenseSportRequest =  new AddExpenseSportRequest ( expense , description , userID , responseListener );
                RequestQueue sport = Volley.newRequestQueue(AddExpenseSport.this) ;
                sport.add(addExpenseSportRequest) ;

            }
        });

    }
}
