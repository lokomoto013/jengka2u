package com.example.jengka2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jengka2u.Prevalent.Prevalent;
import com.example.jengka2u.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity
{
    private Button welcomebutton, joinnowbutton;
    private ProgressDialog loadingBar;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinnowbutton = (Button) findViewById(R.id.join_now_butn);
        welcomebutton = (Button) findViewById(R.id.welcome_join_now_btn);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);

        joinnowbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this , login_activity.class);
                startActivity(intent);
            }
        });
        welcomebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this , RegisterActivity.class);
                startActivity(intent);
            }
        });

        String UserPhonekey = Paper.book().read(Prevalent.UserPhonekey);
        String UserPasswordkey = Paper.book().read(Prevalent.UserPasswordkey);

        if(UserPhonekey != "" && UserPasswordkey != "")
        {
            if (!TextUtils.isEmpty(UserPhonekey) && !TextUtils.isEmpty(UserPasswordkey))
            {
                AllowAccess(UserPhonekey , UserPasswordkey);
                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait...");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();
            }
        }
    }

    private void AllowAccess(final String phone, final String password)
    {
        final DatabaseReference Rootref;
        Rootref = FirebaseDatabase.getInstance().getReference();

        Rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child("Users").child(phone).exists())
                {
                    Users usersdata = dataSnapshot.child("Users").child(phone).getValue(Users.class);
                    if (usersdata.getPhone().equals(phone))
                    {
                        if (usersdata.getPassword().equals(password))
                        {
                            Toast.makeText(MainActivity.this, "You already logged in :)", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this , HomeActivity.class);
                            Prevalent.currentOnlineuser = usersdata;
                            startActivity(intent);
                        }

                        else
                        {
                            Toast.makeText(MainActivity.this, "password is incorrect", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Account with this " + phone + " number do not exist", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
