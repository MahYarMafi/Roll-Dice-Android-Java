package com.example.rolldice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WonActivity extends AppCompatActivity {
    Button btnPlayAgain;
    TextView txtWonCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        //initialize UI
        btnPlayAgain = (Button) findViewById(R.id.btnPlayAgain);
        txtWonCaption = (TextView)findViewById(R.id.txtWonCaption);

        // Receive bundle and who value
        Bundle bundle = getIntent().getExtras();
        String who = bundle.getString("who");

        //set caption for winner
        txtWonCaption.setText(who +" won");

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WonActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}