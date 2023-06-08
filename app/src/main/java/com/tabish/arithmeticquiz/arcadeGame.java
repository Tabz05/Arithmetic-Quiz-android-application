package com.tabish.arithmeticquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class arcadeGame extends AppCompatActivity {

    private FirebaseAuth frbAuth;
    private FirebaseUser currentUser;

    private DatabaseReference mDatabase;

    private Chronometer timerText;
    private TextView questionText;
    private TextView scoreText;
    private TextView correctOrWrong;
    private TextView difficultyTextArcade;

    private CountDownTimer countDownTimer;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private String minutes;
    private String seconds;
    private String time;
    private String[] timeSplit;
    private String level;

    private long score=0;
    private long no_of_ques=0;
    private int answer;
    private int option;

    private String currentQuestion="";

    private int lower_limit;
    private int upper_limit;

    private int getRandomNumber(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    private void generateDivisibleQues()
    {
        int m,n;
        m=getRandomNumber(lower_limit,upper_limit);
        n=getRandomNumber(lower_limit,upper_limit);

        if(n!=0)
        {
            if(m%n==0)
            {
                answer=m/n;
                questionText.setText(m+"/"+n +"= ?");
            }
            else
            {
                generateDivisibleQues();
            }
        }
        else
        {
            generateDivisibleQues();
        }
    }

    private void generateQues()
    {
        int x=getRandomNumber(1,4);
        int y,z;
        y=getRandomNumber(lower_limit,upper_limit);
        z=getRandomNumber(lower_limit,upper_limit);

        if(x==1)
        {
            currentQuestion="";
            answer=y+z;
            questionText.setText(y+"+"+z +"= ?");
        }
        if(x==2)
        {
            currentQuestion="";
            answer=y-z;
            questionText.setText(y+"-"+z +"= ?");
        }
        if(x==3)
        {
            currentQuestion="";
            answer=y*z;
            questionText.setText(y+"x"+z +"= ?");
        }
        if(x==4)
        {
            currentQuestion="Division";
            if(z!=0)
            {
                if(y%z==0)
                {
                    answer=y/z;
                    questionText.setText(y+"/"+z +"= ?");
                }
                else
                {
                    generateDivisibleQues();
                }
            }
            else
            {
                generateDivisibleQues();
            }
        }

    }

    private void setAns()
    {
        int x=getRandomNumber(1,4);
        option=x;
        int a,b,c,opt1,opt2,opt3;
        if(level.toString().equals("easy"))
        {
            a=getRandomNumber(lower_limit,upper_limit);
            b=getRandomNumber(lower_limit,upper_limit);
            c=getRandomNumber(lower_limit,upper_limit);
            if(a==answer || b==answer || c==answer || a==b || a==c || b==c)
            {
                setAns();
            }
            else {
                if (x == 1) {
                    button1.setText(Integer.toString(answer));
                    button2.setText(Integer.toString(a));
                    button3.setText(Integer.toString(b));
                    button4.setText(Integer.toString(c));
                }
                if (x == 2) {
                    button2.setText(Integer.toString(answer));
                    button1.setText(Integer.toString(a));
                    button3.setText(Integer.toString(b));
                    button4.setText(Integer.toString(c));

                }
                if (x == 3) {
                    button3.setText(Integer.toString(answer));
                    button1.setText(Integer.toString(a));
                    button2.setText(Integer.toString(b));
                    button4.setText(Integer.toString(c));
                }
                if (x == 4) {
                    button4.setText(Integer.toString(answer));
                    button1.setText(Integer.toString(a));
                    button2.setText(Integer.toString(b));
                    button3.setText(Integer.toString(c));
                }
            }
        }

        if(level.toString().equals("medium"))
        {
            if(currentQuestion.toString().equals("Division"))
            {
                a=getRandomNumber(0,10);
                b=getRandomNumber(0,10);
                c=getRandomNumber(0,10);
            }
            else
            {
                a=getRandomNumber(100,250);
                b=getRandomNumber(100,250);
                c=getRandomNumber(100,250);
            }
            opt1=answer+a;
            opt2=answer-b;
            opt3=answer+c;
            if(opt1==answer || opt2==answer || opt3==answer || opt1==opt2 || opt1==opt3 || opt2==opt3)
            {
                setAns();
            }
            else {
                if (x == 1) {
                    button1.setText(Integer.toString(answer));
                    button2.setText(Integer.toString(opt1));
                    button3.setText(Integer.toString(opt2));
                    button4.setText(Integer.toString(opt3));
                }
                if (x == 2) {
                    button2.setText(Integer.toString(answer));
                    button1.setText(Integer.toString(opt1));
                    button3.setText(Integer.toString(opt2));
                    button4.setText(Integer.toString(opt3));

                }
                if (x == 3) {
                    button3.setText(Integer.toString(answer));
                    button1.setText(Integer.toString(opt1));
                    button2.setText(Integer.toString(opt2));
                    button4.setText(Integer.toString(opt3));
                }
                if (x == 4) {
                    button4.setText(Integer.toString(answer));
                    button1.setText(Integer.toString(opt1));
                    button2.setText(Integer.toString(opt2));
                    button3.setText(Integer.toString(opt3));
                }
            }
        }

        if(level.toString().equals("difficult"))
        {
            if(currentQuestion.toString().equals("Division"))
            {
                a=getRandomNumber(0,5);
                b=getRandomNumber(0,5);
                c=getRandomNumber(0,5);
            }
            else
            {
                a=getRandomNumber(0,50);
                b=getRandomNumber(0,50);
                c=getRandomNumber(0,50);
            }
            opt1=answer+a;
            opt2=answer-b;
            opt3=answer+c;
            if(opt1==answer || opt2==answer || opt3==answer || opt1==opt2 || opt1==opt3 || opt2==opt3)
            {
                setAns();
            }
            else {
                if (x == 1) {
                    button1.setText(Integer.toString(answer));
                    button2.setText(Integer.toString(opt1));
                    button3.setText(Integer.toString(opt2));
                    button4.setText(Integer.toString(opt3));
                }
                if (x == 2) {
                    button2.setText(Integer.toString(answer));
                    button1.setText(Integer.toString(opt1));
                    button3.setText(Integer.toString(opt2));
                    button4.setText(Integer.toString(opt3));

                }
                if (x == 3) {
                    button3.setText(Integer.toString(answer));
                    button1.setText(Integer.toString(opt1));
                    button2.setText(Integer.toString(opt2));
                    button4.setText(Integer.toString(opt3));
                }
                if (x == 4) {
                    button4.setText(Integer.toString(answer));
                    button1.setText(Integer.toString(opt1));
                    button2.setText(Integer.toString(opt2));
                    button3.setText(Integer.toString(opt3));
                }
            }
        }
    }

    public void checkAnsArcade(View view)
    {
        no_of_ques+=1;

        if(currentUser!=null)
        {
            mDatabase.child("users").child(currentUser.getUid()).child("no_of_ques_attempt_arcade").setValue(ServerValue.increment(1));
        }

        if(Integer.toString(option).equals(view.getTag().toString()))
        {
            if(currentUser!=null)
            {
                mDatabase.child("users").child(currentUser.getUid()).child("no_of_ques_correct_arcade").setValue(ServerValue.increment(1));
            }
            score+=1;
            correctOrWrong.setText("CORRECT");
            correctOrWrong.setTextColor(getResources().getColor(R.color.green));
            scoreText.setText(Long.toString(score)+"/"+Long.toString(no_of_ques));
            generateQues();
            setAns();
        }
        else
        {
            correctOrWrong.setText("INCORRECT");
            correctOrWrong.setTextColor(getResources().getColor(R.color.red));
            scoreText.setText(Long.toString(score)+"/"+Long.toString(no_of_ques));

            time=timerText.getText().toString();
            timeSplit=time.toString().split(":");
            minutes=timeSplit[0];
            seconds=timeSplit[1];
            timerText.stop();
            button1.setEnabled(false);
            button2.setEnabled(false);
            button3.setEnabled(false);
            button4.setEnabled(false);

            countDownTimer = new CountDownTimer(1500,1000){

                @Override
                public void onTick(long l) { //l is no of milliseconds left in timer
                }

                @Override
                public void onFinish() {

                   /* if(currentUser!=null)
                    {
                        mDatabase.child("users").child(currentUser.getUid()).child("no_of_ques_attempt_arcade").setValue(ServerValue.increment(no_of_ques));
                        mDatabase.child("users").child(currentUser.getUid()).child("no_of_ques_correct_arcade").setValue(ServerValue.increment(score));
                    }*/

                    Intent goToArcadeResult = new Intent(getApplicationContext(),arcadeResult.class);

                    goToArcadeResult.putExtra("no_of_ques",Long.toString(no_of_ques));
                    goToArcadeResult.putExtra("score",Long.toString(score));
                    goToArcadeResult.putExtra("minutes",minutes.toString());
                    goToArcadeResult.putExtra("seconds",seconds.toString());
                    goToArcadeResult.putExtra("level",level.toString());

                    startActivity(goToArcadeResult);
                }
            }.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcade_game);

        Intent fromArcadeGameInfo = getIntent();

        level= fromArcadeGameInfo.getStringExtra("level");

        frbAuth = FirebaseAuth.getInstance();

        currentUser= frbAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        timerText=findViewById(R.id.timerTextArcade);
        correctOrWrong=findViewById(R.id.correctOrWrongArcade);
        questionText=findViewById(R.id.questionTextArcade);
        scoreText=findViewById(R.id.scoreTextArcade);
        difficultyTextArcade=findViewById(R.id.difficultyTextArcade);

        button1=findViewById(R.id.buttonW);
        button2=findViewById(R.id.buttonX);
        button3=findViewById(R.id.buttonY);
        button4=findViewById(R.id.buttonZ);

        if(level.toString().equals("easy"))
        {
            lower_limit=0;
            upper_limit=100;
            difficultyTextArcade.setText("Level: EASY"+"\nMode: ARCADE");
        }
        if(level.toString().equals("medium"))
        {
            lower_limit=-100;
            upper_limit= 100;
            difficultyTextArcade.setText("Level: MEDIUM"+"\nMode: ARCADE");
        }
        if(level.toString().equals("difficult"))
        {
            lower_limit=-1000;
            upper_limit=1000;
            difficultyTextArcade.setText("Level: DIFFICULT"+"\nMode: ARCADE");
        }

        generateQues();
        setAns();

        timerText.setBase(SystemClock.elapsedRealtime());
        timerText.start();

        timerText.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometerChanged) {
                timerText = chronometerChanged;
            }
        });
    }

    @Override //to prevent going back when back button is clicked
    public void onBackPressed()
    {
        Toast.makeText(this,"game cannot be terminated in between",Toast.LENGTH_LONG).show();
    }
}