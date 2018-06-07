package hera.com.orders.infrastructure.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import hera.com.orders.LoginActivity;
import hera.com.orders.MainActivity;


public class User {
    RequestQueue requestQueue;  // This is our requests queue to process our HTTP requests.
    public void login(final Context context, final String username, final String password)
    {
        requestQueue = Volley.newRequestQueue(context); // This setups up a new request queue which we will need to make HTTP requests
        RequestQueue queue = Volley.newRequestQueue(context);
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("username",username);
            parameters.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest strRequest = new JsonObjectRequest(Request.Method.POST, LoginActivity.url,parameters,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try {
                            String jwt = response.getString("jwt");
                            String id = response.getJSONObject("korisnik").getString("id");
                            MainActivity.Id=Integer.parseInt(id);
                            LoginActivity.classes_user.Id=Integer.parseInt(id);
                            LoginActivity.classes_user.Token=jwt;
                            LoginActivity.sqlite_user.addUser(context, Integer.parseInt(id), username, password, LoginActivity.url, jwt);
                            Intent intent=new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                            ((Activity)context).finish();
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
                        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(strRequest);
    }
}
