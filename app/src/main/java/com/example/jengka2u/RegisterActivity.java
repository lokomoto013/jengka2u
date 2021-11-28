package com.example.jengka2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity
{   private Button CreateAccountButton;
    private EditText InputName, InputPhoneNumber, InputPassword;
    private ProgressDialog loadingBar;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        CreateAccountButton = (Button) findViewById(R.id.register_btn);
        InputName = (EditText) findViewById(R.id.register_username_input);
        InputPhoneNumber = (EditText) findViewById(R.id.register_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.register_password_input);
        loadingBar = new ProgressDialog(this);

    CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             CreateAccount();
            }
        });
    }

    private void CreateAccount()
    {
        String name = InputName.getText().toString();
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please write your name", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }

        else

        {
         loadingBar.setTitle("Create Account");
         loadingBar.setMessage("Please wait while we're checking the credentials");
         loadingBar.setCanceledOnTouchOutside(false);
         loadingBar.show();

         Validatephonenumber(name,phone,password);
        }
    }

    private void Validatephonenumber(String name, String phone, String password)
    {
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
              if (!(dataSnapshot.child("Users").child(phone).exists()))
              {
                  HashMap<String , Object> userdataMap = new HashMap<>();
                  userdataMap.put("phone",phone);
                  userdataMap.put("password",password);
                  userdataMap.put("name",name);

                  Rootref.child("Users").child(phone).updateChildren(userdataMap)
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task)
                              {
                              if (task.isSuccessful())
                              {
                                  Toast.makeText(RegisterActivity.this, "Congratulations, you have successfully create you account", Toast.LENGTH_SHORT).show();
                                  loadingBar.dismiss();
                                  Intent intent = new Intent(RegisterActivity.this , login_activity.class);
                                  startActivity(intent);
                              }

                              else

                              {
                                  loadingBar.dismiss();
                                  Toast.makeText(RegisterActivity.this, "Network Error, please try again.", Toast.LENGTH_SHORT).show();
                              }
                              }
                          });
              }

              else
              {
                  Toast.makeText(RegisterActivity.this, "This " + phone + " already exists", Toast.LENGTH_SHORT).show();
                  loadingBar.dismiss();
                  Toast.makeText(RegisterActivity.this, "Please try again with another phone number", Toast.LENGTH_SHORT).show();
                  Intent intent = new Intent(RegisterActivity.this , MainActivity.class);
                  startActivity(intent);

              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
