package com.example.patryk.portfelik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
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


public class ShowExpense extends AppCompatActivity {
    private String jsonResult;
    private String url = "http://ratcooding.site88.net/showExpense.php";
    private ListView listView;
    public List<HashMap<String, String>> expensesList = new ArrayList<>();
    ProgressDialog pDialog ;
    TextView sumAllTv;
    String description;
    Integer expense;
    double sum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expense);
        listView = (ListView) findViewById(R.id.listView1);
        accessWebService();
        onPreExecute();


        Intent intent = getIntent();

        final String password = intent.getStringExtra("password");
        final String username = intent.getStringExtra("username");
        final String userID = intent.getStringExtra("userID");

        Button addExpense = (Button) findViewById(R.id.addEx);
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowExpense.this, MainActivity.class);
                intent.putExtra("password" , password );
                intent.putExtra("username" , username ) ;
                intent.putExtra("userID" , userID ) ;
                ShowExpense.this.startActivity(intent);
                finish();
            }
        });
        Button menuEx = (Button) findViewById(R.id.shoeEx);
        menuEx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ShowExpense.this, showExMenu.class);
                intent.putExtra("password" , password );
                intent.putExtra("username" , username ) ;
                intent.putExtra("userID" , userID ) ;
                ShowExpense.this.startActivity(intent);
            }
        });

        sumAllTv = (TextView) findViewById(R.id.sumAllTv);
    }

    protected void onPreExecute() {
        // Showing progress dialog
        pDialog = new ProgressDialog(ShowExpense.this);
        pDialog.setMessage("Loading ... ");
        pDialog.setCancelable(false);
        pDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();


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
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String inputStreamToString(InputStream is) {
            String line;
            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return sb.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrawer();
            setAdapter();
        }
    }

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        task.execute(url);
    }

    public void ListDrawer() {
        try {

            // working with database
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("expense");

            description = String.valueOf(jsonMainNode.length());
            expense = jsonMainNode.length();

            for (int i = 0; i < jsonMainNode.length(); i++) {

                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                description = jsonChildNode.optString("description");
                expense = jsonChildNode.optInt("expense");

                // how to add two values to hashmap using one key but this still sucks

//                Map<String, List<String>> hm = new HashMap<String, List<String>>();
//                List<String> values = new ArrayList<String>();
//                values.add(String.valueOf(expense));
//                values.add(description);
//                if(expense != 0){
//                    expensesList.remove(expense);
//                    hm.put("expense", values);
//                }
//                hm.put("expense", values);
//
//                expensesList.add(hm);

                // creating new hash map
                HashMap<String, String> expenseNo = new HashMap<String, String>();
                if (expense != 0) {
                    expenseNo.put("expense", "price:" + "\t" + String.valueOf(expense) + "\t$");
                } else {
                    expenseNo.put("description", "description:\t" + description);

                }
                expensesList.add(expenseNo);

                // sum all expenses user in this category
                final double sumExpenses = Double.parseDouble(String.valueOf(expense));
                sum += sumExpenses;
                sumAllTv.setText(" " + sum + " $");

                // ending progress dialog
                pDialog.cancel();

            }
        } catch (JSONException e) {

            // toast error
            Toast.makeText(getApplicationContext(),
                    "Error..." + e.toString(), Toast.LENGTH_LONG).show();
        }

    }
    public void setAdapter() {

        ListAdapter adapter = new SimpleAdapter(
                ShowExpense.this, expensesList,
                R.layout.adapterlist, new String[]{ "expense" , "description"}, new int[]{R.id.tx3 , R.id.tx1
        });

        listView.setAdapter(adapter);
        pDialog.cancel();
    }

    public List<HashMap<String, String>> getList() {

        return expensesList;
    }
}


