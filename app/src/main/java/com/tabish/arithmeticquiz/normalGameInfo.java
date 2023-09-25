package com.tabish.arithmeticquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class normalGameInfo extends AppCompatActivity {

    private EditText minutes;
    private EditText seconds;

    private String level="";

    public void difficultySelect(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        if(checked && view.getId()==R.id.easy)
        {
            level="easy";
        }
        else if(checked && view.getId()==R.id.medium)
        {
            level="medium";
        }
        else if(checked && view.getId()==R.id.difficult)
        {
            level="difficult";
        }
    }

    public void start (View view)
    {
        if(!minutes.getText().toString().isEmpty() && !seconds.getText().toString().isEmpty()) {
            if (Integer.parseInt(minutes.getText().toString()) >= 0 && Integer.parseInt(seconds.getText().toString()) >= 0 && (Integer.parseInt(minutes.getText().toString()) + Integer.parseInt(seconds.getText().toString())) > 0) {
                if (Integer.parseInt(seconds.getText().toString()) <= 59) {
                    if (level.toString().equals("easy") || level.toString().equals("medium") || level.toString().equals("difficult")) {

                        Intent goToNormalGame = new Intent(getApplicationContext(), normalGame.class);

                        goToNormalGame.putExtra("minutes", minutes.getText().toString());
                        goToNormalGame.putExtra("seconds", seconds.getText().toString());
                        goToNormalGame.putExtra("level", level.toString());

                        startActivity(goToNormalGame);
                    } else {
                        Toast.makeText(this, "Please select difficulty", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this, "No of seconds has to be less than or equal to 59", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(this, "No of minutes and seconds has to be greater than 0", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Please select duration", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_game_info);

        minutes=findViewById(R.id.minutes);
        seconds=findViewById(R.id.seconds);
    }
}