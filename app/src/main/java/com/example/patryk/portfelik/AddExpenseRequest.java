package com.example.patryk.portfelik;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Patryk on 12.10.2016.
 */
public class AddExpenseRequest extends StringRequest {
    
    private static final String REGISTER_REQUEST_URL = "http://ratcooding.site88.net/expense.php"  ;
    private Map<String , String> params ;

    public AddExpenseRequest(String expense, String description, String userID ,   Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener , null ) ;
        params = new HashMap<>();

        params.put("userID", userID);
        params.put("expense", expense);
        params.put("description", description);

    }

    public Map<String, String> getParams() {
        return params;
    }
}
