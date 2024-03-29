package hera.com.orders.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class User {
    SQLiteDatabase db;
    String sb;
    public void addUser(Context context, hera.com.orders.model.User user)
    {
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        db.execSQL("create table if not exists user1(id integer, username varchar(1000), password varchar(1000)," +
                " token varchar(1000))");
        Cursor c1=db.rawQuery("select * from user1",null);
        StringBuffer sb1=new StringBuffer();
        int flag=0;
        while(c1.moveToNext()) {
            if(c1.getInt(0)==user.Id)
            {
                flag=1;
            }
        }
        if(flag==0) {
            db.execSQL("delete from user1");
            db.execSQL("insert into user1 values(" + user.Id + ",'" + user.Username + "','" + user.Password + "','" + user.Token + "')");
        }
        //db.execSQL("delete from "+ "user1");
        //db.execSQL("delete from "+ "url");
        db.execSQL("create table if not exists url(url varchar(1000))");
        Cursor c=db.rawQuery("select * from url",null);
        while(c.moveToNext()) {
            sb=c.getString(0);
        }
        if(!sb.equals(user.Url)) {
            ContentValues cv = new ContentValues();
            cv.put("url", user.Url);
            db.update("url", cv, "", null);
        }
        Toast.makeText(context, "Korisnik dodan", Toast.LENGTH_SHORT).show();
    }
    public void showUser(Context context)
    {
        Cursor c=db.rawQuery("select * from user1",null);
        StringBuffer stringBuffer=new StringBuffer();
        while(c.moveToNext())
        {
            stringBuffer.append(c.getInt(0)+"   "+c.getString(1)+" " +
                    " "+c.getString(2)+"  "+c.getString(3));
        }
        Toast.makeText(context, stringBuffer, Toast.LENGTH_SHORT).show();
    }
}
