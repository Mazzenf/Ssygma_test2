package syg.gprj.ssygma_test2;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderController extends AppCompatActivity
{

    private LinearLayout optionsLinear;
    private LinearLayout requiredDocs;

    @Override
    public void onCreate (Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.order);


        optionsLinear = (LinearLayout) findViewById(R.id.optionsLinear);

        requiredDocs = (LinearLayout) findViewById(R.id.order_required_documents);


        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myview = inflater.inflate(R.layout.layout_options,null);
        TextView optionText = (TextView) myview.findViewById(R.id.option1);
        TextView optionPrice = (TextView) myview.findViewById(R.id.option1Price);

        optionText.setText("Fill Fuel");
        optionPrice.setText("20 JOD");


        optionsLinear.addView(myview);

        View myview2 = inflater.inflate(R.layout.layout_options,null);
        TextView optionText2 = (TextView) myview2.findViewById(R.id.option1);
        TextView optionPrice2 = (TextView) myview2.findViewById(R.id.option1Price);

        optionText2.setText("WiFi Access");
        optionPrice2.setText("5 JOD");
        optionsLinear.addView(myview2);





        requiredDocs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Dialog mDialog = new Dialog(OrderController.this);
                mDialog.setContentView(R.layout.docs_layout);
                mDialog.show();
            }
        });

    }
}