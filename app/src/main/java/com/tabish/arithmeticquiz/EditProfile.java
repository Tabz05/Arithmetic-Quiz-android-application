package com.tabish.arithmeticquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfile extends AppCompatActivity {

    private FirebaseAuth frbAuth;
    private FirebaseUser currentUser;

    private DatabaseReference mDatabase;

    private Uri selectedImage;

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private EditText newUsername;
    private Button removePic;

    private ImageView imageView3;

    private String Username;
    private long hasImage;

    private void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    //requesting permission

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            }
        }
    }

    public void chooseProfilePicEdit(View view)
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //checking if permission for gallery has been granted or not
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getPhoto();
        }
    }

    private void updateImage()
    {
        mDatabase.child("users").child(currentUser.getUid()).child("hasImage").setValue(1);

        // Code for showing progressDialog while uploading
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        StorageReference Ref = storageReference.child("users").child(currentUser.getUid()).child("image");

        Ref.putFile(selectedImage).addOnSuccessListener(
                new OnSuccessListener<UploadTask.TaskSnapshot>() {

                    @Override
                    public void onSuccess(
                            UploadTask.TaskSnapshot taskSnapshot)
                    {

                        // Image uploaded successfully
                        // Dismiss dialog
                        progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();


                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(
                        new OnProgressListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onProgress(
                                    UploadTask.TaskSnapshot taskSnapshot)
                            {
                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int)progress + "%");
                            }
                        });
    }

    public void submitEditProfile(View view)
    {
        if(!(newUsername.getText().toString().isEmpty()))
        {
            mDatabase.child("users").child(currentUser.getUid()).child("user_name").setValue(newUsername.getText().toString());
        }

        if (selectedImage != null)
        {
            updateImage();
        }

        Toast.makeText(EditProfile.this,"Profile edit completed",Toast.LENGTH_SHORT).show();

        Intent goToMainActivity = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(goToMainActivity);

    }

    public void removeProfilePic(View view)
    {
        selectedImage=null;
        mDatabase.child("users").child(currentUser.getUid()).child("hasImage").setValue(0);
        imageView3.setImageResource(R.drawable.profilepic);
        removePic.setVisibility(View.INVISIBLE);
        removePic.setEnabled(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        frbAuth = FirebaseAuth.getInstance();
        currentUser = frbAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        newUsername=findViewById(R.id.newUsername);
        imageView3=findViewById(R.id.imageView3);
        removePic=findViewById(R.id.removeProfilePic);

        mDatabase.child("users").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User Current_User=snapshot.getValue(User.class);

                Username=Current_User.user_name;
                hasImage=Current_User.hasImage;

                if(hasImage==1)
                {
                    removePic.setVisibility(View.VISIBLE);
                    removePic.setEnabled(true);
                    //getting user's current profile pic and putting it in imageview
                    StorageReference ref = storageReference.child("users").child(currentUser.getUid()).child("image");
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Glide.with(getApplicationContext()).load(uri.toString()).into(imageView3);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });
                }
                else
                {
                    imageView3.setImageResource(R.drawable.profilepic);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        selectedImage = data.getData();

        Glide.with(getApplicationContext()).asBitmap().load(selectedImage.toString()).into(imageView3); //putting image into image view

        //making remove pic button visible
        removePic.setVisibility(View.VISIBLE);
        removePic.setEnabled(true);
    }
}