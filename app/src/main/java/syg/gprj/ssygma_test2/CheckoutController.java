package syg.gprj.ssygma_test2;

import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CheckoutController extends AppCompatActivity
{

    private DatabaseReference mDatabaseRef;

    private LinearLayout addressClick, expandAddress, detailsClick, expandDetails;

    private ImageView addressUpDown,detailsUpDown, backBtn;

    private String image, vName, vColor, vPrice, optionsPrice, insuPrice, totalPrice, NN, rent_date, return_date, AreaName, StreetName, BuildingNumber, currentUser, fullName;

    private TextView vehicleName, vehicleColor, vehiclePrice, allOptionsPrice, insurancePrice, allTotalPrice;

    private EditText areaName, streetName, buildingNumber;

    private RadioGroup paymentRG;

    private RadioButton ccRB, cashRB;

    private Button checkOutBtn;

    boolean pickup, insurance;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.checkout);

        Intent incomingIntent = getIntent();

        vName= incomingIntent.getStringExtra("VehicleName");
        fullName= incomingIntent.getStringExtra("fullName");
        vColor= incomingIntent.getStringExtra("Color");
        vPrice= incomingIntent.getStringExtra("vehiclePrice");
        optionsPrice= incomingIntent.getStringExtra("optionsTotal");
        insuPrice= incomingIntent.getStringExtra("insurancePrice");
        totalPrice= incomingIntent.getStringExtra("Total");

        NN = incomingIntent.getStringExtra("NN");


        rent_date = incomingIntent.getStringExtra("rent_date");
        return_date = incomingIntent.getStringExtra("return_date");


        image = incomingIntent.getStringExtra("image");


        currentUser = incomingIntent.getStringExtra("currentUser");

        // 0 is pickup 1 is deliver
        pickup = incomingIntent.getBooleanExtra("pickup", true);
        // 0 is standard, 1 is premium
        insurance = incomingIntent.getBooleanExtra("insurance", true);




        addressClick = (LinearLayout) findViewById(R.id.addressClick);
        expandAddress = (LinearLayout) findViewById(R.id.expandAddress);
        addressUpDown = (ImageView) findViewById(R.id.addressUpDown);

        detailsClick = (LinearLayout) findViewById(R.id.DetailsClick);
        expandDetails = (LinearLayout) findViewById(R.id.expandDetails);
        detailsUpDown = (ImageView) findViewById(R.id.detailsUpDown);

        //  vehicleName, vehicleColor, vehiclePrice, allOptionsPrice, insurancePrice, allTotalPrice;

        vehicleName = (TextView) findViewById(R.id.vName);
        vehicleColor = (TextView) findViewById(R.id.vColor);
        vehiclePrice = (TextView) findViewById(R.id.vPrice);
        allOptionsPrice = (TextView) findViewById(R.id.optionsPrice);
        insurancePrice = (TextView) findViewById(R.id.insuPrice);
        allTotalPrice = (TextView) findViewById(R.id.totalPrice);

        vehicleName.setText(vName);
        vehicleColor.setText(vColor);
        vehiclePrice.setText(vehiclePrice.getText() + " " + vPrice);
        allOptionsPrice.setText(allOptionsPrice.getText() + " " + optionsPrice);
        insurancePrice.setText(insurancePrice.getText() + " " + insuPrice);
        allTotalPrice.setText(allTotalPrice.getText() + " " + totalPrice);


        areaName = (EditText) findViewById(R.id.addAreaName);
        streetName = (EditText) findViewById(R.id.addStreetName);
        buildingNumber = (EditText) findViewById(R.id.addBuildingNum);

        backBtn = (ImageView) findViewById(R.id.checkoutBackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        paymentRG = (RadioGroup) findViewById(R.id.paymentRG);

        ccRB = (RadioButton) findViewById(R.id.ccRB);
        cashRB = (RadioButton) findViewById(R.id.cashRB);

        checkOutBtn = (Button) findViewById(R.id.checkOutBtn);




        addressClick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int v;
                //  v = (expandAddress.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;

                if  (expandAddress.getVisibility() == View.GONE)
                {
                    v = View.VISIBLE;
                    addressUpDown.setImageResource(getResources().getIdentifier("ic_arrow_up","drawable","syg.gprj.ssygma_test2"));
                    TransitionManager.beginDelayedTransition(addressClick, new AutoTransition());
                    expandAddress.setVisibility(v);
                }
                else
                {
                    v = View.GONE;
                    addressUpDown.setImageResource(getResources().getIdentifier("ic_arrow_down","drawable","syg.gprj.ssygma_test2"));
                    TransitionManager.beginDelayedTransition(addressClick, new AutoTransition());
                    expandAddress.setVisibility(v);
                }


            }
        });


        detailsClick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int v;
                //  v = (expandAddress.getVisibility() == View.GONE) ? View.VISIBLE: View.GONE;

                if  (expandDetails.getVisibility() == View.GONE)
                {
                    v = View.VISIBLE;
                    detailsUpDown.setImageResource(getResources().getIdentifier("ic_arrow_up","drawable","syg.gprj.ssygma_test2"));
                    TransitionManager.beginDelayedTransition(detailsClick, new AutoTransition());
                    expandDetails.setVisibility(v);
                }
                else
                {
                    v = View.GONE;
                    detailsUpDown.setImageResource(getResources().getIdentifier("ic_arrow_down","drawable","syg.gprj.ssygma_test2"));
                    TransitionManager.beginDelayedTransition(detailsClick, new AutoTransition());
                    expandDetails.setVisibility(v);
                }
            }
        });


        checkOutBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                proceed();
            }
        });

    }


    public void proceed ()
    {
        AreaName = areaName.getText().toString();
        StreetName = streetName.getText().toString();
        BuildingNumber = buildingNumber.getText().toString();

        if (BuildingNumber.isEmpty() || StreetName.isEmpty() || AreaName.isEmpty())
        {
            Toast.makeText(this, "Please Fill in the address", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> rentsFill = new HashMap<>();
            rentsFill.put("customer_ID", NN);
            rentsFill.put("rent_date", rent_date);
            rentsFill.put("return_date", return_date);
            rentsFill.put("total_price", totalPrice);
            rentsFill.put("vehicle_name", vName);
            rentsFill.put("rent_status", "pending");
            rentsFill.put("choosen_color", "black");

            mDatabaseRef = FirebaseDatabase.getInstance().getReference("rents");
            mDatabaseRef.push().updateChildren(rentsFill);


            HashMap<String, Object> addressFill = new HashMap<>();
            addressFill.put("Area_name", AreaName);
            addressFill.put("Street_name", StreetName);
            addressFill.put("Building_number", BuildingNumber);
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("customers");
            mDatabaseRef.child(currentUser).child("address").updateChildren(addressFill);

            Intent intent = new Intent(this, SuccessController.class);

            System.out.println(rent_date + return_date);
            intent.putExtra("rent", rent_date);
            intent.putExtra("return", return_date);
            intent.putExtra("fullName", fullName);
            intent.putExtra("color", vColor);

            intent.putExtra("pickup", pickup);
            intent.putExtra("insurance", insurance);

            intent.putExtra("image", image);
            startActivity(intent);

        }

    }

}
