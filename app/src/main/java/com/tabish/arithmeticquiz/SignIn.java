package com.tabish.arithmeticquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class SignIn extends AppCompatActivity {

    private EditText emailSignIn;
    private EditText passSignIn;
    private TextView textsignIn;

    private FirebaseAuth frbAuth;

    private void loginUser() {

        String email = emailSignIn.getText().toString();
        String password = passSignIn.getText().toString();
        frbAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent goToMainActivity = new Intent (getApplicationContext(),MainActivity.class);
                            startActivity(goToMainActivity);
                        } else {
                            // If sign in fails, display a message to the user.
                            FirebaseAuthException e = (FirebaseAuthException) task.getException();

                            textsignIn.setText("not Registered " + e.getMessage());
                        }

                    }
                });
    }

    public void SignInClicked(View view) {
        loginUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailSignIn=findViewById(R.id.emailSignIn);
        passSignIn=findViewById(R.id.passSignIn);
        textsignIn=findViewById(R.id.textSignIn);

        frbAuth = FirebaseAuth.getInstance();
    }
}