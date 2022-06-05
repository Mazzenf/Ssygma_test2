package syg.gprj.ssygma_test2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DocsController extends AppCompatActivity
{

    private ImageView cameraImage;
    private Button cameraButton;
    private static final int requestcamera = 12;


    @Override
    public void onCreate (Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.docs_layout);


        cameraImage = (ImageView) findViewById(R.id.cameraView);
        cameraButton = (Button) findViewById(R.id.cameraButton);




        cameraButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                if (ContextCompat.checkSelfPermission(DocsController.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
//                {
//                    ActivityCompat.requestPermissions(DocsController.this,new String[]{Manifest.permission.CAMERA}, 101);
//                    takePicture();
//                }


            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==requestcamera)
        {
            assert data != null;
            Bitmap imgbitMap = (Bitmap) data.getExtras().get("data");
            cameraImage.setImageBitmap(imgbitMap);
        }

    }
}
