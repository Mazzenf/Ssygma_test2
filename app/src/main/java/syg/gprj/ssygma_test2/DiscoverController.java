package syg.gprj.ssygma_test2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class DiscoverController extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener
{
    
    private CardView card_audi, card_bmw, card_chevy, card_chrysler, card_dodge, card_ford, card_genesis,
            card_gmc, card_honda, card_hyundai, card_kia, card_mazda, card_mercedess, card_nissan, card_toyota,
            card_volkswagen;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    
    
    @Override
    protected void onCreate (Bundle savedInstance) 
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.discover);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);





        card_audi = (CardView) findViewById(R.id.card_audi);
        card_bmw = (CardView) findViewById(R.id.card_bmw);
        card_chevy = (CardView) findViewById(R.id.card_chevy);
        card_chrysler = (CardView) findViewById(R.id.card_chrysler);
        card_dodge = (CardView) findViewById(R.id.card_dodge);
        card_ford = (CardView) findViewById(R.id.card_ford);
        card_genesis = (CardView) findViewById(R.id.card_genesis);
        card_gmc = (CardView) findViewById(R.id.card_gmc);
        card_honda = (CardView) findViewById(R.id.card_honda);
        card_hyundai = (CardView) findViewById(R.id.card_hyundai);
        card_kia = (CardView) findViewById(R.id.card_kia);
        card_mazda = (CardView) findViewById(R.id.card_mazda);
        card_mercedess = (CardView) findViewById(R.id.card_mercedess);
        card_nissan = (CardView) findViewById(R.id.card_nissan);
        card_toyota = (CardView) findViewById(R.id.card_toyota);
        card_volkswagen = (CardView) findViewById(R.id.card_volkswagen);

        card_audi.setOnClickListener(this);
        card_bmw.setOnClickListener(this);
        card_chevy.setOnClickListener(this);
        card_chrysler.setOnClickListener(this);
        card_dodge.setOnClickListener(this);
        card_ford.setOnClickListener(this);
        card_genesis.setOnClickListener(this);
        card_gmc.setOnClickListener(this);
        card_honda.setOnClickListener(this);
        card_hyundai.setOnClickListener(this);
        card_kia.setOnClickListener(this);
        card_mazda.setOnClickListener(this);
        card_mercedess.setOnClickListener(this);
        card_nissan.setOnClickListener(this);
        card_toyota.setOnClickListener(this);
        card_volkswagen.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) 
    {
        switch (view.getId())
        {
            case R.id.card_audi:

                cardClick("audi");
                Toast.makeText(this, "Audi clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_bmw:
                cardClick("BMW");
                Toast.makeText(this, "BMW clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_chevy:
                cardClick("chevrolet");
                Toast.makeText(this, "Chevy clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_chrysler:
                cardClick("chrysler");
                Toast.makeText(this, "Chrysler clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_dodge:
                cardClick("dodge");
                Toast.makeText(this, "Dodge clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_ford:
                cardClick("ford");
                Toast.makeText(this, "Ford clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_genesis:
                cardClick("genesis");
                Toast.makeText(this, "Genesis clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_gmc:
                cardClick("GMC");
                Toast.makeText(this, "Gmc clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_honda:
                cardClick("honda");
                Toast.makeText(this, "Honda clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_hyundai:
                cardClick("hyundai");
                Toast.makeText(this, "Hyundai clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_kia:
                cardClick("kia");
                Toast.makeText(this, "Kia clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_mazda:
                cardClick("mazda");
                Toast.makeText(this, "Mazda clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_mercedess:
                cardClick("mercedes");
                Toast.makeText(this, "mercedes clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_nissan:
                cardClick("nissan");
                Toast.makeText(this, "Nissan clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.card_toyota:
                cardClick("toyota");
                Toast.makeText(this, "Toyota clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.card_volkswagen:
                cardClick("volkswagen");
                Toast.makeText(this, "Volkswagen clicked", Toast.LENGTH_SHORT).show();
                break;

            case R.id.signupBack:
                Intent intent = new Intent(this, SignInController.class);
                break;

        }
        
    }

    public void cardClick (String name)
    {
        Intent intent1 = new Intent(this,BrandsTypesController.class);
        intent1.putExtra("manuf", name);
        startActivity(intent1);
    }

    @Override
    public void onBackPressed()
    {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.itemAbout:

             Intent intent = new Intent(this, SupportController.class);
             startActivity(intent);
             break;

            case R.id.itemSupport:
                 intent = new Intent(this, AboutController.class);
                 startActivity(intent);
                 break;
                 
            case R.id.itemOrders:
                intent = new Intent(this, OrdersController.class);
                startActivity(intent);
                break;

            case R.id.itemLogOut:
                Toast.makeText(this, "LOGOUT CLICKED", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
