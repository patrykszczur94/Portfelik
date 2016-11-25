package com.example.patryk.portfelik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


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

import static java.lang.Integer.parseInt;
import static java.lang.Integer.toString;


public class showLove extends AppCompatActivity {

    List<HashMap<String, String>> expensesList = new ArrayList<>();

    private ProgressDialog pDialog;
    private String jsonResult;
    private String url = "http://ratcooding.site88.net/showLove.php";
    private ListView listView;
    private TextView sumLoveTv;
    private int expense;
    private String description;
    private double sum = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_love);

        listView = (ListView) findViewById(R.id.listView4);
        sumLoveTv = (TextView) findViewById(R.id.sumLoveTv);

        accessWebService();
        onPreExecute();

    }

    protected void onPreExecute() {

        // creating progress dialog
        pDialog = new ProgressDialog(showLove.this);
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

                // send username and password user to database
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

        showLove.JsonReadTask task = new showLove.JsonReadTask();
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
                sumLoveTv.setText("On your love you have spent" + "  " + sum + "  " + "$");

                // ending progress dialog
                pDialog.cancel();
            }
        } catch (JSONException e) {

            // error toast
            Toast.makeText(getApplicationContext(),
                    "Error..." + e.toString(), Toast.LENGTH_LONG).show();
        }
        // setting adapter for listView
        ListAdapter adapter = new SimpleAdapter(

                showLove.this, expensesList,
                R.layout.adapterlist, new String[]{ "description" , "expense"}, new int[]{R.id.tx1,
                R.id.tx3
        });
        listView.setAdapter(adapter);
    }
}