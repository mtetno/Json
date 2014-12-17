package com.example.dynamicjson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;

public class MainActivity extends Activity {

    ArrayList<Model> dynamicPair = new ArrayList<Model>();

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        new GetJSON().execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // http://private-0725d-api129trial.apiary-mock.com/notes
        // {
        // "values":[{"Service_Type":"User Service Restoration","Counts":"37"},{"Service_Type":"User Service Request","Counts":"3"}]}

    }

    public class GetJSON extends AsyncTask<String, Void, Void> {
        JSONObject jsonObject;
        JSONArray jsonArray;

        int isError = 0;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                HttpUriRequest request = new HttpGet(
                        "http://private-0725d-api129trial.apiary-mock.com/notes");
                // request.setHeader("Accept-Encoding", "gzip");
                AndroidHttpClient.modifyRequestToAcceptGzipResponse(request);
                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(request);
                InputStream inputStream = AndroidHttpClient
                        .getUngzippedContent(response.getEntity());
                BufferedReader reader;
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                // String json = "";
                StringBuilder json = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    json.append(line + "\n");
                }

                String mData = json.toString();
                // if (mData.contains("Sorry Agnet is not connected")) {
                // // if (true) { for chkinh error conditions
                // isError = 1;
                //
                // } else {
                // try {
                // jsonObject = new JSONObject(mData);
                // jsonArray = jsonObject.getJSONArray("values");
                // } catch (JSONException e) {
                // // TODO Auto-generated catch block
                // e.printStackTrace();
                // }
                // }

                // searchResult refers to the current element in the array
                // "search_result"
                try {
                    jsonObject = new JSONObject(mData);
                    jsonArray = jsonObject.getJSONArray("values");
                    Iterator keys;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject child = jsonArray.getJSONObject(i);
                        keys = child.keys();
                        Model m = new Model();
                        ArrayList<String> keyzz = new ArrayList<String>();
                        while (keys.hasNext()) {

                            // loop to get the dynamic key
                            String currentDynamicKey = (String) keys.next();
                            m.setKey(currentDynamicKey);
                            System.out.println("Key :" + currentDynamicKey);
                            String strcode = (String) child
                                    .get(currentDynamicKey);
                            System.out.println("value :" + strcode);
                            // get the value of the dynamic key

                            /**/

                            /**/
                            // keyzz.add(object)

                            // do something here with the value...
                        }
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (OutOfMemoryError me) {

                me.printStackTrace();
            } catch (NullPointerException exp) {
                exp.printStackTrace();

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }
}
