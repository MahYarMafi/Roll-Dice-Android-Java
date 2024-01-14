package com.example.rolldice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtMyScore, txtPcScore, txtMyDetail, txtPcDetail, txtSide;
    ImageView imgDice;
    int myScore = 0;
    int pcScore = 0;
    final static int WIN_SCORE = 100;
    boolean isPcTurn = false;
    ArrayList<Integer> myNumbers;
    ArrayList<Integer> pcNumbers;

    /**
     * Show proper caption based on turn.
     */
    private void processSideCaption() {
        if (isPcTurn) {
            txtSide.setText("PC Turn");
        } else {
            txtSide.setText("your Turn, Click on dice to continue.");
        }
    }

    /**
     * Compute String presentation of detail dynamic array
     */
    private String computeDetailString(ArrayList<Integer> detailArray) {
        String detail = "";
        boolean isFirst = true;
        for (Integer myNumber : detailArray) {
            if (isFirst) {
                detail += myNumber;
                isFirst = false;
                continue;
            }
            detail += " + " + myNumber;
        }
        return detail;
    }

    /**
     * Show winner
     */
    private void win() {
        Intent intent  =new Intent(MainActivity.this,WonActivity.class);
        if (isPcTurn) {
            intent.putExtra("who"," PC");
        } else {
            intent.putExtra("who"," You");
        }
       startActivity(intent);
        finish();
    }


    /**
     * ROll dice and return result
     */
    private void rollDice() {
        // Generate random number from 1 to 6 inclusive
        int number = (int) Math.floor(Math.random() * 6 + 1);
        //set dice graphical resource
        int res = 0;
        switch (number) {
            case 1:
                res = R.drawable.dice_1;
                break;
            case 2:
                res = R.drawable.dice_2;
                break;
            case 3:
                res = R.drawable.dice_3;
                break;
            case 4:
                res = R.drawable.dice_4;
                break;
            case 5:
                res = R.drawable.dice_5;
                break;
            case 6:
                res = R.drawable.dice_6;
                break;
        }
        imgDice.setImageResource(res);

        //Compute total score and process UI
        if (isPcTurn) {
            pcNumbers.add(number);
            pcScore += number;
            txtPcDetail.setText(computeDetailString(pcNumbers));
            txtPcScore.setText("" + pcScore);
            if (pcScore >= WIN_SCORE) {
                win();
                return;
            }
        } else {
            myNumbers.add(number);
            myScore += number;
            txtMyDetail.setText(computeDetailString(myNumbers));
            txtMyScore.setText("" + myScore);
            if (myScore >= WIN_SCORE) {
                win();
                return;
            }
        }

        // rol dice again for lucky player
        if (number != 6) {
            isPcTurn = !isPcTurn;
            processSideCaption();
        }
        // if player is PC roll dice automatically
        if (isPcTurn) {
            rollDice();
        }
    }

    private void initializeUI() {

        //Initialize variables
        txtMyDetail = findViewById(R.id.txtMyDetail);
        txtPcScore = findViewById(R.id.txtPcScore);
        txtMyScore = findViewById(R.id.txtMyScore);
        txtPcDetail = findViewById(R.id.txtPcDetail);
        txtSide = findViewById(R.id.txtSide);
        imgDice = (ImageView) findViewById(R.id.imgDice);

        //initialize UI
        txtMyScore.setText("0");
        txtPcScore.setText("0");
        txtMyDetail.setText("");
        txtPcDetail.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUI();

        //initialize dynamic arrays
        myNumbers = new ArrayList<Integer>();
        pcNumbers = new ArrayList<Integer>();


        //roll dice automatically if pc turn
        isPcTurn = Math.random() >= 0.5;
        if (isPcTurn) {
            rollDice();
        }
        processSideCaption();

        imgDice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });
    }
}
