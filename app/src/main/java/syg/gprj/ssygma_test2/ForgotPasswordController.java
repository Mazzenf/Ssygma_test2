package syg.gprj.ssygma_test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordController extends AppCompatActivity
{


    FirebaseAuth auth;

    private EditText forgot_email;
    private Button forgot_confirmbtn;
    private ImageView forgotBack;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        forgot_email = (EditText) findViewById(R.id.forgot_email);
        forgot_confirmbtn = (Button) findViewById(R.id.forgot_confirmbtn);

        forgotBack = (ImageView) findViewById(R.id.forgotBack);

        forgotBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(ForgotPasswordController.this, SignInController.class);
            }
        });


        auth = FirebaseAuth.getInstance();

        forgot_confirmbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                resetPassword();
            }
        });


    }

    private void resetPassword()
    {
        String email = forgot_email.getText().toString();

        if (email.isEmpty())
        {
            forgot_email.setError("Email Is Required");
            forgot_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            forgot_email.setError("Please Enter A Valid Email");
            forgot_email.requestFocus();
            return;
        }


        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(ForgotPasswordController.this, "Check your email to reset your password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ForgotPasswordController.this, "Try again something wrong happened", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


}


