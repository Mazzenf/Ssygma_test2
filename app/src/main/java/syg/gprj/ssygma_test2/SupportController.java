package syg.gprj.ssygma_test2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SupportController extends AppCompatActivity
{

    Button msgBtn;
    ImageView backBtn;


    @Override
    public void onCreate (Bundle savedInstance)
    {

        super.onCreate(savedInstance);
        setContentView(R.layout.contact_support);

        msgBtn = (Button) findViewById(R.id.support_send_btn);
        backBtn = (ImageView) findViewById(R.id.supportBackBtn);

        msgBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(SupportController.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

    }
}