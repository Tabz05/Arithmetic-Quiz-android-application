package com.tabish.arithmeticquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyProfile extends AppCompatActivity {

    private FirebaseAuth frbAuth;
    private FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private TextView myProfileText;
    private ImageView myProfilePicture;
    private long i;

    private void showProfilePic() //getting profile pic
    {
        StorageReference ref = storageReference.child("users").child(currentUser.getUid()).child("image");
        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {


                /*Glide.with(MyProfile.this)
                        .load(uri.toString())
                        .into(myProfilePicture);*/

                Glide.with(getApplicationContext()).load(uri.toString()).into(myProfilePicture);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        myProfileText=findViewById(R.id.MyProfileText);
        myProfilePicture=findViewById(R.id.imageView2);

        frbAuth = FirebaseAuth.getInstance();

        currentUser= frbAuth.getCurrentUser();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        if(currentUser!=null)
        {
            mDatabase.child("users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    User Current_User = snapshot.getValue(User.class);

                    i = Current_User.hasImage;

                    String info = "Username: " + Current_User.user_name + "\n\nEmail: " + Current_User.email_id + "\n\nProfile picture: ";
                    myProfileText.setText(info.toString());

                    if(i==1)
                    {
                        showProfilePic();
                    }
                     
                    else
                    {
                        myProfilePicture.setImageResource(R.drawable.profilepic);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        }
    }
