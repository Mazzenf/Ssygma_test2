package syg.gprj.ssygma_test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    DBMethods dbMethods = new DBMethods();

    String x1;
    TextView t11, t22;
    Button show;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test123);
        t11 = (TextView) findViewById(R.id.t1);
        t22 = (TextView) findViewById(R.id.t2);
        show = (Button) findViewById(R.id.button2);
////        DatabaseMethods dbm = null;
////        t11.setText(dbm.populate1("SELECT manufacturer FROM vehicle WHERE vehicle_id = 32",this,"sygma_db.db"));
////        t22.setText(dbm.populate1("SELECT make FROM vehicle WHERE vehicle_id = 32",this,"sygma_db.db"));
//        MyDBHandler dbh = new MyDBHandler(this,"sygma_db.db",null,3);
//        t11.setText(dbh.loadHandler());

//       MyDBHandler db1 = new MyDBHandler(getApplicationContext());
//
//        try {
//            db1.createDataBase();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            t11.setText(db1.getAllUsers());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }\




    }



//    public void populatecombos() throws SQLException {
//
//        dbMethods.idek1("SELECT manufacturer FROM vehicle WHERE vehicle_id = 32");
//        while (dbMethods.resultSet.next()) {
//            // manuCB.getItems().addAll(dbMethods.resultSet.getString("manufacturer"));
//
//             x1= dbMethods.resultSet.getString(1);
//        }
//
//        t11.setText(x1);
//    }





}