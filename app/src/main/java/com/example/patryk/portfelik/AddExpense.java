package com.example.patryk.portfelik;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.patryk.portfelik.R.id.etPrice;

public class AddExpense extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);


        final EditText etExpense = (EditText) findViewById(R.id.etDescription) ;
        final EditText etDescription = (EditText) findViewById(etPrice) ;
        final Button bConfirm = (Button) findViewById(R.id.confirmExpenseBt) ;

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
                              Intent intent = new Intent(AddExpense.this, MainActivity.class);
                              intent.putExtra("password" , password );
                              intent.putExtra("username" , username ) ;
                              intent.putExtra("userID" , userID ) ;
                              AddExpense.this.startActivity(intent);
                              finish();
                          } else {
                              AlertDialog.Builder builder = new AlertDialog.Builder(AddExpense.this);
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

              AddExpenseRequest addExpenseRequest = new AddExpenseRequest( expense , description ,  userID , responseListener );
              RequestQueue queue = Volley.newRequestQueue(AddExpense.this) ;
              queue.add(addExpenseRequest) ;
          }
      });

    }
}
