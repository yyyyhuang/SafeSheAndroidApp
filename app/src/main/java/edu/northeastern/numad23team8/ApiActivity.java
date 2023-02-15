package edu.northeastern.numad23team8;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ApiActivity extends AppCompatActivity {

    private static final String TAG = "ApiActivity";

    // TODO: tmp variables from filter. year and language for now
    private EditText startYear;
    private  EditText endYear;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String webapi = "https://gutendex.com/books";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        // filter params
        radioGroup = (RadioGroup) findViewById(R.id.radio); // TODO: MODIFY ID NAME
        startYear = (EditText) findViewById(R.id.startyear);
        endYear = (EditText) findViewById(R.id.endyear);
    }

    // fetch data button handler
    public void callWebserviceButtonHandler(View view){
        // getting language selected
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        String lan = radioButton.getText().toString();

        PingWebServiceTask task = new PingWebServiceTask();
        String urlText = webapi + "?author_year_start=" + startYear.getText().toString() + "&author_year_end=" + endYear.getText().toString() + "&languages=" + lan;
        try{
            String url = NetworkUtil.validInput(urlText);
            task.execute(url);
        } catch (NetworkUtil.MyException e) {
            Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
        }
    }

    private class PingWebServiceTask  extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "Making progress...");
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jObject = new JSONObject();
            try {
                URL url = new URL(params[0]);
                String resp = NetworkUtil.httpResponse(url);
                jObject = new JSONObject(resp);
                return jObject;

            } catch (MalformedURLException e) {
                Log.e(TAG,"MalformedURLException");
                e.printStackTrace();
            } catch (ProtocolException e) {
                Log.e(TAG,"ProtocolException");
                e.printStackTrace();
            } catch (IOException e) {
                Log.e(TAG,"IOException");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.e(TAG,"JSONException");
                e.printStackTrace();
            }
            return jObject;
        }

        // TODO: do with result
        @Override
        protected void onPostExecute(JSONObject jObject) {
            super.onPostExecute(jObject);
            // TextView result_view = (TextView)findViewById(R.id.result_textview);


        }



    }







}


