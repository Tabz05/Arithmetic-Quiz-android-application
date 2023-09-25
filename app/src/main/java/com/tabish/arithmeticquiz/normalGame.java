package com.tabish.arithmeticquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class normalGame extends AppCompatActivity {

    private FirebaseAuth frbAuth;
    private FirebaseUser currentUser;

    private DatabaseReference mDatabase;

    private TextView timerText;
    private TextView questionText;
    private TextView scoreText;
    private TextView correctOrWrong;
    private TextView difficultyText;

    private CountDownTimer countDownTimer;

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    private String minutes;
    private String seconds;
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

    public void checkAns(View view)
    {
        no_of_ques+=1;

        if(currentUser!=null)
        {
            mDatabase.child("users").child(currentUser.getUid()).child("no_of_ques_attempt").setValue(ServerValue.increment(1));
        }

        if(Integer.toString(option).equals(view.getTag().toString()))
        {
            if(currentUser!=null)
            {
                mDatabase.child("users").child(currentUser.getUid()).child("no_of_ques_correct").setValue(ServerValue.increment(1));
            }
            score+=1;

            correctOrWrong.setText("CORRECT");
            correctOrWrong.setTextColor(getResources().getColor(R.color.green));

        }
        else
        {
            correctOrWrong.setText("INCORRECT");
            correctOrWrong.setTextColor(getResources().getColor(R.color.red));
        }
        scoreText.setText(Long.toString(score)+"/"+Long.toString(no_of_ques));
        generateQues();
        setAns();
    }

    private void updatetimertext (int secondschange)
    {
        int min = secondschange / 60;
        int sec = secondschange - (min * 60);

        String secondstring = Integer.toString(sec);
        if(secondstring.equals("0"))
        {
            secondstring="00";
        }
        if(sec<=9 && !secondstring.equals("00"))
        {
            secondstring= "0"+ secondstring;
        }

        timerText.setText(Integer.toString(min) + ":" + secondstring);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_game);

        Intent fromNormalGameInfo = getIntent();

        minutes = fromNormalGameInfo.getStringExtra("minutes");
        seconds = fromNormalGameInfo.getStringExtra("seconds");

        level= fromNormalGameInfo.getStringExtra("level");

        frbAuth = FirebaseAuth.getInstance();

        currentUser= frbAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        timerText=findViewById(R.id.timerText);
        correctOrWrong=findViewById(R.id.correctOrWrong);
        questionText=findViewById(R.id.questionText);
        scoreText=findViewById(R.id.scoreText);
        difficultyText=findViewById(R.id.difficultyText);

        button1=findViewById(R.id.buttonA);
        button2=findViewById(R.id.buttonB);
        button3=findViewById(R.id.buttonC);
        button4=findViewById(R.id.buttonD);

        if(Integer.parseInt(seconds)<=9)
        {
            timerText.setText(minutes+":0"+seconds);
        }
        else
        {
            timerText.setText(minutes+":"+seconds);
        }

        if(level.toString().equals("easy"))
        {
            lower_limit=0;
            upper_limit=100;
            difficultyText.setText("Level: EASY"+"\nMode: NORMAL");
        }
        if(level.toString().equals("medium"))
        {
            lower_limit=-100;
            upper_limit= 100;
            difficultyText.setText("Level: MEDIUM"+"\nMode: NORMAL");
        }
        if(level.toString().equals("difficult"))
        {
            lower_limit=-1000;
            upper_limit=1000;
            difficultyText.setText("Level: DIFFICULT"+"\nMode: NORMAL");
        }

        generateQues();
        setAns();

        countDownTimer = new CountDownTimer(((Integer.parseInt(seconds)+(Integer.parseInt(minutes)*60))*1000),1000){

            @Override
            public void onTick(long l) { //l is no of milliseconds left in timer

                updatetimertext ((int) l/1000);
            }

            @Override
            public void onFinish() {

                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);

                Intent goToNormalResult = new Intent(getApplicationContext(),normalResult.class);

                goToNormalResult.putExtra("no_of_ques",Long.toString(no_of_ques));
                goToNormalResult.putExtra("score",Long.toString(score));
                goToNormalResult.putExtra("minutes",minutes.toString());
                goToNormalResult.putExtra("seconds",seconds.toString());
                goToNormalResult.putExtra("level",level.toString());

                startActivity(goToNormalResult);
            }
        }.start();
    }


    @Override //to prevent going back when back button is clicked
    public void onBackPressed()
    {
        Toast.makeText(this,"game cannot be terminated in between",Toast.LENGTH_LONG).show();
    }

}