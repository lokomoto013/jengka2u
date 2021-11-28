package com.example.jengka2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jengka2u.Prevalent.Prevalent;
import com.example.jengka2u.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class login_activity extends AppCompatActivity
{
    private EditText InputPhoneNumber, InputPassword;
    private Button Loginbutton;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe;
    private TextView AdminLink , NotadminLink;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        Loginbutton = (Button) findViewById(R.id.login_btn);
        InputPhoneNumber = (EditText) findViewById(R.id.login_phone_number_input);
        InputPassword = (EditText) findViewById(R.id.login_password_input);
        AdminLink = (TextView) findViewById(R.id.admin_panel_link);
        NotadminLink = (TextView) findViewById(R.id.not_admin_panel_link);
        loadingBar = new ProgressDialog(this);

        chkBoxRememberMe = (CheckBox) findViewById(R.id.remember_me_ckbx);
        Paper.init(this);

        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUser();
            }
        });

        AdminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Loginbutton.setText("Login Admin");
                AdminLink.setVisibility(View.INVISIBLE);
                NotadminLink.setVisibility(View.VISIBLE);
                parentDbName = "Admins";
            }
        });

        NotadminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Loginbutton.setText("Login");
                AdminLink.setVisibility(View.VISIBLE);
                NotadminLink.setVisibility(View.INVISIBLE);
                parentDbName = "Users";
            }
        });
    }

    private void loginUser()
    {
        String phone = InputPhoneNumber.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(phone))
        {
            Toast.makeText(this, "Please write your phone number", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please write your password", Toast.LENGTH_SHORT).show();
        }

        else

        {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait while we're checking the credentials");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone,password);

    }
}

    private void AllowAccessToAccount(String phone, String password)
    {
        if (chkBoxRememberMe.isChecked())
        {
            Paper.book().write(Prevalent.UserPhonekey, phone);
            Paper.book().write(Prevalent.UserPasswordkey, password);
        }

        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(parentDbName).child(phone).exists())
                {
                    Users usersdata = dataSnapshot.child(parentDbName).child(phone).getValue(Users.class);
                    if (usersdata.getPhone().equals(phone))
                    {
                        if (usersdata.getPassword().equals(password))
                        {
                           if (parentDbName.equals("Admins"))
                           {
                               Toast.makeText(login_activity.this, "Welcome admin, you are logged in successfully", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();
                               Intent intent = new Intent(login_activity.this , AddCategoryActivity.class);

                               startActivity(intent);
                           }

                           else if (parentDbName.equals("Users"))
                           {
                               Toast.makeText(login_activity.this, "Logged in Succesfully", Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();
                               Intent intent = new Intent(login_activity.this , HomeActivity.class);
                               Prevalent.currentOnlineuser = usersdata;
                               startActivity(intent);
                           }
                        }

                        else
                        {
                            Toast.makeText(login_activity.this, "password is incorrect", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                }
                else
                {
                    Toast.makeText(login_activity.this, "Account with this " + phone + " number do not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                          }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    }
