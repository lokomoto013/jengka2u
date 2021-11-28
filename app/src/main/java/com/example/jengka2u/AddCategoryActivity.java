package com.example.jengka2u;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AddCategoryActivity extends AppCompatActivity
{
    private ImageView tShirts , sportsTshirts , femaledresses , sweathers ;
    private ImageView bags , glasses , hats , shoes ;
    private ImageView headphones , laptops , watches , mobiles ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        tShirts = (ImageView) findViewById(R.id.t_shirts);
        sportsTshirts = (ImageView) findViewById(R.id.sports_t_shirts);
        femaledresses = (ImageView) findViewById(R.id.female_dresses);
        sweathers = (ImageView) findViewById(R.id.sweather);
        bags = (ImageView) findViewById(R.id.purses);
        glasses = (ImageView) findViewById(R.id.glasses);
        hats = (ImageView) findViewById(R.id.hats);
        shoes = (ImageView) findViewById(R.id.shoes);
        headphones = (ImageView) findViewById(R.id.headphones);
        laptops = (ImageView) findViewById(R.id.laptop);
        watches = (ImageView) findViewById(R.id.watches);
        mobiles = (ImageView) findViewById(R.id.mobiles);


        tShirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "tShirts");
                startActivity(intent);

            }

            });

         sportsTshirts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                    intent.putExtra("category" , "Sports Tshirts");
                    startActivity(intent);

                }


        });
        femaledresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "Female dresses");
                startActivity(intent);

            }


        });

        sweathers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "Sweathers");
                startActivity(intent);

            }


        });

        bags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "Bags");
                startActivity(intent);

            }


        });

        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "Glasses");
                startActivity(intent);

            }


        });

        hats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "Hats");
                startActivity(intent);

            }


        });

        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "Shoes");
                startActivity(intent);

            }


        });

        headphones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "Headphones");
                startActivity(intent);

            }


        });

        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "Laptops");
                startActivity(intent);

            }


        });

        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "Watches");
                startActivity(intent);

            }


        });

        mobiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(AddCategoryActivity.this , AdminaddnewProductactivity.class);
                intent.putExtra("category" , "Mobiles");
                startActivity(intent);

            }


        });
    }
}
