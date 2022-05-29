package syg.gprj.ssygma_test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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


public class BrandsTypesController extends AppCompatActivity implements MyAdapter.RecyclerViewClickListener
{


    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Vehicles> list;
    ImageView topImage, botImage;
    String topName;

    MyAdapter.RecyclerViewClickListener listener;



//    private TextView idk;


    @Override
    protected void onCreate (Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.brand_types);


        Intent incomingIntent = getIntent();
        String brand = incomingIntent.getStringExtra("manuf");


        topName = "ic_" + brand.toLowerCase();

        recyclerView = findViewById(R.id.vehicleList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list= new ArrayList<>();
        myAdapter = new MyAdapter(this,list,this);


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


//    private void setOnClickListener ()
//    {
//        listener = new MyAdapter.RecyclerViewClickListener()
//        {
//            @Override
//            public void onCardClick(View v, int position) {
//
//            }
//
//            @Override
//            public void onClick(View v, int position)
//            {
//                Intent intent = new Intent(BrandsTypesController.this, SelectedController.class);
//
//                intent.putExtra("manuf", list.get(position).getManufacturer());
//                intent.putExtra("make", list.get(position).getMake());
//                intent.putExtra("model", list.get(position).getModel());
//                intent.putExtra("price", list.get(position).getStart_price());
//                intent.putExtra("type", list.get(position).getType());
//            }
//        };
//    }

    @Override
    public void onCardClick(View v, int position)
    {
        Intent intent = new Intent(this, SelectedController.class);

        Long model = list.get(position).getModel();
        Long price = list.get(position).getStart_price();

        intent.putExtra("manuf", list.get(position).getManufacturer());
        intent.putExtra("make", list.get(position).getMake());
        intent.putExtra("model",model );
        intent.putExtra("price", price );
        intent.putExtra("type", list.get(position).getType());
        intent.putExtra("image", list.get(position).getVehicle_image());

        startActivity(intent);

    }

//
//    public void readData ()
//    {
//        reference = FirebaseDatabase.getInstance().getReference();
//        Query q1 = reference.child("vehicles").orderByChild("manufacturer").equalTo("audi");
//    }

}
