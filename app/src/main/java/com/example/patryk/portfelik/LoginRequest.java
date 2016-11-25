package com.example.patryk.portfelik;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Patryk on 07.10.2016.
 */
public class LoginRequest extends StringRequest{

    private static final String LOGIN_REQUEST_URL = "http://ratcooding.site88.net/Login.php"  ;
    private Map<String , String> params ;

    public LoginRequest ( String username   , String password , Response.Listener<String> listener ) {
        super(Request.Method.POST , LOGIN_REQUEST_URL , listener , null ) ;
        params = new HashMap<>();
        params.put("password" , password);
        params.put("username", username);
    }

    public Map<String, String> getParams() {
        return params;
    }
}