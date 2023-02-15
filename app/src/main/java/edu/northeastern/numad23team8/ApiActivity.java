package edu.northeastern.numad23team8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

public class ApiActivity extends AppCompatActivity {

    private static final String TAG = "ApiActivity";
    private Handler textHandler = new Handler();

    // TODO: tmp variables from filter. year and language for now
    private EditText startYear;
    private  EditText endYear;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView results;
    private String webapi = "https://gutendex.com/books";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        // filter params
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        startYear = (EditText) findViewById(R.id.startyear);
        endYear = (EditText) findViewById(R.id.endyear);
        results = (TextView) findViewById(R.id.res);
    }

    // fetch data button handler
    public void callWebserviceButtonHandler(View view){
        // getting language selected
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        String lan = radioButton.getText().toString();
        String start = startYear.getText().toString();
        String end = endYear.getText().toString();
//        PingWebServiceTask task = new PingWebServiceTask();
//        String urlText = webapi + "?author_year_start=" + startYear.getText().toString() + "&author_year_end=" + endYear.getText().toString() + "&languages=" + lan;
//        try{
//            String url = NetworkUtil.validInput(urlText);
//            task.execute(url);
//        } catch (NetworkUtil.MyException e) {
//            Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
//        }

        ExampleThread thread = new ExampleThread(start, end, lan);
        thread.start();
    }

    private String convertStreamToString(InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next().replace(",", ",\n") : "";
    }

    private class ExampleThread extends Thread {
        String start, end, lan;

        ExampleThread(String start, String end, String lan) {
            this.start = start;
            this.end = end;
            this.lan = lan;
        }

        @Override
        public void run(){
            try {
                // getting webservice json
                String[] resp = new String[1];
                URL url;
                url = new URL(webapi + "?author_year_start=" + start + "&author_year_end=" + end + "&languages=" + lan);
                HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setRequestMethod("GET");
                httpConn.setDoInput(true);
                httpConn.setConnectTimeout(5000);
                httpConn.connect();

                InputStream inputStream = httpConn.getInputStream();
                String response = convertStreamToString(inputStream);
                inputStream.close();
                httpConn.disconnect();

                JSONObject responseJSON = new JSONObject(response);
                // only need books
                JSONArray books = responseJSON.getJSONArray("results"); // Note: books is a array of book objects
                resp[0] = books.getJSONObject(0).getString("title"); // TODO: only displaying first book title for now. MODIFY
                String tmpbook = resp[0];

                textHandler.post(new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        results.setText(tmpbook); // TODO: MODIFY handler
                    }
                });
                Log.d(TAG, "Running on a different thread using Thread class");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}


