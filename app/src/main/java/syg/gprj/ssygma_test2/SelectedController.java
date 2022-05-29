package syg.gprj.ssygma_test2;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class SelectedController extends AppCompatActivity
{

    private ImageView selectedTopImage;

    private TextView selectedManu, selectedMake, selectedModel, selectedPrice, datePicker, selectedDirection, selectedBrStatus, selectedType;

    private RadioButton selectedStandard, selectedPremuim, selectedPickup, selectedDeliver;

    private Spinner colors_spinner;

    private Button selectedProceed;


    private Spinner checkSpinner;

    private final List<CheckableSpinnerAdapter.SpinnerItem<Options>> spinner_items = new ArrayList<>();
    private final Set<Options> selected_items = new HashSet<>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_car);


        final String[] selectableOptions = {
               "Fill Fuel", "WiFi Access"
        };

        checkSpinner = (Spinner) findViewById(R.id.options_spinner);

        ArrayList<Options> spList = new ArrayList<>();

        for (int x = 0; x < selectableOptions.length; x++)
        {
            Options options = new Options();
            options.setTitle(selectableOptions[x]);
            options.setSelected(false);
            spList.add(options);
        }

        String headerText = "Click an option";

        for (Options o : spList)
        {
            spinner_items.add(new CheckableSpinnerAdapter.SpinnerItem<>(o, o.getTitle()));
        }

        CheckableSpinnerAdapter spinnerAdapter = new CheckableSpinnerAdapter(this,headerText,spinner_items,selected_items);
        checkSpinner.setAdapter(spinnerAdapter);






        Intent incomingIntent = getIntent();

        String manuf= incomingIntent.getStringExtra("manuf");
        String make= incomingIntent.getStringExtra("make");
        Long model= incomingIntent.getLongExtra("model",0);
        Long price= incomingIntent.getLongExtra("price",0);
        String type= incomingIntent.getStringExtra("type");
        String image = incomingIntent.getStringExtra("image");




        selectedTopImage = (ImageView) findViewById(R.id.selected_top_vehicle_image);

        selectedManu = (TextView) findViewById(R.id.selected_top_vehicle_manu);
        selectedMake = (TextView) findViewById(R.id.selected_top_vehicle_make);
        selectedModel = (TextView) findViewById(R.id.selected_top_vehicle_model); 
        selectedPrice = (TextView) findViewById(R.id.selected_top_vehicle_price); 
        selectedDirection = (TextView) findViewById(R.id.selected_directions_btn); 
        selectedBrStatus = (TextView) findViewById(R.id.selected_branch_status);
        datePicker = (TextView) findViewById(R.id.selected_pickupreturn_selector);
        selectedType = (TextView) findViewById(R.id.selected_top_vehicle_type);

        selectedStandard = (RadioButton) findViewById(R.id.selected_standard_rb);
        selectedPremuim = (RadioButton) findViewById(R.id.selected_premuim_rb);
        selectedPickup = (RadioButton) findViewById(R.id.selected_pickup_rb);
        selectedDeliver = (RadioButton) findViewById(R.id.selected_deliver_rb);

        selectedProceed = (Button) findViewById(R.id.selected_proceed_btn);




        String m2 = Long.toString(model);


        String p2 = Long.toString(price);



        selectedManu.setText(manuf.substring(0,1).toUpperCase() + manuf.substring(1));
        selectedMake.setText(" " +make.substring(0,1).toUpperCase() + make.substring(1) + " ");
        selectedModel.setText(m2);
        selectedPrice.setText(p2 + " JOD");
        selectedType.setText(type);

        Picasso.get()
                .load(image)
                .into(selectedTopImage);

//        selectedTopImage.setMaxHeight(198);

        String city;

        selectedDirection.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String city = "irbid";
                switch (city)
                {
                    case "amman":
                        city = "32.024752,35.856630";
                        openMap(city);
                        break;

                    case "irbid":
                        city = "32.538323,35.852006";
                        openMap(city);
                        break;

                    case "jarash":
                        city = "32.275742,35.902042";
                        openMap(city);
                        break;
                }



            }
        });




        colors_spinner = (Spinner) findViewById(R.id.selected_colors_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.colors_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colors_spinner.setAdapter(adapter);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();


//        Long today = MaterialDatePicker.todayInUtcMilliseconds();
//        Long month = MaterialDatePicker.thisMonthInUtcMilliseconds();



        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select Pickup & Return Date");
        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();


        datePicker.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>()
        {
            @Override
            public void onPositiveButtonClick(Pair<Long, Long> selection)
            {


                Long startDate = selection.first;
                Long endDate = selection.second;

                SimpleDateFormat simpleFormat = new SimpleDateFormat("dd MM yyyy");

                String rent_Date = simpleFormat.format(startDate);
                String return_Date = simpleFormat.format(endDate);

                long msDiff =endDate - startDate;
                long daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff);


                datePicker.setText("selected " + materialDatePicker.getHeaderText());
//                datePicker.setText("selected " + materialDatePicker.getHeaderText());
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
}
