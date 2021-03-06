package com.fawad.cronavirus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
  TextView tvCases,tvRecoverd,tvCritical,tvActiveCases,tvTodayCases,tvTodayDeaths,tvTotalDeaths,tvAffectedCountries;
  SimpleArcLoader loader;
  ScrollView scrollStats;
  PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        idInitialzed();
        fetchData();

    }

    private void fetchData() {
        loader.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CronaApi.Cronal_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());
                    tvCases.setText(jsonObject.getString("cases"));
                    tvRecoverd.setText(jsonObject.getString("recovered"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvActiveCases.setText(jsonObject.getString("active"));
                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));

                    //PieChartGraph
                    pieChart.addPieSlice(new PieModel("Total Cases",Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Recoverd",Integer.parseInt(tvRecoverd.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Total Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Active Cases",Integer.parseInt(tvActiveCases.getText().toString()), Color.parseColor("#29B6F6")));
                    pieChart.startAnimation();

                    loader.stop();
                    loader.setVisibility(View.GONE);
                    scrollStats.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    e.printStackTrace();
                    loader.stop();
                    loader.setVisibility(View.GONE);
                    scrollStats.setVisibility(View.VISIBLE);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.stop();
                loader.setVisibility(View.GONE);
                scrollStats.setVisibility(View.VISIBLE);
                FancyToast.makeText(getApplicationContext(),error.getMessage(),FancyToast.LENGTH_LONG, FancyToast.ERROR,true);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void goTrackCountries(View view) {
        startActivity(new Intent(getApplicationContext(),AffectedCountries.class));

    }
    public void  idInitialzed(){
        tvCases = findViewById(R.id.tvCases);
        tvRecoverd = findViewById(R.id.tvRecoverd);
        tvCritical = findViewById(R.id.tvCritical);
        tvActiveCases = findViewById(R.id.tvActiveCases);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);
        loader = findViewById(R.id.loader);
        scrollStats = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.pieChart);

    }
}