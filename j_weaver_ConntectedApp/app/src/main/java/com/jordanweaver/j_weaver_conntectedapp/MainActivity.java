package com.jordanweaver.j_weaver_conntectedapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity {

    ArrayList<Reddit_Object> reddit_objects;
    GridView gridView;
    Button submitButton;
    EditText userInput;
    Context mContext;
    MyAdapter baseAdapter;
    Boolean count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = (EditText) findViewById(R.id.userInput);
        submitButton = (Button) findViewById(R.id.submitButton);
        gridView = (GridView) findViewById(R.id.gridView);

        reddit_objects = new ArrayList<>();

        mContext = this;

        count = true;


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!userInput.getText().toString().equals("")) {

                    reddit_objects.clear();

                    String search = userInput.getText().toString();

                    count = false;

                    ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

                    if (manager != null) {
                        NetworkInfo info = manager.getActiveNetworkInfo();
                        if (info == null){
                            Toast.makeText(mContext, "There's something wrong with Network Connections", Toast.LENGTH_LONG).show();
                        } else {
                        Log.e("Network", info +"");
                        if (info.isConnected()) {
                        MyTask myTask = new MyTask();
                        myTask.execute("http://api.reddit.com/r/"+search);
                        }
                    }
                } else {
                        Toast.makeText(mContext, "Must enter text", Toast.LENGTH_LONG).show();
                    }
                }





            }
        });

    }


        public class MyTask extends AsyncTask<String, Void, String> {

            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                dialog = new ProgressDialog(MainActivity.this);
                dialog.setTitle("Loading Data");
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("Please Wait");
                dialog.setCancelable(false);
                dialog.show();

            }

            @Override
            protected String doInBackground(String... params) {

                String results = "";

                try {
                    URL url = new URL(params[0]);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    InputStream is = connection.getInputStream();
                    results = IOUtils.toString(is);
                    Log.e("No", "Error");

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    results = "Error";
                } catch (IOException e) {
                    e.printStackTrace();
                    results = "Error";
                }

                return results;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Log.d("Testing", s);

                if (s.equals("Error")) {
                    Toast.makeText(MainActivity.this, "Something Else went wrong.", Toast.LENGTH_LONG).show();
                } else {
                    try {
                        JSONObject mainObject = new JSONObject(s);

                        JSONArray childrenArray = mainObject.getJSONObject("data").getJSONArray("children");
                        String title;
                        String picture;

                        for (int i = 0; i < childrenArray.length(); i++) {
                            JSONObject childObject = childrenArray.getJSONObject(i).getJSONObject("data");
                            if (childObject.has("title")) {
                                title = childObject.getString("title");
                            } else {
                                title = "N/A";
                            }
                            if (childObject.has("thumbnail")) {
                                picture = childObject.getString("thumbnail");
                            } else {
                                picture = "N/A";
                            }

                            Log.e("Before", "Custom Object");
                            reddit_objects.add(new Reddit_Object(title, picture));
                            Log.e("Passed", "Custom Object");
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Log.e("Before", "baseAdapter");
                baseAdapter = new MyAdapter(mContext, reddit_objects);
//                gridView = (GridView) findViewById(R.id.gridView);
                gridView.setAdapter(baseAdapter);
                Log.e("After", "baseAdapter");
                dialog.cancel();
            }


        }



}
