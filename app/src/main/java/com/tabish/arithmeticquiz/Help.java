package com.tabish.arithmeticquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Help extends AppCompatActivity {

    private TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        t = findViewById(R.id.textHelp);
        t.setText("1.In normal mode, the players choose the difficulty level and the duration which they wish to play\n\n2.In arcade mode, the player chooses the difficulty level and plays the game, the game ends when player gives an incorrect answer to a question.");
    }
}