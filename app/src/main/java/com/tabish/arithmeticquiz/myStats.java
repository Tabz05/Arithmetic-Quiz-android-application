package com.tabish.arithmeticquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myStats extends AppCompatActivity {

    private FirebaseAuth frbAuth;
    private FirebaseUser currentUser;

    private DatabaseReference mDatabase;

    private TextView myStatsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_stats);

        myStatsText=findViewById(R.id.MyStatsText);

        frbAuth = FirebaseAuth.getInstance();

        currentUser= frbAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(currentUser!=null)
        {
            mDatabase.child("users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User Current_User=snapshot.getValue(User.class);

                    String info="Username: "+Current_User.user_name+"\nEmail: "+Current_User.email_id+"\n\nStatistics"+"\n\nNormal Mode "+"\n\nNo of questions attempted: "+Current_User.no_of_ques_attempt+"\nNo of questions correct: "+Current_User.no_of_ques_correct+"\n\nArcade mode "+"\n\nNo of questions attempted: "+Current_User.no_of_ques_attempt_arcade+"\nNo of questions correct: "+Current_User.no_of_ques_correct_arcade+"\n\n(Stats are saved only when a game finishes while signed in)";
                    myStatsText.setText(info.toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}