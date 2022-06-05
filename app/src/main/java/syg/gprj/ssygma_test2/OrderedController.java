package syg.gprj.ssygma_test2;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Objects;


public class OrderedController extends AppCompatActivity
{

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference rootRef;
    private TextView order_directions_btn, order_choosen_insurance, order_choosen_method, order_status, shopStatus, order_choosen_dates, order_choosen_color, order_vehicle_name;
    private Button order_action_btn;

    private ImageView order_vehicle_image;

    private String city, user2, rent_date, return_date, fullName, color, image;
    boolean pickup, insurance;


    @Override
    public void onCreate (Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.ordered_deliver);

        Intent incomingIntent = getIntent();
        rent_date= incomingIntent.getStringExtra("rent");
        return_date= incomingIntent.getStringExtra("return");


        fullName= incomingIntent.getStringExtra("fullName");
        image= incomingIntent.getStringExtra("image");
        color= incomingIntent.getStringExtra("color");

        // 0 is pickup 1 is deliver
        pickup = incomingIntent.getBooleanExtra("pickup", true);
        // 0 is standard, 1 is premium
        insurance = incomingIntent.getBooleanExtra("insurance", true);




        order_choosen_color = (TextView) findViewById(R.id.order_choosen_color);
        order_choosen_dates = (TextView) findViewById(R.id.order_choosen_dates);
        order_choosen_insurance = (TextView) findViewById(R.id.order_choosen_insurance);
        order_status = (TextView) findViewById(R.id.order_status);
        order_directions_btn = (TextView) findViewById(R.id.order_directions_btn);
        order_choosen_method = (TextView) findViewById(R.id.order_choosen_method);
        shopStatus = (TextView) findViewById(R.id.shopStatus);
        order_vehicle_name = (TextView) findViewById(R.id.order_vehicle_name);

        order_action_btn = (Button) findViewById(R.id.order_action_btn);

        order_vehicle_image = (ImageView) findViewById(R.id.order_vehicle_image);




        order_vehicle_name.setText(fullName);

        order_choosen_color.setText(color);

        if (insurance)
        {
            order_choosen_insurance.setText("Premium Insurance");
        }
        else
        {
            order_choosen_insurance.setText("Standard Insurance");
        }

        if (pickup)
        {
            order_choosen_method.setText("Home Delivery");
            order_status.setText("Vehicle is en ruote to delivery");
            order_action_btn.setText("Track Vehicle");
        }
        else
        {
            order_choosen_method.setText("Pickup From Branch");
            order_status.setText("Vehicle is ready for pickup at branch");
            order_action_btn.setText("Contact Support");
        }

        order_choosen_dates.setText(rent_date + " " + return_date);

        Picasso.get()
                .load(image)
                .into(order_vehicle_image);


        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
        {
            user2 = user.getUid();
        }


        auth = FirebaseAuth.getInstance();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("customers").child(user2);

        ValueEventListener eventListener = new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                city = dataSnapshot.child("city").getValue(String.class);
                System.out.println(city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        };
        uidRef.addValueEventListener(eventListener);


        shopStatus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                checkTime();
            }
        });

        order_directions_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                System.out.println(city);

                if (Objects.equals(city, "Amman"))
                {
                    city = "32.024752,35.856630";
                    openMap(city);
                }
                else if (Objects.equals(city, "Irbid"))
                {
                    city = "32.538323,35.852006";
                    openMap(city);
                }
                else if (Objects.equals(city, "Jarash"))
                {
                    city = "32.275742,35.902042";
                    openMap(city);
                }





            }
        });




    }


    public void checkTime ()
    {
        Calendar now = Calendar.getInstance();
        int s = now.get(Calendar.HOUR_OF_DAY);

        if (s <= 8 || s >= 23)
        {
            shopStatus.setText("Closed now");
            shopStatus.setTextColor(Color.parseColor("#B8051A"));
        }



    }



    public void openMap (String city)
    {
        Uri gmIntentUri = Uri.parse("geo:" + city + "?q=" + Uri.parse(city + "(Sygma Car Rental)"));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

}