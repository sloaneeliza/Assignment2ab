package com.example.assignment2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private InfoWebFragment infoWebFragment;
    private TickerListFragment tickerListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tickerListFragment = (TickerListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.tickerListFragment);
        infoWebFragment = (InfoWebFragment) getSupportFragmentManager()
                .findFragmentById(R.id.infoWebFragment);
    }

    public void loadTickerInWebView(String ticker) {
        infoWebFragment.loadTicker(ticker);
    }

    public void addTickerFromSMS(String ticker) {
        tickerListFragment.addTicker(ticker);
    }
}