package syg.gprj.ssygma_test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessController extends AppCompatActivity
{

    private Button success_continue_btn;
    private TextView success_orders_btn;

    boolean pickup, insurance;

    String rent_date, return_date, fullName, color, image;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.successorder);

        Intent incomingIntent = getIntent();
        rent_date = incomingIntent.getStringExtra("rent");
        return_date = incomingIntent.getStringExtra("return");


        fullName= incomingIntent.getStringExtra("fullName");
        image= incomingIntent.getStringExtra("image");
        color= incomingIntent.getStringExtra("color");

        // 0 is pickup 1 is deliver
        pickup = incomingIntent.getBooleanExtra("pickup", true);
        // 0 is standard, 1 is premium
        insurance = incomingIntent.getBooleanExtra("insurance", true);


        success_continue_btn = (Button) findViewById(R.id.success_continue_btn);
        success_orders_btn = (TextView) findViewById(R.id.success_orders_btn);

        success_continue_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent int1 = new Intent(SuccessController.this, DiscoverController.class);
                startActivity(int1);
            }
        });

        success_orders_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                goNext();
            }
        });

    }

    public void goNext ()
    {
        Intent int1 = new Intent(SuccessController.this, OrderedController.class);
        System.out.println(rent_date + return_date);
        int1.putExtra("rent", rent_date);
        int1.putExtra("return", return_date);
        int1.putExtra("fullName", fullName);
        int1.putExtra("color", color);
        int1.putExtra("image", image);
        int1.putExtra("pickup", pickup);
        int1.putExtra("insurance", insurance);
        startActivity(int1);
    }


}
