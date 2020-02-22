package com.example.mysplesh;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button[][] buttons = new Button[3][3];
    public boolean playerOneTurn = true;

    public int roundCount;

    public int playerOnePoints;
    public int playerTwoPoints;

    public TextView textViewPlayer1;
    public TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());


                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }



    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if ( playerOneTurn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("0");
        }

        roundCount++;

        if (checkForWin()) {
            if ( playerOneTurn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            drawgame();
        } else {
            playerOneTurn = ! playerOneTurn;
        }

    }
    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
    public void player1Wins() {
        playerOnePoints++;
        Toast.makeText(this, "Player one wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    public void drawgame() {
        Toast.makeText(this, "Match Draw Please play again!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    public void player2Wins() {
        playerTwoPoints++;
        Toast.makeText(this, "Player two wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }
    public void updatePointsText() {
        textViewPlayer1.setText("Player One: " + playerOnePoints);
        textViewPlayer2.setText("Player Two: " + playerTwoPoints);
    }




    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        playerOneTurn = true;
    }
    public void resetGame() {
        playerOnePoints = 0;
        playerTwoPoints = 0;
        updatePointsText();
         resetBoard();
    }




    @Override
    public void onBackPressed() {

        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want Exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }








}
