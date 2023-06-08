package com.tabish.arithmeticquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    private EditText usernameSignUp;
    private EditText emailSignUp;
    private EditText passSignUp;
    private TextView textsignup;

    private CountDownTimer countDownTimer;

    private FirebaseAuth frbAuth;
    private FirebaseUser currentUser;

    private DatabaseReference mDatabase;

    private String username;
    private String email;
    private String pass;

    private void sendEmailVerification() {

        final FirebaseUser user = frbAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {

                            textsignup.setText("Registered. A verification email has been sent to "+ email+" Sign In now");

                        }
                        else {
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();

                            textsignup.setText("not Registered " + e.getMessage());
                        }
                    }
                });
    }

    private void createUser(){
        username=usernameSignUp.getText().toString();
        email = emailSignUp.getText().toString();
        pass = passSignUp.getText().toString();

        if (!email.isEmpty() && !pass.isEmpty()){
            if (pass.toString().length()>=6){
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    frbAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information

                                        sendEmailVerification();

                                        currentUser = frbAuth.getCurrentUser();

                                        User user = new User(username,email);

                                        mDatabase = FirebaseDatabase.getInstance().getReference();

                                        mDatabase.child("users").child(currentUser.getUid());
                                        mDatabase.child("users").child(currentUser.getUid()).setValue(user);

                                        countDownTimer = new CountDownTimer(3000,1000){

                                            @Override
                                            public void onTick(long l) { //l is no of milliseconds left in timer

                                            }

                                            @Override
                                            public void onFinish() {

                                                Intent goToPicUpload = new Intent (getApplicationContext(),picUpload.class);
                                                startActivity(goToPicUpload);
                                            }
                                        }.start();

                                    } else {
                                        // If sign in fails, display a message to the user.

                                        FirebaseAuthException e = (FirebaseAuthException) task.getException();

                                        textsignup.setText("not Registered " + e.getMessage());
                                    }

                                }
                            });
                }
                else
                {
                    Toast.makeText(this,"Please enter a valid email id ",Toast.LENGTH_LONG).show();
                }

            }
            else{
                Toast.makeText(this,"Password must be at least 6 characters long",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this,"Empty Fields Are not Allowed",Toast.LENGTH_LONG).show();
        }
    }

    public void SignUpClicked(View view)
    {
        createUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameSignUp=findViewById(R.id.usernameSignUp);
        emailSignUp=findViewById(R.id.emailSignUp);
        passSignUp=findViewById(R.id.passSignUp);
        textsignup=findViewById(R.id.textSignUp);

        frbAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }
}