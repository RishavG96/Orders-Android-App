package hera.com.orders.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import hera.com.orders.LoginActivity;
import hera.com.orders.MainActivity;
import hera.com.orders.ShowPartnersByWeekActivity;

import static android.content.Context.MODE_PRIVATE;

public class PartnerByWeek {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.

    String jwtToken;
    hera.com.orders.sqlite.PartnerByWeek partnerByWeek;
    Context context;
    public static SQLiteDatabase db;
    public void connect(Context con)
    {
        context=con;
        partnerByWeek=new hera.com.orders.sqlite.PartnerByWeek();
        requestQueue = Volley.newRequestQueue(context); // This setups up a new request queue which we will need to make HTTP requests
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject parameters = new JSONObject();
        db=context.openOrCreateDatabase("order",MODE_PRIVATE,null);
        Cursor c=db.rawQuery("select * from user1",null);
        while(c.moveToNext())
        {
            if(c.getInt(0)== MainActivity.Id)
            {
                jwtToken=c.getString(3);
            }
        }
        JsonArrayRequest strRequest = new JsonArrayRequest(Request.Method.GET, ShowPartnersByWeekActivity.partner_week, (String) null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response)
                    {
                        try {

                            if(LoginActivity.part_week==0) {
                                db=context.openOrCreateDatabase("order", MODE_PRIVATE,null);
                                //partnerByWeek.deletePartner(context);
                                db.beginTransaction();
                                for (int i = 0; i < response.length(); i++) {

                                    JSONObject ob = (JSONObject) response.opt(i);

                                    int partnerId = Integer.parseInt(ob.optString("partnerId"));
                                    String weekDay = ob.optString("weekDay");

                                    partnerByWeek.addPartnerByWeek(context, partnerId, weekDay);
                                }
                                db.setTransactionSuccessful();
                                db.endTransaction();
                                db.close();
                                LoginActivity.part_week=1;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(context,"Error= "+ error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("jwtToken", jwtToken);
                return headers;
            }
        }
                ;
        queue.add(strRequest);
    }
}