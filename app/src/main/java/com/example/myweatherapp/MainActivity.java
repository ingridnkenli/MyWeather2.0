package com.example.myweatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;





public class MainActivity extends AppCompatActivity {

    TextView cityName;
    Button searchButton;
    TextView result;

    class Weather extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... address) {

            try {
                URL url = new URL(address[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //Establish connection with object
                connection.connect();

                //retrieve data from url
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                //retrieve data and return it as String
                int data = isr.read();
                String content = "";
                char ch;
                while (data != -1) {
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                Log.i("Content", content);
                return content;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void search(View view) {

        cityName = findViewById(R.id.cityNamePlainText);
        searchButton = findViewById(R.id.searchButton);
        result = findViewById(R.id.resultTextView);

        String cName = cityName.getText().toString();
        String content;
        Weather weather = new Weather();
        try {
            content = weather.execute("https://samples.openweathermap.org/data/2.5/weather?q=" + cName + "&appid=b6907d289e10d714a6e88b30761fae22").get();
            //First we will check if data is retrieved successfully
            Log.i("contentData", content);

            //JSON
            JSONObject jsonObject = new JSONObject(content);
            String weatherData = jsonObject.getString("weather");
            String mainTemperature = jsonObject.getString("main"); //refers to main object, not property in weather array
            //weather data is in Array
            JSONArray array = new JSONArray(weatherData);
            String main = "";
            String description = "";
            String temperature = ""; //property in 'main' object
            double visibility;

            for (int i = 0; i < array.length(); i++) {
                JSONObject weatherPart = array.getJSONObject(i);
                main = weatherPart.getString("main");
                description = weatherPart.getString("description");

            }
            JSONObject mainPart = new JSONObject(mainTemperature);
            temperature = mainPart.getString("temp");

            visibility = Double.parseDouble(jsonObject.getString("visibility"));
            int visibilityInKilometer = (int) visibility / 1000;
            Log.i("Temperature", temperature);


            Log.i("main", main); //property value in weather array
            Log.i("description", description); //property value in weather array
            String resultText = "Main: " + main +
                    "\nDescription: " + description +
                    "\nTemperature : " + temperature +
                    "\nVisibility: " + visibilityInKilometer + "Km";
            result.setText(resultText);
            //Now we will show this result on screen
            //  Youtube 8:37/17:56 mark
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


}


