package com.example.rolldice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txtMyScore, txtPcScore, txtMyDetail, txtPcDetail, txtSide;
    ImageView imgDice;
    int myScore = 0;
    int pcScore = 0;
    boolean isPcTurn = false;
    ArrayList<Integer> myNumbers;
    ArrayList<Integer> pcNumbers;

    private void processSideCaption() {
        if (isPcTurn) {
            txtSide.setText("PC Turn");
        } else {
            txtSide.setText("your Turn, Click on dice to continue.");
        }
    }

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

    private void rollDice() {
        int number = (int) Math.floor(Math.random() * 6 + 1);
        if (isPcTurn) {
            pcNumbers.add(number);
            pcScore += number;
            txtPcDetail.setText(computeDetailString(pcNumbers));
            txtPcScore.setText("" + pcScore);
        } else {
            myNumbers.add(number);
            myScore += number;
            txtMyDetail.setText(computeDetailString(myNumbers));
            txtMyScore.setText("" + myScore);
        }

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
        if (number != 6) {
            isPcTurn = !isPcTurn;
            processSideCaption();
        }
        if (isPcTurn) {
            rollDice();
        }
    }

    private void initializeUI() {
        txtMyDetail = findViewById(R.id.txtMyDetail);
        txtPcScore = findViewById(R.id.txtPcScore);
        txtMyScore = findViewById(R.id.txtMyScore);
        txtPcDetail = findViewById(R.id.txtPcDetail);
        txtSide = findViewById(R.id.txtSide);
        imgDice = (ImageView) findViewById(R.id.imgDice);

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

        myNumbers = new ArrayList<Integer>();
        pcNumbers = new ArrayList<Integer>();


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
