package edu.northeastern.numad23team8;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private LinearLayout layout;
    private EditText startYear;
    private EditText endYear;
    private RadioGroup radioGroup;
    private Button find;
    private TextView results;
    private String start, end, lan;
    private String webapi = "https://gutendex.com/books";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_layouts);

        // filter params
        layout = findViewById(R.id.linear);

        addText();
        addRadioButtons();
        addButton();
        addRes();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                lan = (i == 1) ? "en": "fr";
            }
        });
    }

    //Adding textviews
    private void addText() {
        LinearLayout textLayout = new LinearLayout(this);
        textLayout.setOrientation(LinearLayout.VERTICAL);

        layout.addView(textLayout);

//        for (int i = 0; i <= 1; i++) {
//            EditText editText = new EditText(this);
//            if (i == 0) {
//                editText.setHint("Enter Start Year");
//            }else {
//                editText.setHint("Enter End Year");
//            }
//            setTextAttributes(editText);
//            textLayout.addView(editText);
//        }
        startYear = new EditText(this);
        startYear.setHint("Enter Start Year");
        setTextAttributes(startYear);
        textLayout.addView(startYear);
        addLine();

        endYear = new EditText(this);
        endYear.setHint("Enter End Year");
        setTextAttributes(endYear);
        textLayout.addView(endYear);

    }

    //Adding radio buttons
    private void addRadioButtons() {
        radioGroup = new RadioGroup(this);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        layout.addView(radioGroup);
//        for (int i = 0; i <= 1; i++) {
//            RadioButton radioButton = new RadioButton(this);
//            if (i == 0) {
//                radioButton.setText("English");
//            } else {
//                radioButton.setText("French");
//            }
//            radioGroup.addView(radioButton);
////            setButtonAttributes(radioButton);
//        }
        RadioButton eng = new RadioButton(this);
        eng.setText("English");
        radioGroup.addView(eng);
        setButtonAttributes(eng);

        addLine();

        RadioButton fren = new RadioButton(this);
        fren.setText("French");
        radioGroup.addView(fren);
        setButtonAttributes(fren);
    }

    private void addButton() {
        find = new Button(this);
        find.setText("FIND");
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callWebserviceButtonHandler(view);
            }
        });

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layout.addView(find);
        find.setLayoutParams(params);

        addLine();
    }

    private void addRes() {
        results = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layout.addView(results);
        results.setLayoutParams(params);
    }
    private void setTextAttributes(EditText editText) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 10, 10, 0);
        editText.setLayoutParams(params);
    }

    private void setButtonAttributes(RadioButton radioButton) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10, 50, 0,0);
        radioButton.setLayoutParams(params);
    }
    private void addLine() {
        LinearLayout lineLayout = new LinearLayout(this);
        lineLayout.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 2
        );
        params.setMargins(0, 2, 0, 2);
        lineLayout.setLayoutParams(params);
        layout.addView(lineLayout);
    }

    // fetch data button handler
    public void callWebserviceButtonHandler(View view){
        // getting language selected
//        int selectedId = radioGroup.getCheckedRadioButtonId();
//        radioButton = (RadioButton) findViewById(selectedId);
//        String lan = radioButton.getText().toString();
        start = startYear.getText().toString();
        end = endYear.getText().toString();
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


