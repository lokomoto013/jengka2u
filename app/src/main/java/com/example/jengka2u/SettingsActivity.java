package com.example.jengka2u;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jengka2u.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private CircleImageView profileImageView;
    private EditText fullnameedittext , UserPhoneEdittext , addressEditText;
    private TextView profilechangeTxtbtn , closeTxtbtn , saveTxtbtn;

    private Uri imageuri;
    private String myUrl = "";
    private StorageReference storageprofilepicRef;
    private String checker = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profileImageView = (CircleImageView) findViewById(R.id.settings_profile_image);
        fullnameedittext = (EditText) findViewById(R.id.settings_full_name);
        UserPhoneEdittext = (EditText) findViewById(R.id.settings_phone_number);
        addressEditText = (EditText) findViewById(R.id.settings_address);
        profilechangeTxtbtn = (TextView) findViewById(R.id.profile_change_image_btn);
        closeTxtbtn = (TextView) findViewById(R.id.close_settings_btn);
        saveTxtbtn = (TextView) findViewById(R.id.update_settings_btn);

        userInfoDisplay(profileImageView , fullnameedittext , UserPhoneEdittext , addressEditText);

        closeTxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        saveTxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (checker.equals("clicked"))

                {
                    userInfoSaved();
                }
      //          else
                {
      //              updateonlyUserInfo();
                }
            }
        });

        profilechangeTxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             checker = "clicked";

                CropImage.activity(imageuri)
                        .setAspectRatio(1,1)
                        .start(SettingsActivity.this);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageuri = result.getUri();
            profileImageView.setImageURI(imageuri);
        }
        else
        {
            Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SettingsActivity.this , SettingsActivity.class));
        }
    }

    private void userInfoSaved()
    {

    }

    private void userInfoDisplay(CircleImageView profileImageView, EditText fullnameedittext, EditText userPhoneEdittext, EditText addressEditText)
    {
        DatabaseReference Usersref = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineuser.getPhone());

        Usersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists())
                {
                  if (dataSnapshot.child("image").exists())
                  {
                      String image = dataSnapshot.child("image").getValue().toString();
                      String name = dataSnapshot.child("name").getValue().toString();
                      String phone = dataSnapshot.child("phone").getValue().toString();
                      String address = dataSnapshot.child("address").getValue().toString();

                      Picasso.get().load(image).into(profileImageView);
                      fullnameedittext.setText(name);
                      UserPhoneEdittext.setText(phone);
                      addressEditText.setText(address);

                  }
                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
