package syg.gprj.ssygma_test2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BrandsTypesController extends AppCompatActivity
{


    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Vehicles> list;
    ImageView topImage, botImage;
    String topName;



//    private TextView idk;


    @Override
    protected void onCreate (Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.brand_types);


        Intent incomingIntent = getIntent();
        String brand = incomingIntent.getStringExtra("manuf");

        topName = "ic_" + brand;

        recyclerView = findViewById(R.id.vehicleList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list= new ArrayList<>();
        myAdapter = new MyAdapter(this,list);


        database = FirebaseDatabase.getInstance().getReference();

        topImage = (ImageView) findViewById(R.id.brands_types_top_image);
        topImage.setImageResource(getResources().getIdentifier(topName,"drawable","syg.gprj.ssygma_test2"));

        Query q1 = database.child("vehicles").orderByChild("manufacturer").equalTo(brand);





        recyclerView.setAdapter(myAdapter);

        q1.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Vehicles vehicle1 = dataSnapshot.getValue(Vehicles.class);
                    list.add(vehicle1);
                }
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(BrandsTypesController.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });








    }


//
//    public void readData ()
//    {
//        reference = FirebaseDatabase.getInstance().getReference();
//        Query q1 = reference.child("vehicles").orderByChild("manufacturer").equalTo("audi");
//    }

}
