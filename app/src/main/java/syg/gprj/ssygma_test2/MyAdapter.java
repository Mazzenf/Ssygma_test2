package syg.gprj.ssygma_test2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{

    private RecyclerViewClickListener mlistener;

    Context context;

    ArrayList<Vehicles> list;



    public MyAdapter(Context context, ArrayList<Vehicles> list, RecyclerViewClickListener mlistener)
    {
        this.context = context;
        this.list = list;
        this.mlistener=mlistener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.vehicle_item,parent,false);
        return new MyViewHolder(v,mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Vehicles vehicles = list.get(position);
        String make = vehicles.getMake().substring(0,1).toUpperCase() + vehicles.getMake().substring(1);
        holder.v_make.setText(make);

        Long m1 = vehicles.getModel();
        String m2 = Long.toString(m1);

        Long p1 = vehicles.getStart_price();
        String p2 = Long.toString(p1);

        Long c1 = vehicles.getCapacity();
        String c2 = Long.toString(c1);

        holder.v_model.setText(m2);
        holder.v_price.setText(p2);
        holder.v_capacity.setText(c2);

        Picasso.get()
                .load(vehicles.getVehicle_image())
                .fit()
                .into(holder.carView);

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private RecyclerViewClickListener listener;

        TextView v_model, v_make, v_capacity, v_price;
        ImageView carView;

        public MyViewHolder(@NonNull View itemView, RecyclerViewClickListener listener)
        {
            super(itemView);

            this.listener = listener;


            v_model = itemView.findViewById(R.id.brands_types_vehicle_model);
            v_make = itemView.findViewById(R.id.brands_types_vehicle_make);
            v_capacity = itemView.findViewById(R.id.brands_types_vehicle_capacity);
            v_price = itemView.findViewById(R.id.brands_types_vehicle_price);

            carView = itemView.findViewById(R.id.brands_types_vehicle_image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            listener.onCardClick(view, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener
    {
        void onCardClick (View v, int position);
    }

}
