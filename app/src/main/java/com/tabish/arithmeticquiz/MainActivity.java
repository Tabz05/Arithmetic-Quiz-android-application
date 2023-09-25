package com.tabish.arithmeticquiz;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth frbAuth;
    private FirebaseUser currentUser;

    private DatabaseReference mDatabase;

    private TextView welcomeUser;

    private boolean isNetworkAvailable() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            connected = true;
        }
        else
        {
            connected = false;
        }

        return connected;
    }

    //requesting permissions
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goToMain);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();

        if(currentUser!=null)
        {
            menuInflater.inflate(R.menu.menu_bar_logged_in,menu);
        }
        else
        {
            menuInflater.inflate(R.menu.menu_bar, menu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.myStats)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            } else {
                if (isNetworkAvailable()) {
                        Toast.makeText(this, "My stats", Toast.LENGTH_LONG).show();
                        Intent goToMyStats = new Intent(getApplicationContext(), myStats.class);

                        startActivity(goToMyStats);
                    } else {
                        Toast.makeText(this, "No internet access", Toast.LENGTH_LONG).show();
                    }
            }
                return true;
        }

        else if(item.getItemId()==R.id.editProfile)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            } else {
                if (isNetworkAvailable()) {
                    Toast.makeText(this, "Edit Profile", Toast.LENGTH_LONG).show();
                    Intent goToEditProfile = new Intent(getApplicationContext(), EditProfile.class);

                    startActivity(goToEditProfile);
                } else {
                    Toast.makeText(this, "No internet access", Toast.LENGTH_LONG).show();
                }

            }
            return true;
        }

        else if(item.getItemId()==R.id.aboutus)
        {
            Toast.makeText(this, "About Us", Toast.LENGTH_LONG).show();
            Intent goToAboutUs = new Intent(getApplicationContext(), AboutUs.class);

            startActivity(goToAboutUs);

            return true;
        }

        if(item.getItemId()==R.id.help)
        {
            Toast.makeText(this, "Help", Toast.LENGTH_LONG).show();
            Intent goToHelp = new Intent(getApplicationContext(), Help.class);

            startActivity(goToHelp);

            return true;
        }

        else if(item.getItemId()==R.id.signUp)
        {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, 1); //asking for internet permission
            } else
            {
                if(isNetworkAvailable()) //if connected to internet
                {
                    Toast.makeText(this, "Sign Up", Toast.LENGTH_LONG).show();
                    Intent goToSignUp = new Intent(getApplicationContext(), SignUp.class);
                    startActivity(goToSignUp);
                }
                else
                {
                    Toast.makeText(this, "No internet access", Toast.LENGTH_LONG).show();
                }

            }

            return true;
        }

        else if(item.getItemId()==R.id.signIn)
        {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, 1);
            } else
            {
                if(isNetworkAvailable())
                {
                    Toast.makeText(this, "Sign In", Toast.LENGTH_LONG).show();
                    Intent goToSignIn = new Intent(getApplicationContext(), SignIn.class);
                    startActivity(goToSignIn);
                }
                else
                {
                    Toast.makeText(this, "No internet access", Toast.LENGTH_LONG).show();
                }
            }

            return true;
        }

        else if(item.getItemId()==R.id.signOut)
        {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, 1);
            } else {
                if(isNetworkAvailable())
                {
                    frbAuth.signOut();
                    Intent goToMainActivity = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(goToMainActivity);
                }
                else
                {
                    Toast.makeText(this, "No internet access", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }

        else if(item.getItemId()==R.id.myProfile)
        {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, 1);
            } else
            {
                if(isNetworkAvailable())
                {
                    Toast.makeText(this, "Your Profile", Toast.LENGTH_LONG).show();
                    Intent goToMyProfile = new Intent(getApplicationContext(), MyProfile.class);
                    startActivity(goToMyProfile);
                }
                else
                {
                    Toast.makeText(this, "No internet access", Toast.LENGTH_LONG).show();
                }
            }
            return true;
        }

        else if(item.getItemId()==R.id.resetStat)
        {
            long x=0;

            if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, 1);
            } else
            {
                if(isNetworkAvailable())
                {

                    new AlertDialog.Builder(this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Are you sure!?")
                            .setMessage("Do you want to reset your stats?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    mDatabase.child("users").child(currentUser.getUid()).child("no_of_ques_attempt").setValue(x);
                                    mDatabase.child("users").child(currentUser.getUid()).child("no_of_ques_correct").setValue(x);
                                    mDatabase.child("users").child(currentUser.getUid()).child("no_of_ques_attempt_arcade").setValue(x);
                                    mDatabase.child("users").child(currentUser.getUid()).child("no_of_ques_correct_arcade").setValue(x);

                                    Toast.makeText(MainActivity.this,"Stats reset completed",Toast.LENGTH_LONG).show();

                                    Intent goToStat = new Intent(getApplicationContext(),myStats.class);
                                    startActivity(goToStat);
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();
                }
                else
                {
                    Toast.makeText(this, "No internet access", Toast.LENGTH_LONG).show();
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public void normalMode(View view)
    {
        if(currentUser!=null)
        {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Since you are logged in, you need to have access to internet to play the game",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, 1);
            }
            else
            {
                if(isNetworkAvailable())
                {
                    Intent goToNormalGameInfo = new Intent(getApplicationContext(), normalGameInfo.class);
                    startActivity(goToNormalGameInfo);
                }
                else
                {
                    Toast.makeText(this,"Since you are logged in, you need to have access to internet to play the game",Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            Intent goToNormalGameInfo = new Intent(getApplicationContext(), normalGameInfo.class);
            startActivity(goToNormalGameInfo);
        }

    }

    public void arcadeMode(View view)
    {
        if(currentUser!=null)
        {
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this,"Since you are logged in, you need to have access to internet to play the game",Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET}, 1);
            }
            else
            {
                if(isNetworkAvailable())
                {
                    Intent goToArcadeGameInfo = new Intent(getApplicationContext(), arcadeGameInfo.class);
                    startActivity(goToArcadeGameInfo);
                }
                else
                {
                    Toast.makeText(this,"Since you are logged in, you need to have access to internet to play the game",Toast.LENGTH_LONG).show();
                }
            }
        }
        else
        {
            Intent goToArcadeGameInfo = new Intent(getApplicationContext(), arcadeGameInfo.class);
            startActivity(goToArcadeGameInfo);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frbAuth = FirebaseAuth.getInstance();
        currentUser = frbAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        welcomeUser = findViewById(R.id.welcomeUser);

        if(currentUser!=null)
        {
            mDatabase.child("users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User Current_User=snapshot.getValue(User.class);

                    String welcome="Welcome "+Current_User.user_name;
                    welcomeUser.setText(welcome.toString());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}