package syg.gprj.ssygma_test2;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class OrderController extends AppCompatActivity
{


    DatabaseReference mDatabaseRef;
    FirebaseStorage storage;
    StorageReference mStoragref;
    FirebaseAuth mAuth;

    private String currentUserID,  numID, vName, optionsIntent, fullName, image;

    private ImageView cameraImage, backBtn;
    private Button cameraButton, submitInfoBtn, checkOut;

    private TextView vehicleName, vehiclePrice, vehicleDays, optionsTotal, orderToPay, subTotal;

    private EditText  idNumber;

    int insuPrice;
    String choosenColor, rent_date, return_date;


    private LinearLayout optionsLinear;
    private LinearLayout requiredDocs;
    private static final int requestImage = 1;

    private Uri imageUri;

    private boolean fuel, wifi, pickup, insurance;

    private int toPay, options1, totalOptions, iPrice, iDays, vPrice;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.order);

        Intent incomingIntent = getIntent();

        long days = incomingIntent.getLongExtra("days", 0);

        fullName = incomingIntent.getStringExtra("fullvName");
        vName = incomingIntent.getStringExtra("vName");
        String price = incomingIntent.getStringExtra("price");


        image = incomingIntent.getStringExtra("image");


        rent_date = incomingIntent.getStringExtra("rent_date");
        return_date = incomingIntent.getStringExtra("return_date");

        choosenColor = incomingIntent.getStringExtra("Color");

        fuel = incomingIntent.getBooleanExtra("fuel", true);
        wifi = incomingIntent.getBooleanExtra("wifi", true);

        // 0 is pickup 1 is deliver
        pickup = incomingIntent.getBooleanExtra("pickup", true);
        // 0 is standard, 1 is premium
        insurance = incomingIntent.getBooleanExtra("insurance", true);

        ////////////////////////////////////////////

        optionsLinear = (LinearLayout) findViewById(R.id.optionsLinear);

        requiredDocs = (LinearLayout) findViewById(R.id.order_required_documents);

        ///////////////////////////////////////////

        vehicleName = (TextView) findViewById(R.id.order_selected_vehicle_name);


        vehiclePrice = (TextView) findViewById(R.id.order_selected_vehicle_price);


        vehicleDays = (TextView) findViewById(R.id.order_selected_vehicle_days);

        optionsTotal = (TextView) findViewById(R.id.order_options);

        orderToPay = (TextView) findViewById(R.id.order_topay);

        checkOut = (Button) findViewById(R.id.order_checkout_btn);

        backBtn = (ImageView) findViewById(R.id.orderBackBtn);




        mStoragref = FirebaseStorage.getInstance().getReference("customer_id");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("customers");



        //////////////////////////////////////////
        iPrice = Integer.parseInt(price);
        iDays = Math.toIntExact(days);

        vPrice = iDays * iPrice;

        vehiclePrice.setText(vPrice + " JOD");

        /////////////////////////////////////////

        vehicleName.setText(fullName);
        vehicleDays.setText(iDays + " days");

        /////////////////////////////////////////////


        fillContent();


        requiredDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog mDialog = new Dialog(OrderController.this);
                mDialog.setContentView(R.layout.docs_layout);

                cameraImage = (ImageView) mDialog.findViewById(R.id.cameraView);
                cameraButton = (Button) mDialog.findViewById(R.id.cameraButton);
                idNumber = (EditText) mDialog.findViewById(R.id.IDInput);
                submitInfoBtn = (Button) mDialog.findViewById(R.id.docsConfirm);
                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cameraShow();
                    }
                });

                submitInfoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadPicture();
                    }
                });

                mDialog.show();
            }
        });


        checkOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goNext();
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void cameraShow()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestImage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestImage && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            imageUri = data.getData();

            Picasso.get().load(imageUri).fit().into(cameraImage);

            //cameraImage.setImageURI(imageUri);
        }
    }






    public void fillContent() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myview = inflater.inflate(R.layout.layout_options, null);
        TextView optionText = (TextView) myview.findViewById(R.id.option1);
        TextView optionPrice = (TextView) myview.findViewById(R.id.option1Price);

        if (!insurance) {
             insuPrice = iDays * 5;
            totalOptions += insuPrice;
            toPay += insuPrice;

            optionText.setText("Standard Insurance");
            optionPrice.setText(insuPrice + " JOD");


        } else {

             insuPrice = iDays * 15;

            optionText.setText("Premuim Insurance");
            optionPrice.setText(insuPrice + " JOD");

            totalOptions += insuPrice;
            toPay += insuPrice;
        }

        optionsLinear.addView(myview);


        View myview2 = inflater.inflate(R.layout.layout_options, null);
        TextView optionText2 = (TextView) myview2.findViewById(R.id.option1);
        TextView optionPrice2 = (TextView) myview2.findViewById(R.id.option1Price);

        if (!pickup) {

        } else {

            optionText2.setText("Deliver");
            optionPrice2.setText("10 JOD");
            optionsLinear.addView(myview2);
            totalOptions += 10;
            toPay += 10;
            options1 +=10;
        }


        View myview3 = inflater.inflate(R.layout.layout_options, null);
        TextView optionText3 = (TextView) myview3.findViewById(R.id.option1);
        TextView optionPrice3 = (TextView) myview3.findViewById(R.id.option1Price);

        if (wifi) {
            int wifiPrice = iDays * 3;

            optionText3.setText("WiFi Access");
            optionPrice3.setText(wifiPrice + " JOD");
            optionsLinear.addView(myview3);

            totalOptions += wifiPrice;
            toPay += wifiPrice;
            options1 += wifiPrice;
        }


        View myview4 = inflater.inflate(R.layout.layout_options, null);
        TextView optionText4 = (TextView) myview4.findViewById(R.id.option1);
        TextView optionPrice4 = (TextView) myview4.findViewById(R.id.option1Price);

        if (fuel) {
            optionText4.setText("Fill Fuel");
            optionPrice4.setText("20 JOD");
            optionsLinear.addView(myview4);

            totalOptions += 20;
            toPay += 20;
            options1 += 20;
        }

        optionsTotal.setText(totalOptions + " JOD");

        orderToPay.setText(toPay + vPrice + " JOD");

        optionsIntent = options1 + " JOD";

    }


    private String getFileExtension (Uri uri)
    {
        ContentResolver cr = getContentResolver();
        MimeTypeMap  mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadPicture()
    {
        numID = idNumber.getText().toString();
        if (numID.isEmpty())
        {
            Toast.makeText(this, "Please Fill in ID Number", Toast.LENGTH_SHORT).show();
        }
        else
        {
            if (imageUri != null)
            {
                StorageReference fileReference = mStoragref.child(System.currentTimeMillis()
                        + "." + getFileExtension(imageUri));

                fileReference.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                        {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                            {
                                Toast.makeText(OrderController.this, "Upload Successful", Toast.LENGTH_SHORT).show();


                                HashMap<String, Object> map = new HashMap<>();

                                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                                {
                                    @Override
                                    public void onSuccess(Uri uri)
                                    {
                                        final String downloadUrl = uri.toString();

                                        map.put("license_image", downloadUrl);
                                        map.put("ID_Number", numID);
                                        String uploadID = mDatabaseRef.push().getKey();
                                        mAuth = FirebaseAuth.getInstance();
                                        currentUserID = mAuth.getCurrentUser().getUid();
                                        mDatabaseRef.child(currentUserID).updateChildren(map);

                                    }
                                });


                            }
                        }).addOnFailureListener(new OnFailureListener()
                        {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(OrderController.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
            {
                Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void goNext ()
    {
        Intent goNext = new Intent(this, CheckoutController.class);
        goNext.putExtra("optionsTotal", optionsIntent);
        goNext.putExtra("insurancePrice",insuPrice+" JOD");
        goNext.putExtra("vehiclePrice",vehiclePrice.getText().toString());
        goNext.putExtra("Total",orderToPay.getText().toString());
        goNext.putExtra("Color",choosenColor);
        goNext.putExtra("VehicleName",vName);
        goNext.putExtra("fullName",fullName);
        goNext.putExtra("NN",numID);

        goNext.putExtra("pickup", pickup);
        goNext.putExtra("insurance", insurance);

        System.out.println(rent_date + return_date);
        goNext.putExtra("rent_date", rent_date);
        goNext.putExtra("return_date", return_date);


        goNext.putExtra("image", image);


        goNext.putExtra("currentUser", currentUserID);

        startActivity(goNext);

    }
}


