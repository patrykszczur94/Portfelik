package com.example.patryk.portfelik;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by patryk on 2016-10-28.
 */

public class NewBillExpenseRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://ratcooding.site88.net/newBillExpense.php"  ;
    private Map<String , String> params ;

    public NewBillExpenseRequest(String expense, String description, String userID ,  Response.Listener<String> listener) {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener , null ) ;
        params = new HashMap<>();
        params.put("userID", userID);
        params.put("expense", expense);
        params.put("description", description);
    }

    public Map<String, String> getParams() {
        return params;
    }

}
