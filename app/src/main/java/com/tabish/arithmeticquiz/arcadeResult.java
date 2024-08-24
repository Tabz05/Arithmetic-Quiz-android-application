package com.tabish.arithmeticquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class arcadeResult extends AppCompatActivity {

    private String no_of_ques;
    private String score;
    private String minutes;
    private String seconds;
    private String level;

    private TextView resultText;

    public void home(View view)
    {
        Intent goToMain = new Intent (getApplicationContext(),MainActivity.class);
        startActivity(goToMain);
    }

    public void playAgainArcade(View view)
    {
        Intent goToArcadeGame = new Intent (getApplicationContext(),arcadeGame.class);
        goToArcadeGame.putExtra("level", level.toString());
        startActivity(goToArcadeGame);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcade_result);

        Intent fromGame = getIntent();

        no_of_ques = fromGame.getStringExtra("no_of_ques");
        score=fromGame.getStringExtra("score");
        minutes = fromGame.getStringExtra("minutes");
        seconds =fromGame.getStringExtra("seconds");
        level=fromGame.getStringExtra("level");

        resultText=findViewById(R.id.resultTextArcade);

        resultText.setText("Level: "+level+" ,Mode: Arcade"+"\n\nTotal time: "+minutes+ " minutes "+seconds+" seconds"+"\n\n"+"Total Number of questions attempted: "+no_of_ques+"\n\n"+"Total number of questions correct: "+score);
    }

    @Override
    public void onBackPressed()
    {

    }
}