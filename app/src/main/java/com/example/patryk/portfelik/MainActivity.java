package com.example.patryk.portfelik;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView newExpense = (ImageView) findViewById(R.id.newExpenseBt);
        final ImageView newTreats = (ImageView) findViewById(R.id.buttonExpTreats);
        final ImageView newBill = (ImageView) findViewById(R.id.billBtEx) ;
        final ImageView newLove = (ImageView) findViewById(R.id.loveBt) ;
        final ImageView sport = (ImageView) findViewById(R.id.confirmSport) ;

        ImageView button = (ImageView) findViewById(R.id.back);

        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        final String userID = intent.getStringExtra("userID");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ShowExpense.class);
                intent.putExtra("userID", userID);
                intent.putExtra("password", password);
                intent.putExtra("username", username);

                MainActivity.this.startActivity(intent);
            }
        });

        newExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddExpense.class);
                intent.putExtra("password" , password );
                intent.putExtra("username" , username ) ;
                intent.putExtra("userID" , userID ) ;
                MainActivity.this.startActivity(intent);

            }
        });

        newTreats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , newExpense_treats.class);
                intent.putExtra("password" , password );
                intent.putExtra("username" , username ) ;
                intent.putExtra("userID" , userID ) ;
                MainActivity.this.startActivity(intent);
            }
        });

        newBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , NewBillExpense.class);
                intent.putExtra("password" , password );
                intent.putExtra("username" , username ) ;
                intent.putExtra("userID" , userID ) ;
                MainActivity.this.startActivity(intent);
            }
        });

        newLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , NewExpenseLove.class);
                intent.putExtra("password" , password );
                intent.putExtra("username" , username ) ;
                intent.putExtra("userID" , userID ) ;
                MainActivity.this.startActivity(intent);
            }
        });
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this , AddExpenseSport.class);
                intent.putExtra("password" , password );
                intent.putExtra("username" , username ) ;
                intent.putExtra("userID" , userID ) ;
                MainActivity.this.startActivity(intent);
            }
        });
    }
}
