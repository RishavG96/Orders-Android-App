package hera.com.orders;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    EditText id;
    EditText pass;
    Button submit;
    EditText newurl;
    public static int part, art,assort,part_week;
    hera.com.orders.model.User classes_user;
    hera.com.orders.service.User service_user;
    SQLiteDatabase db;
    public static String url="http://212.39.121.35:8080/Euro99NarudzbeBack/resources/"; // This will hold the full URL which will include the username entered in the id.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        classes_user=new hera.com.orders.model.User();
        service_user=new hera.com.orders.service.User();


        this.id = (EditText) findViewById(R.id.ID);
        this.pass = (EditText) findViewById(R.id.pass);
        this.submit = (Button) findViewById(R.id.send);
        newurl=findViewById(R.id.editText2);
        newurl.setText(url);

        db=openOrCreateDatabase("order",MODE_PRIVATE, null);
        db.execSQL("create table if not exists url(url varchar(1000))");
        db.execSQL("create table if not exists login(flag integer)");
        Cursor c=db.rawQuery("select * from url",null);
        int flag=0;
        while(c.moveToNext()) {
            flag=1;
        }
        if(flag==0) {
            db.execSQL("insert into url values('" + url + "')");
        }
        Cursor c1=db.rawQuery("select * from login",null);
        int flag1=0;
        while(c1.moveToNext()) {
            flag1=1;
        }
        if(flag1==0) {
//            db.execSQL("insert into login values(1)");
            part=0;
            art=0;
            assort=0;
            part_week=0;
        }
        else
        {
            part=1;
            art=1;
            assort=1;
            part_week=1;
            Intent intent=new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classes_user.Username=id.getText().toString();
                classes_user.Password=pass.getText().toString();
                service_user.login(getApplicationContext(),classes_user);

                if(!url.equals(newurl.getText().toString()))
                {
                    url=newurl.getText().toString();
                    ContentValues cv=new ContentValues();
                    cv.put("url",url);
                    db.update("url",cv,null,null);
                    Toast.makeText(getApplicationContext(),"URL Promijenjen",Toast.LENGTH_SHORT).show();
                }
                classes_user.Url=url;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Cursor c1=db.rawQuery("select * from login",null);
        int flag1=0;
        while(c1.moveToNext()) {
            flag1=1;
        }
        if(flag1==1)
        {
            part=1;
            art=1;
            assort=1;
            part_week=1;
            Intent intent=new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        else
        {
            part=0;
            art=0;
            assort=0;
            part_week=0;
        }
    }
}
