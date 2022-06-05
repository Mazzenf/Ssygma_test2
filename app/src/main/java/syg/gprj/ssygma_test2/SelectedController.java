package syg.gprj.ssygma_test2;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class SelectedController extends AppCompatActivity
{


    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference rootRef;

    private ImageView selectedTopImage;

    private TextView selectedManu, selectedMake, selectedModel, selectedPrice, datePicker, selectedDirection, selectedBrStatus, selectedType;

    private RadioButton selectedStandard, selectedPremuim, selectedPickup, selectedDeliver;

    private Spinner colors_spinner;

    private Button selectedProceed;

    private CheckBox cbFuel, cbWifi;

    private RadioGroup insuGroup, pickGroup;

    private String p2, user2, choosenColor, rent_date, return_date, city, image ;

    private long daysDiff;

    // 0 is standard, 1 is premium
    private boolean insuChecked;

    // 0 is pickup 1 is deliver
    private boolean pickChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_car);


        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user2 = user.getUid();
        }


        auth = FirebaseAuth.getInstance();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference uidRef = rootRef.child("customers").child(user2);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                city = dataSnapshot.child("city").getValue(String.class);
                System.out.println(city);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        uidRef.addValueEventListener(eventListener);

        System.out.println(city);


        Intent incomingIntent = getIntent();

        String manuf = incomingIntent.getStringExtra("manuf");
        String make = incomingIntent.getStringExtra("make");
        Long model = incomingIntent.getLongExtra("model", 0);
        Long price = incomingIntent.getLongExtra("price", 0);
        String type = incomingIntent.getStringExtra("type");
        image = incomingIntent.getStringExtra("image");


        selectedTopImage = (ImageView) findViewById(R.id.selected_top_vehicle_image);

        selectedManu = (TextView) findViewById(R.id.selected_top_vehicle_manu);
        selectedMake = (TextView) findViewById(R.id.selected_top_vehicle_make);
        selectedModel = (TextView) findViewById(R.id.selected_top_vehicle_model);
        selectedPrice = (TextView) findViewById(R.id.selected_top_vehicle_price);
        selectedDirection = (TextView) findViewById(R.id.selected_directions_btn);
        selectedBrStatus = (TextView) findViewById(R.id.selected_branch_status);
        datePicker = (TextView) findViewById(R.id.selected_pickupreturn_selector);
        selectedType = (TextView) findViewById(R.id.selected_top_vehicle_type);

        insuGroup = (RadioGroup) findViewById(R.id.insuranceGroup);
        pickGroup = (RadioGroup) findViewById(R.id.pickupGroup);

        selectedStandard = (RadioButton) findViewById(R.id.selected_standard_rb);
        selectedPremuim = (RadioButton) findViewById(R.id.selected_premuim_rb);
        selectedPickup = (RadioButton) findViewById(R.id.selected_pickup_rb);
        selectedDeliver = (RadioButton) findViewById(R.id.selected_deliver_rb);

        selectedProceed = (Button) findViewById(R.id.selected_proceed_btn);

        cbFuel = (CheckBox) findViewById(R.id.cb_fuel);
        cbWifi = (CheckBox) findViewById(R.id.cb_wifi);


        checkTime();

        String m2 = Long.toString(model);
        p2 = Long.toString(price);

        selectedManu.setText(manuf.substring(0, 1).toUpperCase() + manuf.substring(1));
        selectedMake.setText(" " + make.substring(0, 1).toUpperCase() + make.substring(1) + " ");
        selectedModel.setText(m2);
        selectedPrice.setText(p2 + " JOD");
        selectedType.setText(type);

        Picasso.get()
                .load(image)
                .into(selectedTopImage);




        selectedDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println(city);

                if (Objects.equals(city, "Amman")) {
                    city = "32.024752,35.856630";
                    openMap(city);
                } else if (Objects.equals(city, "Irbid")) {
                    city = "32.538323,35.852006";
                    openMap(city);
                } else if (Objects.equals(city, "Jarash")) {
                    city = "32.275742,35.902042";
                    openMap(city);
                }


            }
        });


        colors_spinner = (Spinner) findViewById(R.id.selected_colors_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.colors_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colors_spinner.setAdapter(adapter);

        choosenColor = colors_spinner.getSelectedItem().toString();


        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();

        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Pickup & Return Date");
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();


        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection) {


                Long startDate = selection.first;
                Long endDate = selection.second;

                SimpleDateFormat simpleFormat = new SimpleDateFormat("dd MM yyyy");

                rent_date = simpleFormat.format(startDate);
                return_date = simpleFormat.format(endDate);

                long msDiff = endDate - startDate;
                daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);


                datePicker.setText("selected " + materialDatePicker.getHeaderText());
//                datePicker.setText("selected " + materialDatePicker.getHeaderText());
            }


        });

        insuGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == selectedStandard.getId()) {
                    insuChecked = false;
                } else if (i == selectedPremuim.getId()) {
                    insuChecked = true;
                }
            }
        });

        pickGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == selectedPickup.getId()) {
                    pickChecked = false;
                } else if (i == selectedDeliver.getId()) {
                    pickChecked = true;
                }
            }
        });


        selectedProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proceed();
            }
        });

    }

    public void openMap (String city)
    {
        Uri gmIntentUri = Uri.parse("geo:" + city + "?q=" + Uri.parse(city + "(Sygma Car Rental)"));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void checkTime ()
    {
        Calendar now = Calendar.getInstance();
        int s = now.get(Calendar.HOUR_OF_DAY);

        if (s <= 8 || s >= 23)
        {
            selectedBrStatus.setText("Closed now");
            selectedBrStatus.setTextColor(Color.parseColor("#B8051A"));
        }



    }


    public void proceed ()
    {
        Intent goNext = new Intent(this, OrderController.class);
        goNext.putExtra("fullvName", selectedManu.getText().toString() + selectedMake.getText().toString() + selectedModel.getText().toString());
        goNext.putExtra("vName", selectedManu.getText().toString() + selectedMake.getText().toString());
        goNext.putExtra("price", p2);

        goNext.putExtra("fuel", cbFuel.isChecked());
        goNext.putExtra("wifi", cbWifi.isChecked());

        goNext.putExtra("pickup", pickChecked);
        goNext.putExtra("insurance", insuChecked);

        goNext.putExtra("days", daysDiff);

        goNext.putExtra("Color", choosenColor);

        goNext.putExtra("image", image);


        goNext.putExtra("rent_date", rent_date);
        goNext.putExtra("return_date", return_date);



        startActivity(goNext);
    }

}
