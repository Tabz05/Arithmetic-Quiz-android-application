package com.tabish.arithmeticquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

public class arcadeGameInfo extends AppCompatActivity {

    private String level="";

    public void difficultySelectArcade(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        if(checked && view.getId()==R.id.easyArcade)
        {
            level="easy";
        }
        else if(checked && view.getId()==R.id.mediumArcade)
        {
            level="medium";
        }
        else if(checked && view.getId()==R.id.difficultArcade)
        {
            level="difficult";
        }
    }

    public void startArcade (View view)
    {
        if (level.toString().equals("easy") || level.toString().equals("medium") || level.toString().equals("difficult")) {

            Intent goToArcadeGame = new Intent(getApplicationContext(),arcadeGame.class);
            goToArcadeGame.putExtra("level", level.toString());

            startActivity(goToArcadeGame);
        }
        else
        {
            Toast.makeText(this, "Please select difficulty", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcade_game_info);
    }
}