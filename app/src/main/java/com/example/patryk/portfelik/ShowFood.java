package com.example.patryk.portfelik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.fingerprintAuthDrawable;
import static android.R.attr.name;

public class ShowFood extends AppCompatActivity {

    private String jsonResult;
    private String url = "http://ratcooding.site88.net/showFood.php";
    private ListView listView;


    String description;
    Integer expense;
    List<Map<String, String>> expensesList = new ArrayList<Map<String, String>>();
    ProgressDialog pDialog;
    TextView sumFood;
    int sum = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_food);
        listView = (ListView) findViewById(R.id.listView5);
        sumFood = (TextView) findViewById(R.id.sumFoodTv);
        accessWebService();
        onPreExecute();
    }
    protected void onPreExecute() {

        pDialog = new ProgressDialog(ShowFood.this);
        pDialog.setMessage("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private class JsonReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("username", getIntent().getStringExtra("username")));
                nameValuePairs.add(new BasicNameValuePair("password", getIntent().getStringExtra("password")));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
            }

            catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            }
            catch (IOException e) {

                // error
            }
            return answer;
        }
        @Override
        protected void onPostExecute(String result) {
            ListDrawer();
        }
    }

    public void accessWebService() {
        ShowFood.JsonReadTask task = new ShowFood.JsonReadTask();
        task.execute(url);
    }

    public void ListDrawer() {
        try {

            // working with database
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("expense");

            description = String.valueOf(jsonMainNode.length());
            expense = jsonMainNode.length();

            for (int i = 0; i < jsonMainNode.length() ; i++) {

                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                description = jsonChildNode.optString("description");
                expense = jsonChildNode.optInt("expense");

                // creating new hash map
                HashMap<String, String> expenseNo = new HashMap<String, String>();
                if(expense != 0) {
                    expenseNo.put("expense", "price:\t" + String.valueOf(expense) + "\t$");
                }else{
                    expenseNo.put("description", "description:\t" + description);
                }

                expensesList.add(expenseNo);

                // sum all expenses user in this category
                final double sumExpenses = Double.parseDouble(String.valueOf(expense));
                sum += sumExpenses ;
                sumFood.setText("On food you have spent" + "  " + sum + "  " + "$");

                // ending progress dialog
                pDialog.cancel();
            }
        } catch (JSONException e) {

            // error toast
            Toast.makeText(getApplicationContext(),
                    "Error..." + e.toString(), Toast.LENGTH_LONG).show();
        }

        ListAdapter adapter = new SimpleAdapter(
                ShowFood.this, expensesList,
                R.layout.adapterlist, new String[]{ "description" , "expense"}, new int[]{R.id.tx1,
                R.id.tx3
        });
        listView.setAdapter(adapter);
    }
}
