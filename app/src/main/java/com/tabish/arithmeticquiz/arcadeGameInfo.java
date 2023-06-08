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
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.easyArcade:
                if (checked)
                    level="easy";
                break;
            case R.id.mediumArcade:
                if (checked)
                    level="medium";
                break;
            case R.id.difficultArcade:
                if (checked)
                    level="difficult";
                break;
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