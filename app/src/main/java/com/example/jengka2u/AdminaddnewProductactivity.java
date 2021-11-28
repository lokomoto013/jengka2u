package com.example.jengka2u;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminaddnewProductactivity extends AppCompatActivity {

    private String CategoryName , Description , Price , Pname, saveCurrentDate, saveCurrentTime;
    private Button AddnewproductButton;
    private ImageView AddProductImage;
    private EditText InputProductName , InputProductDescription , InputProductPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String ProductRandomKey, downloadimageURL;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaddnew_productactivity);



        CategoryName = getIntent().getExtras().get("category").toString();

        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Image");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");
        AddnewproductButton = (Button) findViewById(R.id.add_new_product);
        AddProductImage = (ImageView) findViewById(R.id.select_product_image);
        InputProductName = (EditText) findViewById(R.id.product_name);
        InputProductDescription = (EditText) findViewById(R.id.product_description);
        InputProductPrice = (EditText) findViewById(R.id.product_price);
        loadingBar = new ProgressDialog(this);

        AddProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
              OpenGallery();
            }
        });

        AddnewproductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ValidateProductData();
            }
        });
    }

    private void ValidateProductData()
    {
        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        Pname = InputProductName.getText().toString();

        if(ImageUri == null)
        {
            Toast.makeText(this, "Product Image is mandatory..", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please put the product description", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please put the product price", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Pname))
        {
            Toast.makeText(this, "Please put the product name", Toast.LENGTH_SHORT).show();
        }

        else
        {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation()
    {
        loadingBar.setTitle("Add new Product");
        loadingBar.setMessage("Please wait while we are adding the new product");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentdate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentdate.format(calendar.getTime());

        SimpleDateFormat currenttime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currenttime.format(calendar.getTime());

        ProductRandomKey = saveCurrentDate+saveCurrentTime;

        StorageReference filepath = ProductImagesRef.child(ImageUri.getLastPathSegment() +  ProductRandomKey + ".jpg");
        final UploadTask uploadTask = filepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
             String message = e.toString();
                Toast.makeText(AdminaddnewProductactivity.this, "Error" + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) 
            {
                Toast.makeText(AdminaddnewProductactivity.this, "Product Image uploaded successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadimageURL = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())


                        {
                            downloadimageURL = task.getResult().toString();

                            Toast.makeText(AdminaddnewProductactivity.this, "got the product image url successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfotoDatabase();
                        }

                    }
                });
                
            }
        });

    }

    private void SaveProductInfotoDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", ProductRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("image", downloadimageURL);
        productMap.put("category", CategoryName);
        productMap.put("price", Price);
        productMap.put("product_name", Pname);

        ProductRef.child(ProductRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if(task.isSuccessful())
                        {

                            Intent intent = new Intent(AdminaddnewProductactivity.this , AddCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminaddnewProductactivity.this, "Product is successfully added into the database", Toast.LENGTH_SHORT).show();
                        }

                        else

                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminaddnewProductactivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent , GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            AddProductImage.setImageURI(ImageUri);

        }

    }
}
