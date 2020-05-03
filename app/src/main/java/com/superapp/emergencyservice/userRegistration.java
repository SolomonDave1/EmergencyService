package com.superapp.emergencyservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.util.UUID;

public class userRegistration extends AppCompatActivity {

    TextView exit;
    TextView create;
    public WebView view;
    Button Loginme;

    private static final int STORAGE_PERMISSION_CODE= 4655;
    private int PICK_IMAGE_RESULT=1;
    private Uri filepath;
    private FirebaseAuth firebaseAuth;

    private EditText etUserName;
    private EditText etUserEmail;
    private EditText etUserPassword;
    private EditText etUserContact;
    private EditText etUserAddress;
    private EditText etPersonToCall;
    private EditText etPersonToCallContact;
    private ImageView imageView;
    private Bitmap bitmap;
    ProgressBar progressBar;
    Button btRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        firebaseAuth = FirebaseAuth.getInstance();

        storagePermission();
        setupUIviews();

        getSupportActionBar().setTitle("Fill the form to register");

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (authenticate()){
                   // Upload user data to the database
              String userName = etUserName.getText().toString().trim();
              String userEmail = etUserEmail.getText().toString().trim();
              String userPassword = etUserPassword.getText().toString().trim();
              String userContact = etUserContact.getText().toString().trim();
              String userAddress = etUserAddress.getText().toString().trim();
              String userPerson_to_call = etPersonToCall.getText().toString().trim();
              String userPerson_to_callContact = etPersonToCallContact.getText().toString().trim();


firebaseAuth.createUserWithEmailAndPassword(userEmail,userPassword)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(userRegistration.this,"Registration Successful", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(userRegistration.this,"Registration Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });


               }
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null){
            // Handle already signed in user.
        }

    }





    private Boolean authenticate() {
        Boolean result = false;
        String name = etUserName.getText().toString().trim();
        String email = etUserEmail.getText().toString().trim();
        String password = etUserPassword.getText().toString().trim();
        String contact = etUserContact.getText().toString().trim();
        String address = etUserAddress.getText().toString().trim();
        String next_person_to_call = etPersonToCall.getText().toString().trim();
        String next_person_to_call_contact = etPersonToCallContact.getText().toString().trim();


        if (email.isEmpty() && contact.isEmpty() && address.isEmpty() && next_person_to_call.isEmpty() && next_person_to_call_contact.isEmpty() && name.isEmpty()) {
            Toast.makeText(this, "ENTER ALL DETAILS ", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }



    private void setupUIviews(){

        etUserName = findViewById(R.id.edFullName);
        etUserEmail = findViewById(R.id.edEmail);
        etUserPassword = findViewById(R.id.etUserPassword);                                                                  // Assigning the UI components
        etUserContact = findViewById(R.id.edContact);                            // to their respective java objects
        etUserAddress = findViewById(R.id.edAddress);
        etPersonToCall = findViewById(R.id.edPersonToContact);
        etPersonToCallContact = findViewById(R.id.edPersonToContact_no);
        imageView= findViewById(R.id.imageView2);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btRegister =(Button) findViewById(R.id.btRegister);

    }


    private void storagePermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){              // Obtaining storage permissions
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
            }
        }
    }



    public void selectImage(View view){          //Method to select
        showFileChooser();                      //user image
    }


    private void showFileChooser(){
        Intent intent=new Intent();                                                                   //Implementation of
        intent.setType("image/*");                                                                   // image selector
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Image"),PICK_IMAGE_RESULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_RESULT && data != null && data.getData() != null){
            filepath=data.getData();
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                imageView.setImageBitmap(bitmap);
            }catch (Exception ex){

            }
        }
    }

}
