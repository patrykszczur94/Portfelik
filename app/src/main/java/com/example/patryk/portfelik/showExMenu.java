package com.example.patryk.portfelik;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class showExMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ex_menu);
        Intent intent = getIntent();
        final String username = intent.getStringExtra("username");
        final String password = intent.getStringExtra("password");
        final String userID = intent.getStringExtra("userID");

        ImageView back1 = (ImageView) findViewById(R.id.back1);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showExMenu.this, ShowExpense.class);
                intent.putExtra("userID", userID);
                intent.putExtra("password", password);
                intent.putExtra("username", username);
                showExMenu.this.startActivity(intent);
            }
        });

        ImageView showSport = (ImageView) findViewById(R.id.ExForSport);
        showSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(showExMenu.this , ShowSportsEx.class);
                intent.putExtra("password" , getIntent().getStringExtra("username") );
                intent.putExtra("username" , getIntent().getStringExtra("password") ) ;
                String user  = getIntent().getStringExtra("password");
                showExMenu.this.startActivity(intent);
            }
        });

        ImageView showBills = (ImageView) findViewById(R.id.showBills);
        showBills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showExMenu.this , ShowBills.class);
                intent.putExtra("password" , getIntent().getStringExtra("username") );
                intent.putExtra("username" , getIntent().getStringExtra("password") ) ;

                showExMenu.this.startActivity(intent);  }
        });

        ImageView showLove = (ImageView) findViewById(R.id.showLove);
        showLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showExMenu.this , showLove.class);
                intent.putExtra("password" , getIntent().getStringExtra("username") );
                intent.putExtra("username" , getIntent().getStringExtra("password") ) ;
                showExMenu.this.startActivity(intent);  }
        });
        ImageView showTreats = (ImageView) findViewById(R.id.showTreats);
        showTreats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showExMenu.this , showTreats.class);
                intent.putExtra("password" , getIntent().getStringExtra("username") );
                intent.putExtra("username" , getIntent().getStringExtra("password") ) ;
                showExMenu.this.startActivity(intent);  }
        });
        ImageView showFoodd = (ImageView) findViewById(R.id.showFood);
        showFoodd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(showExMenu.this , ShowFood.class);
                intent.putExtra("password" , getIntent().getStringExtra("username") );
                intent.putExtra("username" , getIntent().getStringExtra("password") ) ;
                showExMenu.this.startActivity(intent);  }
        });
    }
}
