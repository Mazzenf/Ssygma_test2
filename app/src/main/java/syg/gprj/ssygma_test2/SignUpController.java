package syg.gprj.ssygma_test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.DatePickerDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class SignUpController extends AppCompatActivity implements View.OnClickListener
{

    private FirebaseAuth mAuth;

    private EditText signup_fname, signup_lname, signup_email, signup_password, signup_phone;
    private TextView signup_bdate, signin_btn1;
    private Button signup_createbtn;
    private Spinner signup_city;
    private ImageView signupBack;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mAuth = FirebaseAuth.getInstance();


        signup_fname = (EditText) findViewById(R.id.signup_fname);
        signup_lname = (EditText) findViewById(R.id.signup_lname);
        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_password = (EditText) findViewById(R.id.signup_password);
        signup_phone = (EditText) findViewById(R.id.signup_phone);

        signup_bdate = (TextView) findViewById(R.id.signup_bdate);
        signin_btn1 = (TextView) findViewById(R.id.signin_btn1);

        signup_createbtn = (Button) findViewById(R.id.signup_createbtn);

        signup_city = (Spinner) findViewById(R.id.signup_city);

        signupBack = (ImageView) findViewById(R.id.signupBack);


        signupBack.setOnClickListener(this);
        signup_createbtn.setOnClickListener(this);
        signup_bdate.setOnClickListener(this);
        signin_btn1.setOnClickListener(this);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.cities_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signup_city.setAdapter(adapter);
//        signup_city.setOnItemClickListener((AdapterView.OnItemClickListener) this);

        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth)
            {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateCalendar();
            }
            private void updateCalendar ()
            {
                String Format = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(Format, Locale.US);

                signup_bdate.setText(sdf.format(calendar.getTime()));
            }

        };

        signup_bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignUpController.this, date, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.signup_createbtn:
                registerUser();
//                startActivity(new Intent(this, DiscoverController.class));
          break;
            case R.id.signin_btn1:
                Intent intent = new Intent(this, SignInController.class);
                break;

            case R.id.signupBack:
                 intent = new Intent(this, SignInController.class);
                 break;

        }
    }



    private void registerUser ()
    {
        String email = signup_email.getText().toString();
        String fname = signup_fname.getText().toString();
        String lname = signup_lname.getText().toString();
        String password = signup_password.getText().toString();
        String phone = signup_phone.getText().toString();
        String fullName;
        String city = signup_city.getSelectedItem().toString();


        String dateOfBirth = signup_bdate.getText().toString();

        if (fname.isEmpty() && lname.isEmpty())
        {
            signup_fname.setError("First Name Is Required");
            signup_fname.requestFocus();
            signup_lname.setError("Last Name Is Required");
            signup_lname.requestFocus();
            return;
        }
        else
        {
            fullName = fname + " " + lname;
        }

        if (email.isEmpty())
        {
            signup_email.setError("Email Is Required");
            signup_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signup_email.setError("Please Provide Valid Email");
            signup_email.requestFocus();
            return;
        }


        if (password.isEmpty())
        {
            signup_password.setError("Password Is Required");
            signup_password.requestFocus();
            return;
        }

        if (password.length() < 6 )
        {
            signup_password.setError("Min Password Length Should Be 6 Characters!");
            signup_password.requestFocus();
            return;
        }

        if (phone.isEmpty())
        {
            signup_phone.setError("Phone Number Is Required");
            signup_phone.requestFocus();
            return;
        }

        if (!Patterns.PHONE.matcher(phone).matches() && phone.length() < 10)
        {
            signup_phone.setError("Please Provide Valid Phone Number");
            signup_phone.requestFocus();
            return;
        }



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Customer customer = new Customer(fullName,dateOfBirth,email,city,password,phone);

                            FirebaseDatabase.getInstance().getReference("customers")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(SignUpController.this, "User has been registered successfully", Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(SignUpController.this, "Failed to register, Try again! ", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(SignUpController.this, "Failed to register, Try again! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}