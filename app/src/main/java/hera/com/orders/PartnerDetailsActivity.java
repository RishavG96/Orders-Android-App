package hera.com.orders;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hera.com.orders.adapters.DetailsAdapter;
import hera.com.orders.adapters.PartnerListAdapter;
import hera.com.orders.sqlite.Orders;
import hera.com.orders.sqlite.Partner;

public class PartnerDetailsActivity extends AppCompatActivity {

    ListView lv;
    DetailsAdapter adapter;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    Partner sqlite_partner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_details);

        sqlite_partner=new Partner();
        lv=findViewById(R.id.listview9);
        List<hera.com.orders.model.Partner> partnerList=new ArrayList<>();
        partnerList=(List<hera.com.orders.model.Partner>)sqlite_partner.showPartner(this,PartnersActivity.pos);
        String id = partnerList.get(0).id.toString();
        String code = partnerList.get(0).code;
        String name = partnerList.get(0).name;
        String address = partnerList.get(0).address;
        String city = partnerList.get(0).city;
        String amount = partnerList.get(0).amount;
        String type = partnerList.get(0).type;
        String discount = partnerList.get(0).discount;
        String status = partnerList.get(0).status;
        String businessHOurs = partnerList.get(0).businessHours;
        String timeOfReceipt = partnerList.get(0).timeOfReceipt;
        String responsiblePerson = partnerList.get(0).responsiblePerson;
        String forMobile = partnerList.get(0).forMobile;
        ArrayList values=new ArrayList();
        ArrayList heading=new ArrayList();
        heading.add("ID Partnera");
        heading.add("Kod Partnera");
        heading.add("Ime Partnera");
        heading.add("Adresa Partnera");
        heading.add("Grad Partnera");
        heading.add("Cijena");
        heading.add("Tip Partnera");
        heading.add("Popust");
        heading.add("Status Partnera");
        heading.add("Radno Vrijeme");
        heading.add("Time Of Recepit");
        heading.add("Odgovorna Osoba");
        heading.add("For Mobile");
        values.add(id);
        values.add(code);
        values.add(name);
        values.add(address);
        values.add(city);
        values.add(amount);
        values.add(type);
        values.add(discount);
        values.add(status);
        values.add(businessHOurs);
        values.add(timeOfReceipt);
        values.add(responsiblePerson);
        values.add(forMobile);

        adapter=new DetailsAdapter(this, heading, values);
        lv.setAdapter(adapter);

        navigationView=findViewById(R.id.nav_view8);
        Toolbar toolbar=findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer_layout8);
        actionBarDrawerToggle= new ActionBarDrawerToggle(this,drawerLayout, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        View headerView = navigationView.getHeaderView(0);
        TextView username=headerView.findViewById(R.id.nav_header_textView1);
        username.setText(MainActivity.user);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.orders:
                                Intent intent2=new Intent(getApplicationContext(), MainActivity.class);
                                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent2);
                                finish();
                                break;
                            case R.id.partner:
                                Intent intent = new Intent(getApplicationContext(), PartnersActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.article:
                                Intent intent1=new Intent(getApplicationContext(), ArticleActivity.class);
                                startActivity(intent1);
                                finish();
                                break;
                            case R.id.partnerweek:
                                Intent intent3=new Intent(getApplicationContext(), WeekDaysActivity.class);
                                startActivity(intent3);
                                finish();
                                break;
                            case R.id.refresh:
                                LoginActivity.assort=0;
                                LoginActivity.art=0;
                                LoginActivity.part=0;
                                LoginActivity.part_week=0;
                                Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent4);
                                finish();
                                break;
                            case R.id.setup:
                                Intent intent5 = new Intent(getApplicationContext(), UpdateURLActivity.class);
                                startActivity(intent5);
                                break;
                            case R.id.logout:
                                Intent intent6 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent6);
                                MainActivity.db.execSQL("drop table login");
                                MainActivity.db.execSQL("drop table user1");
                                MainActivity.db.execSQL("drop table url");
                                finish();
                                break;
                        }
                        drawerLayout.closeDrawers();  // CLOSE DRAWER
                        return true;
                    }
                });
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}
