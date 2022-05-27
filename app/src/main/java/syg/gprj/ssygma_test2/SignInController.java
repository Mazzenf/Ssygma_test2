package syg.gprj.ssygma_test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInController extends AppCompatActivity implements View.OnClickListener
{

    private FirebaseAuth mAuth;
    private EditText signin_email, signin_password;
    private TextView signup_btn1,forgot_btn;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);




        signin_email = (EditText) findViewById(R.id.signin_email_input);
        signin_password = (EditText) findViewById(R.id.signin_password_input);
        login_btn = (Button) findViewById(R.id.login_btn);
        signup_btn1 = (TextView) findViewById(R.id.signup_btn1);
        forgot_btn = (TextView) findViewById(R.id.forgot_btn);

        forgot_btn.setOnClickListener(this);
        login_btn.setOnClickListener(this);
        signup_btn1.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.forgot_btn:
                startActivity(new Intent(this,ForgotPasswordController.class));
                break;

            case R.id.signup_btn1:
                startActivity(new Intent(this,SignUpController.class));
                break;

            case R.id.login_btn:
                userLogin();
                break;
        }
    }

    private void userLogin()
    {
        String email = signin_email.getText().toString();
        String password = signin_password.getText().toString();

        if (email.isEmpty())
        {
            signin_email.setError("Email Is Required");
            signin_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signin_email.setError("Please Enter A Valid Email");
            signin_email.requestFocus();
            return;
        }

        if (password.isEmpty())
        {
            signin_password.setError("Password Is Required");
            signin_password.requestFocus();
            return;
        }

        if (password.length()<6)
        {
            signin_password.setError("Min Password Length Is 6 Characters");
            signin_password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    if (user.isEmailVerified())
                    {
                        startActivity(new Intent(SignInController.this,DiscoverController.class));

                    }
                    else
                    {
                        user.sendEmailVerification();
                        Toast.makeText(SignInController.this, "Check your email to verify your account", Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    Toast.makeText(SignInController.this, "Failed to login, Please check your credentials", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


}