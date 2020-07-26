package com.example.mysplesh

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    var buttons = Array(3) { arrayOfNulls<Button>(3) }
    var playerOneTurn = true
    var roundCount = 0
    var playerOnePoints = 0
    var playerTwoPoints = 0
    var textViewPlayer1: TextView? = null
    var textViewPlayer2: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewPlayer1 = findViewById(R.id.text_view_p1)
        textViewPlayer2 = findViewById(R.id.text_view_p2)
        for (i in 0..2) {
            for (j in 0..2) {
                val buttonID = "button_$i$j"
                val resID = resources.getIdentifier(buttonID, "id", packageName)
                buttons[i][j] = findViewById(resID)
                buttons[i][j]?.setOnClickListener(this)
            }
        }
        val buttonReset = findViewById<Button>(R.id.button_reset)
        buttonReset.setOnClickListener { resetGame() }
    }

    override fun onClick(v: View) {
        if ((v as Button).text.toString() != "") {
            return
        }
        if (playerOneTurn) {
            v.text = "X"
        } else {
            v.text = "0"
        }
        roundCount++
        if (checkForWin()) {
            if (playerOneTurn) {
                player1Wins()
            } else {
                player2Wins()
            }
        } else if (roundCount == 9) {
            drawgame()
        } else {
            playerOneTurn = !playerOneTurn
        }
    }

    private fun checkForWin(): Boolean {
        val field = Array(3) { arrayOfNulls<String>(3) }
        for (i in 0..2) {
            for (j in 0..2) {
                field[i][j] = buttons[i][j]!!.text.toString()
            }
        }
        for (i in 0..2) {
            if (field[i][0] == field[i][1] && field[i][0] == field[i][2] && field[i][0] != "") {
                return true
            }
        }
        for (i in 0..2) {
            if (field[0][i] == field[1][i] && field[0][i] == field[2][i] && field[0][i] != "") {
                return true
            }
        }
        if (field[0][0] == field[1][1] && field[0][0] == field[2][2] && field[0][0] != "") {
            return true
        }
        return if (field[0][2] == field[1][1] && field[0][2] == field[2][0] && field[0][2] != "") {
            true
        } else false
    }

    fun player1Wins() {
        playerOnePoints++
        Toast.makeText(this, "Player one wins!", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    fun drawgame() {
        Toast.makeText(this, "Match Draw Please play again!", Toast.LENGTH_SHORT).show()
        resetBoard()
    }

    fun player2Wins() {
        playerTwoPoints++
        Toast.makeText(this, "Player two wins!", Toast.LENGTH_SHORT).show()
        updatePointsText()
        resetBoard()
    }

    fun updatePointsText() {
        textViewPlayer1!!.text = "Player One: $playerOnePoints"
        textViewPlayer2!!.text = "Player Two: $playerTwoPoints"
    }

    fun resetBoard() {
        for (i in 0..2) {
            for (j in 0..2) {
                buttons[i][j]!!.text = ""
            }
        }
        roundCount = 0
        playerOneTurn = true
    }

    fun resetGame() {
        playerOnePoints = 0
        playerTwoPoints = 0
        updatePointsText()
        resetBoard()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want Exit ?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, which -> super@MainActivity.onBackPressed() }
                .setNegativeButton("No") { dialog, which -> dialog.cancel() }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}